package com.example.graduationcheck.dto;

import java.util.List;

public class RequirementStatusDTO {
    // ─────────────────────────────────────────────────────────────
    // 전공필수
    private boolean majorRequiredPassed;
    private int majorRequiredTotal;
    private List<String> majorRequiredTaken;
    private List<String> majorRequiredMissing;

    // ─────────────────────────────────────────────────────────────
    // 전공학점 (새 필드)
    private int majorCreditRequired;
    private int majorCreditCurrent;
    private boolean majorCreditPassed;
    private List<String> majorCreditCourseNames;

    // ─────────────────────────────────────────────────────────────
    // 산학협력 과목 (새 필드)
    private int industrialCourseRequired;
    private int industrialCourseCurrent;
    private boolean industrialCoursePassed;

    // ─────────────────────────────────────────────────────────────
    // 교양영역
    private boolean distributionAreaPassed;
    private int distributionAreaTotal;
    private List<String> distributionAreaTaken;
    private List<String> distributionAreaMissing;

    // ─────────────────────────────────────────────────────────────
    // 교양필수(기본교양)
    private boolean basicCoursesPassed;
    private int basicCoursesTotal;
    private List<String> basicCoursesTaken;
    private List<String> basicCoursesMissing;

    // ─────────────────────────────────────────────────────────────
    // 총학점 (새 필드)
    private int totalCreditRequired;
    private int totalCreditCurrent;
    private int totalCreditMissing;
    private boolean totalCreditPassed;
    private List<String> totalCreditCourseNames;

    // ─────────────────────────────────────────────────────────────
    // 비교과 (어학 / 프로그래밍 역량)
    private boolean languagePassed;
    private int requiredToeic;
    private int userToeic;
    private int requiredTeps;
    private int userTeps;
    private int requiredOpic;
    private int userOpic;
    private int requiredToeicSpeaking;
    private int userToeicSpeaking;

    private boolean programmingPassed;
    private int requiredTopcit;
    private int userTopcit;
    private int requiredApc;
    private int userApc;

    private boolean comparativePassed;

    // ─────────────────────────────────────────────────────────────
    // getters / setters



    public boolean isMajorRequiredPassed() {
        return majorRequiredPassed;
    }
    public void setMajorRequiredPassed(boolean majorRequiredPassed) {
        this.majorRequiredPassed = majorRequiredPassed;
    }

    public int getMajorRequiredTotal() {
        return majorRequiredTotal;
    }
    public void setMajorRequiredTotal(int majorRequiredTotal) {
        this.majorRequiredTotal = majorRequiredTotal;
    }

    public List<String> getMajorRequiredTaken() {
        return majorRequiredTaken;
    }
    public void setMajorRequiredTaken(List<String> majorRequiredTaken) {
        this.majorRequiredTaken = majorRequiredTaken;
    }

    public List<String> getMajorRequiredMissing() {
        return majorRequiredMissing;
    }
    public void setMajorRequiredMissing(List<String> majorRequiredMissing) {
        this.majorRequiredMissing = majorRequiredMissing;
    }

    public int getMajorCreditRequired() {
        return majorCreditRequired;
    }
    public void setMajorCreditRequired(int majorCreditRequired) {
        this.majorCreditRequired = majorCreditRequired;
    }

    public int getMajorCreditCurrent() {
        return majorCreditCurrent;
    }
    public void setMajorCreditCurrent(int majorCreditCurrent) {
        this.majorCreditCurrent = majorCreditCurrent;
    }

    public boolean isMajorCreditPassed() {
        return majorCreditPassed;
    }
    public void setMajorCreditPassed(boolean majorCreditPassed) {
        this.majorCreditPassed = majorCreditPassed;
    }

    public List<String> getMajorCreditCourseNames() {
        return majorCreditCourseNames;
    }
    public void setMajorCreditCourseNames(List<String> majorCreditCourseNames) {
        this.majorCreditCourseNames = majorCreditCourseNames;
    }

    public int getIndustrialCourseRequired() {
        return industrialCourseRequired;
    }
    public void setIndustrialCourseRequired(int industrialCourseRequired) {
        this.industrialCourseRequired = industrialCourseRequired;
    }

    public int getIndustrialCourseCurrent() {
        return industrialCourseCurrent;
    }
    public void setIndustrialCourseCurrent(int industrialCourseCurrent) {
        this.industrialCourseCurrent = industrialCourseCurrent;
    }

