package com.example.itjobportal.service;


import com.example.itjobportal.dto.ProjectDTO;
import com.example.itjobportal.entity.CandidateProfile;
import com.example.itjobportal.entity.Project;
import com.example.itjobportal.repository.CandidateProfileRepository;
import com.example.itjobportal.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public Project toEntity(ProjectDTO dto, CandidateProfile profile) {
        return Project.builder()
                .projectName(dto.getProjectName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .candidateProfile(profile)
                .build();
    }

    public ProjectDTO toDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .description(project.getDescription())
                .build();
    }

    public Project addProjectToProfile(Long userId, ProjectDTO dto) {
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Create Profile before Project"));

        Project project = toEntity(dto, profile);
        return projectRepository.save(project);
    }

    public Project updateProject(Long projectId, ProjectDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project is not found to update"));

        project.setProjectName(dto.getProjectName());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setDescription(dto.getDescription());

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project is not found to delete");
        }
        projectRepository.deleteById(projectId);
    }

    public List<Project> getProjectsByUserId(Long userId) {
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile is not found"));
        return projectRepository.findByCandidateProfileId(profile.getId());
    }

}
