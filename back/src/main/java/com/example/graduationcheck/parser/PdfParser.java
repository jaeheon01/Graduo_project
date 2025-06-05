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
    public List<TranscriptCourse> parse(MultipartFile file, Long userId) {
        List<TranscriptCourse> result = new ArrayList<>();
        try (PDDocument doc = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);

            Pattern pattern = Pattern.compile("(.+?)\\s+([ABCDF][+0]?)\\s+(\\d+)");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String courseName = matcher.group(1).trim();
                String grade = matcher.group(2).trim();
                int credit = Integer.parseInt(matcher.group(3));
                TranscriptCourse course = TranscriptCourse.builder()
                        .userId(userId)
                        .courseName(courseName)
                        .grade(grade)
                        .credit(credit)
                        .build();
                result.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
