package com.example.jobmatch.domain.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity create(Principal principal, @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillService.createSkill(principal, skillRequest));
    }

    @DeleteMapping("/delete/{skillId}")
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity delete(@PathVariable Integer skillId) {
        return ResponseEntity.ok(skillService.deleteSkill(skillId));
    }

    @PutMapping("/update/{skillId}")
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity update(@PathVariable Integer skillId, @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillService.updateSkill(skillId,skillRequest));
    }

    @GetMapping("/get")
    public ResponseEntity get(Principal principal) {
        return ResponseEntity.ok(skillService.getSkill(principal));
    }
}
