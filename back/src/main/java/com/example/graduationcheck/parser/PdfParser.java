package com.example.graduationcheck.parser;

import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.model.Lecture;
import com.example.graduationcheck.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

@Component
@RequiredArgsConstructor
public class PdfParser {
    private final LectureRepository lectureRepository;

    public List<TranscriptCourse> parse(MultipartFile file, Long userId) {
        List<TranscriptCourse> result = new ArrayList<>();
        try (PDDocument doc = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);

            Pattern coursePattern = Pattern.compile("([가-힣A-Za-z0-9()\s]+)\\s+([A-F][+0-]?)");
            Matcher matcher = coursePattern.matcher(text);

            while (matcher.find()) {
                String courseName = matcher.group(1).trim();
                String grade = matcher.group(2).trim();

                Lecture lecture = lectureRepository.findByName(courseName).orElse(null);

                TranscriptCourse course = TranscriptCourse.builder()
                        .courseName(courseName)
                        .grade(grade)
                        .credit(3)
                        .userId(userId)
                        .lectureId(lecture != null ? lecture.getId() : null)
                        .build();

                result.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
