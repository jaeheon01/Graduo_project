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

    public List<TranscriptCourse> parse(MultipartFile file) {
        List<TranscriptCourse> result = new ArrayList<>();

        try (PDDocument doc = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);

            // 1. 성적 과목 + 점수 추출
            // 예: 자료구조 A+, 운영체제 B0 형식
            Pattern coursePattern = Pattern.compile("([가-힣A-Za-z0-9()\\s]+)\\s+([A-F][+0-]?)");
            Matcher courseMatcher = coursePattern.matcher(text);

            while (courseMatcher.find()) {
                String courseName = courseMatcher.group(1).trim();
                String grade = courseMatcher.group(2).trim();

                TranscriptCourse course = new TranscriptCourse();
                course.setCourseName(courseName);
                course.setGrade(grade);
                course.setCredit(3); // PDF에 명시 없으므로 기본값 3 설정
                course.setUserId(1L); // 임시 유저 ID, 실제 매핑 필요
                result.add(course);
            }

            //  2. 영어 인증 통과 여부 (단순 키워드 기반)
            if (text.contains("인증영어성적")) {
                System.out.println("[INFO] 영어 인증 성적이 포함되어 있음");
                // GraduationCheckService에서 추가 조건으로 확인 가능
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