        // ─────────────────────────────────────────────────────────────
    // **새로 추가**: 산학협력 충족 여부
    private boolean industryPassed;

    // ─────────────────────────────────────────────────────────────
    // getters / setters (기존 항목들 생략…)

    public boolean isIndustryPassed() {
        return industryPassed;
    }

    public void setIndustryPassed(boolean industryPassed) {
        this.industryPassed = industryPassed;
    }


    public boolean isIndustrialCoursePassed() {
        return industrialCoursePassed;
    }
    public void setIndustrialCoursePassed(boolean industrialCoursePassed) {
        this.industrialCoursePassed = industrialCoursePassed;
    }

    public boolean isDistributionAreaPassed() {
        return distributionAreaPassed;
    }
    public void setDistributionAreaPassed(boolean distributionAreaPassed) {
        this.distributionAreaPassed = distributionAreaPassed;
    }

    public int getDistributionAreaTotal() {
        return distributionAreaTotal;
    }
    public void setDistributionAreaTotal(int distributionAreaTotal) {
        this.distributionAreaTotal = distributionAreaTotal;
    }

    public List<String> getDistributionAreaTaken() {
        return distributionAreaTaken;
    }
    public void setDistributionAreaTaken(List<String> distributionAreaTaken) {
        this.distributionAreaTaken = distributionAreaTaken;
    }

    public List<String> getDistributionAreaMissing() {
        return distributionAreaMissing;
    }
    public void setDistributionAreaMissing(List<String> distributionAreaMissing) {
        this.distributionAreaMissing = distributionAreaMissing;
    }

    public boolean isBasicCoursesPassed() {
        return basicCoursesPassed;
    }
    public void setBasicCoursesPassed(boolean basicCoursesPassed) {
        this.basicCoursesPassed = basicCoursesPassed;
    }

    public int getBasicCoursesTotal() {
        return basicCoursesTotal;
    }
    public void setBasicCoursesTotal(int basicCoursesTotal) {
        this.basicCoursesTotal = basicCoursesTotal;
    }

    public List<String> getBasicCoursesTaken() {
        return basicCoursesTaken;
    }
    public void setBasicCoursesTaken(List<String> basicCoursesTaken) {
        this.basicCoursesTaken = basicCoursesTaken;
    }

    public List<String> getBasicCoursesMissing() {
        return basicCoursesMissing;
    }
    public void setBasicCoursesMissing(List<String> basicCoursesMissing) {
        this.basicCoursesMissing = basicCoursesMissing;
    }

    public int getTotalCreditRequired() {
        return totalCreditRequired;
    }
    public void setTotalCreditRequired(int totalCreditRequired) {
        this.totalCreditRequired = totalCreditRequired;
    }

    public int getTotalCreditCurrent() {
        return totalCreditCurrent;
    }
    public void setTotalCreditCurrent(int totalCreditCurrent) {
        this.totalCreditCurrent = totalCreditCurrent;
    }

    public int getTotalCreditMissing() {
        return totalCreditMissing;
    }
    public void setTotalCreditMissing(int totalCreditMissing) {
        this.totalCreditMissing = totalCreditMissing;
    }

    public boolean isTotalCreditPassed() {
        return totalCreditPassed;
    }
    public void setTotalCreditPassed(boolean totalCreditPassed) {
        this.totalCreditPassed = totalCreditPassed;
    }

    public List<String> getTotalCreditCourseNames() {
        return totalCreditCourseNames;
    }
    public void setTotalCreditCourseNames(List<String> totalCreditCourseNames) {
        this.totalCreditCourseNames = totalCreditCourseNames;
    }

    public boolean isLanguagePassed() {
        return languagePassed;
    }
    public void setLanguagePassed(boolean languagePassed) {
        this.languagePassed = languagePassed;
    }

    public int getRequiredToeic() {
        return requiredToeic;
    }
    public void setRequiredToeic(int requiredToeic) {
        this.requiredToeic = requiredToeic;
    }

    private int industryCount;


    public int getUserToeic() {
        return userToeic;
    }
    public void setUserToeic(int userToeic) {
        this.userToeic = userToeic;
    }

    public int getRequiredTeps() {
        return requiredTeps;
    }
    public void setRequiredTeps(int requiredTeps) {
        this.requiredTeps = requiredTeps;
    }

    public int getUserTeps() {
        return userTeps;
    }
    public void setUserTeps(int userTeps) {
        this.userTeps = userTeps;
    }

