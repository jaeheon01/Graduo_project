package com.example.graduationcheck.controller;

import com.example.graduationcheck.model.Lecture;
import com.example.graduationcheck.model.TakenLecture;
import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.model.User;
import com.example.graduationcheck.repository.LectureRepository;
import com.example.graduationcheck.repository.TakenLectureRepository;
import com.example.graduationcheck.repository.UserRepository;
import com.example.graduationcheck.service.GraduationService;
import com.example.graduationcheck.parser.PdfParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TranscriptController {

    private final PdfParser pdfParser;
    private final LectureRepository lectureRepository;
    private final TakenLectureRepository takenLectureRepository;
    private final UserRepository userRepository;
    private final GraduationService graduationService;

    @PostMapping("/transcript/upload")
    public ResponseEntity<?> uploadTranscript(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) {
        Integer userIdInt = userId.intValue();
        Optional<User> userOpt = userRepository.findById(userIdInt);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("유저를 찾을 수 없습니다.");
        }
        String userDept = userOpt.get().getDepartment();

        // PDF 파싱하여 TranscriptCourse 리스트 반환
        List<TranscriptCourse> courses = pdfParser.parse(file, userId);

        // 기존에 저장된 TakenLecture 삭제
        takenLectureRepository.deleteByUserId(userIdInt);

        List<TakenLecture> toSave = new ArrayList<>();
        for (TranscriptCourse tc : courses) {
            // 1) 파싱된 과목명에서 공백(띄어쓰기) 제거
            String parsedNameNoSpace = tc.getCourseName().replaceAll("\\s+", "");

            // 2) DB에 저장된 모든 Lecture 중, “공백 제거 후” 이름이 동일한 것만 필터
            List<Lecture> matched = lectureRepository.findAll().stream()
                .filter(lec -> lec.getName() != null
                    && lec.getName().replaceAll("\\s+", "").equals(parsedNameNoSpace))
                .collect(Collectors.toList());

            if (matched.isEmpty()) {
                // 해당 과목명이 DB에 없으면 skip
                continue;
            }

            // 3) matched 중 사용자 학과가 같은 강의가 있으면 그쪽을 우선 선택, 없으면 matched 전체 사용
            List<Lecture> filteredByDept = matched.stream()
                .filter(lec -> lec.getLectureDepartment() != null
                             && lec.getLectureDepartment().equals(userDept))
                .collect(Collectors.toList());
            List<Lecture> lecturesToUse = filteredByDept.isEmpty() ? matched : filteredByDept;

            // 4) lecturesToUse 리스트에 있는 모든 lectureId로 TakenLecture 엔티티 생성
            for (Lecture lec : lecturesToUse) {
                TakenLecture tl = new TakenLecture();
                tl.setUserId(tc.getUserId());
                tl.setLectureId(lec.getLectureId());
                tl.setScore(tc.getGrade());
                tl.setTakenYear(tc.getTakenYear());
                // 필요하다면 아래 줄을 추가하여 semester도 저장
                // tl.setSemester(tc.getSemester());
                toSave.add(tl);
            }
        }

        takenLectureRepository.saveAll(toSave);
        return ResponseEntity.ok("Saved " + toSave.size() + " taken lectures.");
    }

    @GetMapping("/transcript/{userId}")
    public List<TakenLecture> getTakenLectures(@PathVariable Integer userId) {
        return takenLectureRepository.findByUserId(userId);
    }

}
