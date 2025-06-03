package com.example.graduationcheck.controller;

import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.parser.PdfParser;
import com.example.graduationcheck.repository.TranscriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/transcript")
@RequiredArgsConstructor
public class TranscriptController {

    private final PdfParser pdfParser;
    private final TranscriptRepository transcriptRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTranscript(@RequestParam("file") MultipartFile file) {
        List<TranscriptCourse> courses = pdfParser.parse(file);
        transcriptRepository.saveAll(courses);
        return ResponseEntity.ok("Parsed and saved " + courses.size() + " courses.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TranscriptCourse>> getCourses(@PathVariable Long userId) {
        return ResponseEntity.ok(transcriptRepository.findByUserId(userId));
    }
}
