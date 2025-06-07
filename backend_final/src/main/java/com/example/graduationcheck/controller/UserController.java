package com.example.graduationcheck.controller;

import com.example.graduationcheck.dto.SignUpRequest;
import com.example.graduationcheck.model.ExternalScore;
import com.example.graduationcheck.model.User;
import com.example.graduationcheck.repository.ExternalScoreRepository;
import com.example.graduationcheck.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ExternalScoreRepository externalScoreRepository;

    /**
     * 회원가입: User + ExternalScore(어학/프로그래밍 점수) 동시 저장
     */
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest req) {
        // 이미 같은 userId가 있으면 에러 반환
        if (userRepository.existsById(req.getUserId())) {
            return ResponseEntity
                    .badRequest()
                    .body("{\"message\":\"이미 존재하는 아이디입니다.\"}");
        }

        // 1) User 저장
        User user = new User();
        user.setUserId(req.getUserId());
        user.setName(req.getName());
        user.setStudentNumber(req.getStudentNumber());
        user.setDepartment(req.getDepartment());
        user.setEntranceYear(req.getEntranceYear());
        user.setIsDualDegree(req.getIsDualDegree());
        userRepository.save(user);

        // 2) ExternalScore 저장: PK인 externalScoreId 필드를 반드시 세팅
        ExternalScore es = new ExternalScore();
        // 외부 점수 PK는 AUTO_INCREMENT가 아니므로, 편의상 회원가입 시 userId와 동일하게 세팅
        es.setExternalScoreId(req.getUserId());
        es.setUserId(req.getUserId());
        es.setEngScoreType(req.getEngScoreType());
        es.setEngScore(req.getEngScore());
        es.setExternalScoreType(req.getExternalScoreType());
        es.setExternalScore(req.getExternalScore());

        // 취득일이 넘어오지 않았으면 오늘 날짜
        if (req.getAquisitionDate() != null && !req.getAquisitionDate().isEmpty()) {
            es.setAquisitionDate(LocalDate.parse(req.getAquisitionDate())); // 형식: yyyy-MM-dd
        } else {
            es.setAquisitionDate(LocalDate.now());
        }

        externalScoreRepository.save(es);

        return ResponseEntity.ok("{\"message\":\"회원가입 성공\"}");
    }

    /**
     * 로그인: userId + studentNumber 일치 여부 확인
     */
    @GetMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam Integer userId,
            @RequestParam String studentNumber) {

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty() ||
            !userOpt.get().getStudentNumber().equals(studentNumber)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 학번이 일치하지 않습니다.");
        }
        return ResponseEntity.ok("로그인 성공");
    }
}
