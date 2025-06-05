package com.example.graduationcheck.parser;

import com.example.graduationcheck.model.Lecture;
import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
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

            Pattern pattern = Pattern.compile("(.+?)\\s+([ABCDF][+0]?)\\s+(\\d+)");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String courseName = matcher.group(1).trim();
                String grade = matcher.group(2).trim();
                int credit = Integer.parseInt(matcher.group(3));

                // 💡 Lecture 테이블에서 정보 보강
                Optional<Lecture> lectureOpt = lectureRepository.findByCourseName(courseName);
                String subjectField = lectureOpt.map(Lecture::getSubjectField).orElse(null);
                String classification = lectureOpt.map(Lecture::getClassification).orElse(null);

                TranscriptCourse course = TranscriptCourse.builder()
                        .userId(userId)
                        .courseName(courseName)
                        .grade(grade)
                        .credit(credit)
                        .subjectField(subjectField)
                        .classification(classification)
                        .build();

                result.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
