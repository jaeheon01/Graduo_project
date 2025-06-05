package com.example.graduationcheck.controller;

import com.example.graduationcheck.dto.GraduationResult;
import com.example.graduationcheck.service.GraduationCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/graduation")
@RequiredArgsConstructor
public class GraduationController {

    private final GraduationCheckService graduationCheckService;

    @GetMapping("/{userId}")
    public ResponseEntity<GraduationResult> checkGraduation(@PathVariable Long userId) {
        GraduationResult result = graduationCheckService.checkGraduation(userId);
        return ResponseEntity.ok(result);
    }
}
