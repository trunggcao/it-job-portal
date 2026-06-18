package com.example.itjobportal.controller;


import com.example.itjobportal.dto.ProjectDTO;
import com.example.itjobportal.entity.Project;
import com.example.itjobportal.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ProjectDTO> addProject(
            @PathVariable Long userId,
            @RequestBody ProjectDTO dto) {

        Project savedProject = projectService.addProjectToProfile(userId, dto);

        ProjectDTO responseDTO = projectService.toDTO(savedProject);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectDTO dto) {

        Project updatedProject = projectService.updateProject(projectId, dto);
        return ResponseEntity.ok(projectService.toDTO(updatedProject));
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<Map<String, String>> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);

        // Trả về chuỗi JSON thông báo xóa thành công để Frontend tiện bắt logic
        return ResponseEntity.ok(Map.of("message", "Delete Success"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUserId(@PathVariable Long userId) {
        List<Project> projects = projectService.getProjectsByUserId(userId);

        List<ProjectDTO> projectDTOs = projects.stream()
                .map(projectService::toDTO)
                .toList();

        return ResponseEntity.ok(projectDTOs);
    }

}
