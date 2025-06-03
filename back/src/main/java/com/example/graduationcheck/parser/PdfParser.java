package com.example.graduationcheck.parser;

import com.example.graduationcheck.model.TranscriptCourse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.*;

@Component
public class PdfParser {

    public List<TranscriptCourse> parse(MultipartFile file) {
        List<TranscriptCourse> result = new ArrayList<>();
        try (PDDocument doc = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);

            Pattern pattern = Pattern.compile("([가-힣A-Za-z0-9()\\s·]+)\\s+([A-F][+0-]?)");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String name = matcher.group(1).trim();
                String grade = matcher.group(2).trim();

                TranscriptCourse course = TranscriptCourse.builder()
                        .courseName(name)
                        .grade(grade)
                        .credit(3) // 기본값
                        .userId(1L) // 임시값
                        .build();

                result.add(course);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
