package com.example.itjobportal.service;

import com.example.itjobportal.dto.SkillDTO;
import com.example.itjobportal.entity.Skill;
import com.example.itjobportal.repository.SkilllRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Skill newSkill = toEnity(skillDTO);
        newSkill = skilllRepository.save(newSkill);

        return toDTO(newSkill);
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
