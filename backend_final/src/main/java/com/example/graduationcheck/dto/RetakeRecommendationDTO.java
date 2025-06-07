package com.example.graduationcheck.dto;

import java.util.List;

/**
 * 재수강 추천 결과를 담는 DTO
 */
public class RetakeRecommendationDTO {
    // 원본 lectureId 리스트
    private List<String> retakeLectureIds;
    // 화면에 표시할 과목명(만약 change_named_lecture에 매핑이 있으면 currentLectureName, 없으면 lec.getName())
    private List<String> retakeLectureNames;

    public List<String> getRetakeLectureIds() { return retakeLectureIds; }
    public void setRetakeLectureIds(List<String> retakeLectureIds) { this.retakeLectureIds = retakeLectureIds; }

    public List<String> getRetakeLectureNames() { return retakeLectureNames; }
    public void setRetakeLectureNames(List<String> retakeLectureNames) { this.retakeLectureNames = retakeLectureNames; }
}
