package com.example.itjobportal.service;

import com.example.itjobportal.dto.SkillDTO;
import com.example.itjobportal.entity.Skill;
import com.example.itjobportal.repository.SkilllRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkilllRepository skilllRepository;

    public Skill toEnity(SkillDTO skillDTO){
        return Skill.builder()
                .id(skillDTO.getId())
                .name(skillDTO.getName())
                .build();
    }

    public SkillDTO toDTO(Skill skill){
        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .createAt(skill.getCreatedAt())
                .updateAt(skill.getUpdatedAt())
                .build();
    }

    public SkillDTO createSkill(SkillDTO skillDTO){

        if (skilllRepository.existsByName(skillDTO.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Skill with name is already exist");
        }
        Skill newSkill = toEnity(skillDTO);
        newSkill = skilllRepository.save(newSkill);

        return toDTO(newSkill);
    }

    public SkillDTO updateSkill(Long id , SkillDTO skillDTO){
        Skill existingSkill = skilllRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill is not found"));
        if (skilllRepository.existsByNameAndIdNot(
                skillDTO.getName(),
                id
        )){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Skill with name is already exist");
        }
        existingSkill.setName(skillDTO.getName());
        existingSkill = skilllRepository.save(existingSkill);
        return toDTO(existingSkill);
    }

    public List<SkillDTO> findByNameContainingIgnoreCase(String keyword){
        List<Skill> skills = skilllRepository.findByNameContainingIgnoreCase(keyword);
        return  skills.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<SkillDTO> getAllSkill(){
        List<Skill> skills = skilllRepository.findAll();
        return skills.stream()
                .map(this::toDTO)
                .toList();
    }
}
