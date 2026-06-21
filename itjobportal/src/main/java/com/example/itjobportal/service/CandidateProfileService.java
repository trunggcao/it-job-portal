package com.example.itjobportal.service;

import com.example.itjobportal.dto.CandidateProfileDTO;
import com.example.itjobportal.dto.CandidateSearchResponseDTO;
import com.example.itjobportal.dto.ProjectDTO;
import com.example.itjobportal.dto.SkillDTO;
import com.example.itjobportal.entity.*;
import com.example.itjobportal.enums.EUserRole;
import com.example.itjobportal.repository.CandidateProfileRepository;
import com.example.itjobportal.repository.ProfileUnlockRepository;
import com.example.itjobportal.repository.SkilllRepository;
import com.example.itjobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;
    private final SkilllRepository skillRepository;
    private final ProfileUnlockRepository profileUnlockRepository;

    public CandidateProfile toEntity(CandidateProfileDTO dto, User user,
                                     List<Skill> skills, List<Project> projects){
        return CandidateProfile.builder()
                .title(dto.getTitle())
                .yearsOfExperience(dto.getYearsOfExperience())
                .isOpenToWork(dto.getIsOpenToWork() != null ? dto.getIsOpenToWork() : false)
                .user(user)
                .skills(skills)
                .projects(projects != null ? projects : new ArrayList<>())
                .build();
    }

    public CandidateProfileDTO toDTO(CandidateProfile enity){
        List<SkillDTO> skillDTOs = enity.getSkills() == null ? new ArrayList<>() :
                enity.getSkills().stream()
                        .map(s -> SkillDTO.builder()
                                .id(s.getId())
                                .name(s.getName())
                                .build())
                        .toList();
        List<ProjectDTO> projectDTOs = enity.getProjects() == null ? new ArrayList<>() :
                enity.getProjects().stream()
                        .map(p -> ProjectDTO.builder()
                                .projectName(p.getProjectName())
                                .startDate(p.getStartDate())
                                .endDate(p.getEndDate())
                                .description(p.getDescription())
                                .build())
                        .toList();
        return CandidateProfileDTO.builder()
                .id(enity.getId())
                .title(enity.getTitle())
                .yearsOfExperience(enity.getYearsOfExperience())
                .isOpenToWork(enity.getIsOpenToWork())
                .skills(skillDTOs)
                .projects(projectDTOs)
                .createdAt(enity.getCreatedAt())
                .updateAt(enity.getUpdatedAt())
                .build();
    }

    private String maskText(String text, boolean isEmail) {
        if (text == null || text.isEmpty()) return "***";

        if (isEmail) {
            return text.replaceAll("(?<=.{2}).(?=[^@]*?@)", "*");
        } else {
            return text.replaceAll("(?<=\\b\\w)\\w+", "***");
        }
    }

    public CandidateSearchResponseDTO toResponseDTO(CandidateProfile profile, boolean isUnlocked) {

        User user = profile.getUser();

        // Danh sách dự án luôn công khai để thu hút nhà tuyển dụng xem năng lực trước
        List<ProjectDTO> projectDTOs = profile.getProjects() == null ? new ArrayList<>() :
                profile.getProjects().stream()
                        .map(p -> ProjectDTO.builder()
                                .projectName(p.getProjectName())
                                .startDate(p.getStartDate())
                                .endDate(p.getEndDate())
                                .description(p.getDescription())
                                .build())
                        .toList();

        return CandidateSearchResponseDTO.builder()
                .profileId(profile.getId())
                .title(profile.getTitle())
                .yearsOfExperience(profile.getYearsOfExperience())
                .skills(profile.getSkills() == null ? new ArrayList<>() :
                        profile.getSkills().stream().map(Skill::getName).toList())
                .projects(projectDTOs)
                .isUnlocked(isUnlocked)
                // Logic bảo mật: Chỉ hiển thị thông tin liên hệ nếu hồ sơ này đã được mua/mở khóa
                .fullName(isUnlocked ? user.getFullName() : maskText(user.getFullName(), false))
                .email(isUnlocked ? user.getEmail() : maskText(user.getEmail(), true))
                .build();
    }

    @Transactional
    public CandidateProfileDTO saveOrUpdateProfile(Long userId, CandidateProfileDTO dto) {
        // 1. Kiểm tra tài khoản User có tồn tại không
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user is not found"));
        if (user.getRole() != EUserRole.ROLE_CANDIDATE){
            throw new RuntimeException("User with role Candidate can only create profile");
        }

        // 2. Tìm kiếm Profile xem đã tồn tại chưa, nếu chưa thì khởi tạo mới
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElse(new CandidateProfile());

        // 3. Gán dữ liệu cơ bản
        profile.setUser(user);
        profile.setTitle(dto.getTitle());
        profile.setYearsOfExperience(dto.getYearsOfExperience());
        profile.setIsOpenToWork(dto.getIsOpenToWork() != null ? dto.getIsOpenToWork() : false);


        List<Long> skillIds = dto.getSkills().stream()
                .map(SkillDTO::getId)
                .toList();

        List<Skill> skills = skillRepository.findAllById(skillIds);
        profile.setSkills(skills);


        CandidateProfile savedProfile = candidateProfileRepository.save(profile);
        return toDTO(savedProfile);
    }

    public CandidateProfile getProfileByUserId(Long userId) {
        return candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile is not created"));
    }

    // nhà tuyeen dung
    public List<CandidateSearchResponseDTO> searchCandidatesForEmployer(Long employerId, String title, List<Long> skillIds,
                                                                        Integer minYearsOfExperience) {

        userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer is not found"));

        Integer minExp = (minYearsOfExperience != null && minYearsOfExperience >= 0) ? minYearsOfExperience : 0;

        List<CandidateProfile> profiles = candidateProfileRepository.searchCandidates(
                (title != null && !title.trim().isEmpty()) ? title : null,
                (skillIds != null && !skillIds.isEmpty()) ? skillIds : null,
                minExp
        );


        return profiles.stream()
                .map(profile -> {
                    // Kiểm tra xem nhà tuyển dụng này đã unlock ứng viên này chưa
                    boolean isUnlocked = profileUnlockRepository.existsByEmployerIdAndProfileId(employerId, profile.getId());
                    // Gọi hàm toResponseDTO sẵn có để tự động che mờ nếu chưa unlock
                    return toResponseDTO(profile, isUnlocked);
                })
                .toList();
    }

    public CandidateSearchResponseDTO getCandidateProfileDetailForEmployer(Long employerId, Long profileId) {
        userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer is not found"));
        CandidateProfile profile = candidateProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Candidate profile is not found"));

        boolean isUnlocked = profileUnlockRepository.existsByEmployerIdAndProfileId(employerId, profile.getId());

        return toResponseDTO(profile, isUnlocked);
    }

    @Transactional
    public void unlockProfile(Long employerId, Long profileId) {
        // 1. Kiểm tra xem hồ sơ này có tồn tại trong hệ thống không
        candidateProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile is not found"));

        // check info nha tuyen dung
        userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer is not found"));

        // 2. Kiểm tra xem Nhà tuyển dụng này đã mở khóa hồ sơ này trước đó chưa (tránh trùng lặp)
        boolean alreadyUnlocked = profileUnlockRepository.existsByEmployerIdAndProfileId(employerId, profileId);
        if (alreadyUnlocked) {
            throw new RuntimeException("Profile is already unlock");
        }

        ProfileUnlock unlockRecord = ProfileUnlock.builder()
                .employerId(employerId)
                .profileId(profileId)
                .build();

        profileUnlockRepository.save(unlockRecord);
    }

}