    public int getRequiredOpic() {
        return requiredOpic;
    }
    public void setRequiredOpic(int requiredOpic) {
        this.requiredOpic = requiredOpic;
    }

    public int getUserOpic() {
        return userOpic;
    }
    public void setUserOpic(int userOpic) {
        this.userOpic = userOpic;
    }

    public int getRequiredToeicSpeaking() {
        return requiredToeicSpeaking;
    }
    public void setRequiredToeicSpeaking(int requiredToeicSpeaking) {
        this.requiredToeicSpeaking = requiredToeicSpeaking;
    }

    public int getUserToeicSpeaking() {
        return userToeicSpeaking;
    }
    public void setUserToeicSpeaking(int userToeicSpeaking) {
        this.userToeicSpeaking = userToeicSpeaking;
    }

    public boolean isProgrammingPassed() {
        return programmingPassed;
    }
    public void setProgrammingPassed(boolean programmingPassed) {
        this.programmingPassed = programmingPassed;
    }

    public int getRequiredTopcit() {
        return requiredTopcit;
    }
    public void setRequiredTopcit(int requiredTopcit) {
        this.requiredTopcit = requiredTopcit;
    }

    public int getUserTopcit() {
        return userTopcit;
    }
    public void setUserTopcit(int userTopcit) {
        this.userTopcit = userTopcit;
    }

    public int getRequiredApc() {
        return requiredApc;
    }
    public void setRequiredApc(int requiredApc) {
        this.requiredApc = requiredApc;
    }

    public int getUserApc() {
        return userApc;
    }
    public void setUserApc(int userApc) {
        this.userApc = userApc;
    }

    public boolean isComparativePassed() {
        return comparativePassed;
    }
    public void setComparativePassed(boolean comparativePassed) {
        this.comparativePassed = comparativePassed;
    }

        // 새로 추가한 필드 getter / setter
    public int getIndustryCount() {
        return industryCount;
    }
    public void setIndustryCount(int industryCount) {
        this.industryCount = industryCount;
    }


    // ─────────────────────────────────────────────────────────────
    // toString() 메서드 (디버깅용)
    @Override
    public String toString() {
        return "RequirementStatusDTO{" +
                "majorRequiredPassed=" + majorRequiredPassed +
                ", majorRequiredTotal=" + majorRequiredTotal +
                ", majorRequiredTaken=" + majorRequiredTaken +
                ", majorRequiredMissing=" + majorRequiredMissing +
                ", majorCreditRequired=" + majorCreditRequired +
                ", majorCreditCurrent=" + majorCreditCurrent +
                ", majorCreditPassed=" + majorCreditPassed +
                ", majorCreditCourseNames=" + majorCreditCourseNames +
                ", industrialCourseRequired=" + industrialCourseRequired +
                ", industrialCourseCurrent=" + industrialCourseCurrent +
                ", industrialCoursePassed=" + industrialCoursePassed +
                ", distributionAreaPassed=" + distributionAreaPassed +
                ", distributionAreaTotal=" + distributionAreaTotal +
                ", distributionAreaTaken=" + distributionAreaTaken +
                ", distributionAreaMissing=" + distributionAreaMissing +
                ", basicCoursesPassed=" + basicCoursesPassed +
                ", basicCoursesTotal=" + basicCoursesTotal +
                ", basicCoursesTaken=" + basicCoursesTaken +
                ", basicCoursesMissing=" + basicCoursesMissing +
                ", totalCreditRequired=" + totalCreditRequired +
                ", totalCreditCurrent=" + totalCreditCurrent +
                ", totalCreditMissing=" + totalCreditMissing +
                ", totalCreditPassed=" + totalCreditPassed +
                ", totalCreditCourseNames=" + totalCreditCourseNames +
                ", languagePassed=" + languagePassed +
                ", requiredToeic=" + requiredToeic +
                ", userToeic=" + userToeic +
                ", requiredTeps=" + requiredTeps +
                ", userTeps=" + userTeps +
                ", requiredOpic=" + requiredOpic +
                ", userOpic=" + userOpic +
                ", requiredToeicSpeaking=" + requiredToeicSpeaking +
                ", userToeicSpeaking=" + userToeicSpeaking +
                ", programmingPassed=" + programmingPassed +
                ", requiredTopcit=" + requiredTopcit +
                ", userTopcit=" + userTopcit +
                ", requiredApc=" + requiredApc +
                ", userApc=" + userApc +
                ", comparativePassed=" + comparativePassed +
                '}';
    }
}
