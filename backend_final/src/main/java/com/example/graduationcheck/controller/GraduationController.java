package com.example.graduationcheck.controller;

import com.example.graduationcheck.dto.RequirementStatusDTO;
import com.example.graduationcheck.dto.RetakeRecommendationDTO;
import com.example.graduationcheck.service.GraduationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/graduation")
@RequiredArgsConstructor
public class GraduationController {

    private static final Logger logger = LoggerFactory.getLogger(GraduationController.class);
    private final GraduationService graduationService;

    @GetMapping("/{userId}/requirements")
    public RequirementStatusDTO checkRequirements(@PathVariable Integer userId) {
        // 요청 들어온 userId 확인
        logger.info("[졸업요건] 요청 userId = {}", userId);

        // Service 호출
        RequirementStatusDTO result = graduationService.checkRequirements(userId);

        // Service 결과 값 확인
        logger.info("[졸업요건] Controller에서 반환 RequirementStatusDTO = {}", result);
        return result;
    }

    @GetMapping("/{userId}/retake")
    public RetakeRecommendationDTO getRetakeRecommendations(@PathVariable Integer userId) {
        // 요청 들어온 userId 확인
        logger.info("[재수강추천] 요청 userId = {}", userId);

        // Service 호출
        RetakeRecommendationDTO recommendations = graduationService.getRetakeRecommendations(userId);

        // Service 결과 값 확인
        logger.info("[재수강추천] Controller에서 반환 RetakeRecommendationDTO = {}", recommendations);
        return recommendations;
    }
}
