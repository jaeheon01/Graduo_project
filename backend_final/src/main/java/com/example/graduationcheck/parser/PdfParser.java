package com.example.graduationcheck.parser;

import com.example.graduationcheck.model.TranscriptCourse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class PdfParser {

    public List<TranscriptCourse> parse(MultipartFile file, Long userId) {
        List<TranscriptCourse> courses = new ArrayList<>();
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println("------ PDF에서 추출된 텍스트 ------");
            System.out.println(text);
            System.out.println("----------------------------------");

            String[] lines = text.split("\r?\n");
            for (String line : lines) {
                // 불필요한 라인, 요약/헤더/공백 무시
                if (line.trim().isEmpty()) continue;
                if (line.contains("수강년도") || line.contains("취득학점") || line.contains("총계") || line.contains("성 적 정 보") || line.contains("페이지")) continue;

                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 6) {
                    // 각 컬럼 파싱
                    // 예시: 2021 1학기 교필 A+ 3 한국사와 민족의 발전
                    String yearStr = parts[0].trim();       // 2021
                    String semester = parts[1].trim();      // 1학기
                    // parts[2]: 학수구분
                    String grade = parts[3].trim();         // A+, P, 등
                    // parts[4]: 학점
                    String courseName = String.join(" ", java.util.Arrays.copyOfRange(parts, 5, parts.length)); // 과목명

                    TranscriptCourse tc = new TranscriptCourse();
                    tc.setUserId(userId.intValue());
                    tc.setCourseName(courseName);
                    tc.setGrade(grade);

                    // 수강년도/학기 저장
                    try {
                        tc.setTakenYear(Integer.parseInt(yearStr));
                    } catch (Exception ignore) {
                        tc.setTakenYear(null);
                    }
                    tc.setSemester(semester);

                    courses.add(tc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

}
