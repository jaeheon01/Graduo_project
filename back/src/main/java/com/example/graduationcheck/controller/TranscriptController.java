package com.example.graduationcheck.controller;

import com.example.graduationcheck.service.TranscriptService;
import com.example.graduationcheck.service.GraduationCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class TranscriptController {

    @Autowired
    private TranscriptService transcriptService;

    @Autowired
    private GraduationCheckService graduationCheckService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTranscript(@RequestParam("file") MultipartFile file) {
        String result = transcriptService.parseAndSaveTranscript(file);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkGraduation(@PathVariable Long userId) {
        return ResponseEntity.ok(graduationCheckService.checkGraduation(userId));
    }
}

