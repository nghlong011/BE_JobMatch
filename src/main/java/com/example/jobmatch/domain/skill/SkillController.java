package com.example.jobmatch.domain.skill;

import com.example.jobmatch.domain.category.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity create(Principal principal, @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillService.createSkill(principal, skillRequest));
    }

    @DeleteMapping("/delete/{skillId}")
    public ResponseEntity delete(@PathVariable Integer skillId) {
        return ResponseEntity.ok(skillService.deleteSkill(skillId));
    }

    @PutMapping("/update/{skillId}")
    public ResponseEntity update(@PathVariable Integer skillId, @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillService.updateSkill(skillId,skillRequest));
    }

    @GetMapping("/get")
    public ResponseEntity get(Principal principal) {
        return ResponseEntity.ok(skillService.getSkill(principal));
    }
}
