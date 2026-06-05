package com.example.itjobportal.service;

import com.example.itjobportal.dto.ResumeDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.entity.Job;
import com.example.itjobportal.entity.Resume;
import com.example.itjobportal.entity.User;
import com.example.itjobportal.enums.Estatus;
import com.example.itjobportal.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final JobService jobService;
    private final UserService userService;
    private final EmailService emailService;

    private Resume toEntity(ResumeDTO resumeDTO, User user, Job job){
        return Resume.builder()
                .email(user.getEmail())
                .url(resumeDTO.getUrl())
                .status(Estatus.valueOf(resumeDTO.getStatus()))
                .user(user)
                .job(job)
                .build();
    }

    private ResumeDTO toDTO(Resume resume){
        return ResumeDTO.builder()
                .id(resume.getId())
                .email(resume.getEmail())
                .url(resume.getUrl())
                .status(resume.getStatus().name())
                .userId(resume.getUser().getId())
                .userName(resume.getUser().getFullName())
                .jobId(resume.getJob().getId())
                .jobName(resume.getJob().getName())
                .createdAt(resume.getCreatedAt())
                .updateAt(resume.getUpdatedAt())
                .build();
    }

    public ResumeDTO createResume(ResumeDTO resumeDTO, Long jobId){
        User currentUser = userService.getCurrentUser();
        Job job = jobService.findById(jobId);

        Resume resume = Resume.builder()
                .email(currentUser.getEmail())
                .url(resumeDTO.getUrl())
                .status(Estatus.PENDING)
                .user(currentUser)
                .job(job)
                .build();


        Resume savedResume = resumeRepository.save(resume);
        sendApplicationSuccessEmail(currentUser,job, savedResume.getId());
        return toDTO(savedResume);
    }

    private void sendApplicationSuccessEmail(User user, Job job, Long resumeId) {
        try {
            String detailLink = "http://localhost:5454/api/v1.0/candidate/resumes/" + resumeId;
            String subject = "Bạn vừa ứng tuyển thành công vào " + job.getCompany().getCompanyName();
            String body = "Xin chào " + user.getFullName() + ",\n\n" +
                    "Hồ sơ của bạn đã được gửi đến " + job.getCompany().getCompanyName() + ".\n\n" +
                    "Bạn có thể nhấn vào đây để xem chi tiết: " + detailLink;

            emailService.sendEmail(user.getEmail(), subject, body);
        } catch (Exception e) {
            System.err.println("Không thể gửi email thông báo ứng tuyển: " + e.getMessage());
        }
    }



    public Resume getResumeEntityById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume with id: " + id + " is not found"));
    }

    public ResumeDTO findById(Long id) {
        Resume resume = getResumeEntityById(id);
        return toDTO(resume);
    }

    public ResumeDTO updateResumeStatus(Long id, String statusName){
        Resume resume = getResumeEntityById(id);
        Estatus newStatus;
        try {
            newStatus = Estatus.valueOf(statusName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Trạng thái ứng tuyển không hợp lệ: " + statusName);
        }
        resume.setStatus(newStatus);
        Resume updatedResume = resumeRepository.save(resume);
        return toDTO(updatedResume);
    }

    public List<ResumeDTO> getAllByJobId(Long id){
        Job job = jobService.findById(id);
        if (job == null){
            throw new RuntimeException("job with id: "+ id +" is not found");
        }
        List<Resume> resumes = resumeRepository.findByJobId(id);
        return resumes.stream()
                .map(this::toDTO).toList();
    }

    public List<ResumeDTO> getByCurrentUserId(){
        User currentUser = userService.getCurrentUser();
        List<Resume> resumes = resumeRepository.findByUserId(currentUser.getId());
        return resumes.stream()
                .map(this::toDTO).toList();
    }

}
