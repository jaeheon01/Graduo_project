package com.example.graduationcheck.controller;

import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.parser.PdfParser;
import com.example.graduationcheck.repository.TranscriptRepository;
import com.example.graduationcheck.service.GraduationCheckService;
import com.example.graduationcheck.dto.GraduationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/transcript")
@RequiredArgsConstructor
public class TranscriptController {
    private final PdfParser pdfParser;
    private final TranscriptRepository transcriptRepository;
    private final GraduationCheckService graduationCheckService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTranscript(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userId") Long userId) {
        System.out.println("pdf 업로드 요청 받음");
        List<TranscriptCourse> courses = pdfParser.parse(file, userId);
        transcriptRepository.saveAll(courses);
        return ResponseEntity.ok("Saved " + courses.size() + " courses.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TranscriptCourse>> getCourses(@PathVariable Long userId) {
        return ResponseEntity.ok(transcriptRepository.findByUserId(userId));
    }

    @GetMapping("/graduation/check/{userId}")
    public ResponseEntity<GraduationResult> checkGraduation(@PathVariable Long userId) {
        GraduationResult result = graduationCheckService.checkGraduation(userId);
        return ResponseEntity.ok(result);
    }
}
