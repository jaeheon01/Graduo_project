// parser/PdfParser.java
package com.example.graduationcheck.parser;

import com.example.graduationcheck.model.TranscriptCourse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

@Component
public class PdfParser {
    public List<TranscriptCourse> parse(MultipartFile file, Long userId) {
        List<TranscriptCourse> result = new ArrayList<>();
        try (PDDocument doc = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);

            Pattern coursePattern = Pattern.compile("([가-힣A-Za-z0-9()\s]+)\\s+([A-F][+0-]?)");
            Matcher courseMatcher = coursePattern.matcher(text);

            while (courseMatcher.find()) {
                String courseName = courseMatcher.group(1).trim();
                String grade = courseMatcher.group(2).trim();
                TranscriptCourse course = TranscriptCourse.builder()
                        .courseName(courseName)
                        .grade(grade)
                        .credit(3)
                        .userId(userId)
                        .build();
                result.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}