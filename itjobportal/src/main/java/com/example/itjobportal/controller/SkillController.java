package com.example.itjobportal.controller;

import com.example.itjobportal.dto.SkillDTO;
import com.example.itjobportal.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;

    @PostMapping()
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO){
        SkillDTO newSkill = skillService.createSkill(skillDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
    }

    @GetMapping()
    public ResponseEntity<List<SkillDTO>> getAll(){
        List<SkillDTO> skills = skillService.getAllSkill();
        return ResponseEntity.status(HttpStatus.OK).body(skills);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable Long id, @RequestBody SkillDTO skillDTO){
        SkillDTO skillUpdated = skillService.updateSkill(id,skillDTO);
        return ResponseEntity.status(HttpStatus.OK).body(skillUpdated);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SkillDTO>> findByName(@RequestParam String keyword){
        List<SkillDTO> skills = skillService.findByNameContainingIgnoreCase(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(skills);
    }
}
