package com.example.graduationcheck.service;

import com.example.graduationcheck.parser.PdfParser;
import com.example.graduationcheck.repository.TranscriptRepository;
import com.example.graduationcheck.model.TranscriptCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TranscriptService {

    @Autowired
    private PdfParser pdfParser;

    @Autowired
    private TranscriptRepository transcriptRepository;

    public String parseAndSaveTranscript(MultipartFile file) {
        List<TranscriptCourse> courses = pdfParser.parse(file);
        transcriptRepository.saveAll(courses);
        return "성적표 파싱 및 저장 완료";
    }
}
