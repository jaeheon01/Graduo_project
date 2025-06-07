package com.example.graduationcheck.service;

import com.example.graduationcheck.dto.RequirementStatusDTO;
import com.example.graduationcheck.dto.RetakeRecommendationDTO;
import com.example.graduationcheck.model.ChangeNamedLecture;
import com.example.graduationcheck.model.ExternalScore;
import com.example.graduationcheck.model.GraduationComparativeScore;
import com.example.graduationcheck.model.GraduationReqBasicCourses;
import com.example.graduationcheck.model.GraduationRequirementBase;
import com.example.graduationcheck.model.GraduationRequirementDetail;
import com.example.graduationcheck.model.Lecture;
import com.example.graduationcheck.model.Major;
import com.example.graduationcheck.model.RequiredGeneral;
import com.example.graduationcheck.model.TakenLecture;
import com.example.graduationcheck.model.User;
import com.example.graduationcheck.repository.ChangeNamedLectureRepository;
import com.example.graduationcheck.repository.ExternalScoreRepository;
import com.example.graduationcheck.repository.GraduationComparativeScoreRepository;
import com.example.graduationcheck.repository.GraduationReqBasicCoursesRepository;
import com.example.graduationcheck.repository.GraduationRequirementBaseRepository;
import com.example.graduationcheck.repository.GraduationRequirementDetailRepository;
import com.example.graduationcheck.repository.LectureRepository;
import com.example.graduationcheck.repository.MajorRepository;
import com.example.graduationcheck.repository.RequiredGeneralRepository;
import com.example.graduationcheck.repository.TakenLectureRepository;
import com.example.graduationcheck.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraduationService {

    private static final Logger logger = LoggerFactory.getLogger(GraduationService.class);

    private final UserRepository userRepository;
    private final TakenLectureRepository takenLectureRepository;
    private final LectureRepository lectureRepository;
    private final MajorRepository majorRepository;
    private final ChangeNamedLectureRepository changeNamedLectureRepository;
    private final GraduationRequirementBaseRepository baseRepository;
    private final GraduationRequirementDetailRepository detailRepository;
    private final GraduationReqBasicCoursesRepository basicCoursesRepository;
    private final GraduationComparativeScoreRepository compScoreRepository;
    private final ExternalScoreRepository externalScoreRepository;
    private final RequiredGeneralRepository requiredGeneralRepository;

    public GraduationService(UserRepository userRepository,
                             TakenLectureRepository takenLectureRepository,
                             LectureRepository lectureRepository,
                             MajorRepository majorRepository,
                             ChangeNamedLectureRepository changeNamedLectureRepository,
                             GraduationRequirementBaseRepository baseRepository,
                             GraduationRequirementDetailRepository detailRepository,
                             GraduationReqBasicCoursesRepository basicCoursesRepository,
                             GraduationComparativeScoreRepository compScoreRepository,
                             ExternalScoreRepository externalScoreRepository,
                             RequiredGeneralRepository requiredGeneralRepository) {
        this.userRepository = userRepository;
        this.takenLectureRepository = takenLectureRepository;
        this.lectureRepository = lectureRepository;
        this.majorRepository = majorRepository;
        this.changeNamedLectureRepository = changeNamedLectureRepository;
        this.baseRepository = baseRepository;
        this.detailRepository = detailRepository;
        this.basicCoursesRepository = basicCoursesRepository;
        this.compScoreRepository = compScoreRepository;
        this.externalScoreRepository = externalScoreRepository;
        this.requiredGeneralRepository = requiredGeneralRepository;
    }

    @Transactional
    public RequirementStatusDTO checkRequirements(Integer userId) {
        logger.info("[졸업요건] Service 호출: userId = {}", userId);

        RequirementStatusDTO dto = new RequirementStatusDTO();

        // 1) 사용자 조회
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            logger.warn("[졸업요건] 사용자 조회 실패: userId={} 존재하지 않음", userId);
            return dto;
        }
        User user = userOpt.get();

        // 2) 졸업요건 기준 조회 (Base, Detail)
        GraduationRequirementBase base =
            baseRepository.findByDepartmentAndStartYearLessThanEqualAndEndYearGreaterThanEqual(
                user.getDepartment(), user.getEntranceYear(), user.getEntranceYear());
        if (base == null) {
            logger.warn("[졸업요건] Base 조회 실패: department={}, entranceYear={}",
                        user.getDepartment(), user.getEntranceYear());
            return dto;
        }
        GraduationRequirementDetail detail =
            detailRepository.findByBaseIdAndIsDualDegree(base.getId(), user.getIsDualDegree());
        if (detail == null) {
            logger.warn("[졸업요건] Detail 조회 실패: baseId={}, isDualDegree={}",
                        base.getId(), user.getIsDualDegree());
            return dto;
        }

        // 3) TakenLecture 목록 가져오기
        List<TakenLecture> takenLectures = takenLectureRepository.findByUserId(userId);
        if (takenLectures == null) {
            takenLectures = Collections.emptyList();
        }
        Set<String> takenIds = takenLectures.stream()
            .map(TakenLecture::getLectureId)
            .collect(Collectors.toSet());

        // ─────────────────────────────────────────────────────────────
        // 4) 전공필수 체크 (중복된 과목명 제거 + changeNamedLecture 적용)
        // (A) ChangeNamedLecture 테이블에서 “변경 전” 과목명 → “변경 후” 과목명 매핑 생성
        Map<String, String> prevToCurrentMap = changeNamedLectureRepository.findAll()
            .stream()
            .collect(Collectors.toMap(
                cnl -> cnl.getPrevLectureName().replaceAll("\\s+", ""),
                ChangeNamedLecture::getCurrentLectureName
            ));

        // (B) Major 테이블에서 is_required=true인 lectureId를 모두 가져와서 “중복 제거” 해 둠
        List<Major> rawMajorList = majorRepository.findByIsRequiredTrue();
        Set<String> dedupMajorIds = new LinkedHashSet<>();
        for (Major mj : rawMajorList) {
            String lecId = mj.getLectureId();
            Optional<Lecture> lecOpt = lectureRepository.findById(lecId);
            if (lecOpt.isEmpty()) continue;

            Lecture lec = lecOpt.get();
            String lecName = lec.getName();
            if (lecName == null) {
                continue;
            }
            String lecNameNoSpace = lecName.replaceAll("\\s+", "");

            // “변경 전 과목명”이 맵에 있으면, 실제로는 “변경 후 과목명” 강의 ID를 찾아서 덮어씀
            if (prevToCurrentMap.containsKey(lecNameNoSpace)) {
                String currentName = prevToCurrentMap.get(lecNameNoSpace);
                List<Lecture> matchedCurrent = lectureRepository.findAll().stream()
                    .filter(l -> currentName.equals(l.getName()))
                    .collect(Collectors.toList());
                if (!matchedCurrent.isEmpty()) {
                    dedupMajorIds.add(matchedCurrent.get(0).getLectureId());
                }
            } else {
                dedupMajorIds.add(lecId);
            }
        }

        logger.debug("[전공필수] dedupMajorIds = {}", dedupMajorIds);
        // ─────────────────────────────────────────────────────────────

        // (C) 실제로 “필요 과목” 중에서 유저 학과에 속하는 것만 선별 → taken / missing 분류
        Set<String> countedMajorIds = new LinkedHashSet<>();
        Set<String> takenMajorNames = new LinkedHashSet<>();
        Set<String> missingMajorNames = new LinkedHashSet<>();

        for (String requiredLecId : dedupMajorIds) {
            if (countedMajorIds.contains(requiredLecId)) {
                continue;
            }
            Optional<Lecture> requiredLecOpt = lectureRepository.findById(requiredLecId);
            if (requiredLecOpt.isEmpty()) {
                continue;
            }
            Lecture requiredLec = requiredLecOpt.get();

            // (1) “유저 학과”와 “Lecture.lectureDepartment”가 같아야 전공필수로 간주
            String lecDept = requiredLec.getLectureDepartment();
            if (lecDept == null || !lecDept.equals(user.getDepartment())) {
                continue;
            }

            // (2) 이수 판단: takenIds에 ID가 있으면 통과
            boolean isTaken = false;
            if (takenIds.contains(requiredLecId)) {
                isTaken = true;
            } else {
                // (3) changeNamedLecture(현재 과목명 → 이전 과목명) 으로 “이전에 들었으면 통과” 검사
                ChangeNamedLecture cnl = changeNamedLectureRepository.findByCurrentLectureName(requiredLec.getName());
                if (cnl != null) {
                    String prevNoSpace = cnl.getPrevLectureName().replaceAll("\\s+", "");
                    for (TakenLecture tl : takenLectures) {
                        Optional<Lecture> tlLecOpt = lectureRepository.findById(tl.getLectureId());
                        if (tlLecOpt.isPresent()) {
                            String takenNameNoSpace = tlLecOpt.get().getName().replaceAll("\\s+", "");
                            if (takenNameNoSpace.equals(prevNoSpace)) {
                                isTaken = true;
                                break;
                            }
                        }
                    }
                }
            }

            String displayName = requiredLec.getName();
            if (isTaken) {
                takenMajorNames.add(displayName);
                countedMajorIds.add(requiredLecId);
            } else {
                missingMajorNames.add(displayName);
            }
        }

        logger.debug("[전공필수] takenMajorNames = {}", takenMajorNames);
        logger.debug("[전공필수] missingMajorNames = {}", missingMajorNames);

        List<String> majorTakenList = new ArrayList<>(takenMajorNames);
        List<String> majorMissingList = new ArrayList<>(missingMajorNames);
        dto.setMajorRequiredTotal(majorTakenList.size() + majorMissingList.size());
        dto.setMajorRequiredTaken(majorTakenList);
        dto.setMajorRequiredMissing(majorMissingList);
        dto.setMajorRequiredPassed(majorMissingList.isEmpty());

        // ─────────────────────────────────────────────────────────────
        // 5) 전공학점 체크 & 과목명 수집
        int userMajorCreditSum = 0;
        List<String> majorCreditCourseNames = new ArrayList<>();
        for (String lecId : countedMajorIds) {
            Optional<Lecture> lecOpt = lectureRepository.findById(lecId);
            if (lecOpt.isPresent()) {
                Lecture lec = lecOpt.get();
                int credit = lec.getCredit() == null ? 0 : lec.getCredit();
                userMajorCreditSum += credit;
                majorCreditCourseNames.add(lec.getName());
            }
        }
        int requiredMajorCredit = detail.getMajorCredit();
        dto.setMajorCreditRequired(requiredMajorCredit);
        dto.setMajorCreditCurrent(userMajorCreditSum);
        dto.setMajorCreditPassed(userMajorCreditSum >= requiredMajorCredit);
        dto.setMajorCreditCourseNames(majorCreditCourseNames);

        logger.info("[전공학점] required={}, current={}, passed={}, 과목목록={}",
                    requiredMajorCredit, userMajorCreditSum,
                    (userMajorCreditSum >= requiredMajorCredit), majorCreditCourseNames);

        // ─────────────────────────────────────────────────────────────
       //  [새로 추가]  “산학협력 과목 이수 여부” 계산
        //
        //  a) major 테이블에서 is_industry=true인 lectureId 모두 가져오기
        List<Major> allIndustryMajors = majorRepository.findByIsIndustryTrue();

        //  b) 사용자 학과가 같은 산학협력 과목 중, 사용자가 실제 이수했는지 카운트
        String userDept = user.getDepartment();
        int userIndustryCount = 0;

        for (Major mj : allIndustryMajors) {
            String lecId = mj.getLectureId();
            Optional<Lecture> lecOpt = lectureRepository.findById(lecId);
            if (lecOpt.isEmpty()) {
                continue;
            }
            Lecture lec = lecOpt.get();

            // “lecture_department” 컬럼이 사용자 학과와 같아야 해당 산학협력 과목으로 간주
            if (!userDept.equals(lec.getLectureDepartment())) {
                continue;
            }

            // (1) takenIds에 lecId가 있으면 이미 이수함 → 카운트
            if (takenIds.contains(lecId)) {
                userIndustryCount++;
            }
            else {
                // (2) 이름이 변경된 과목(prev→current)이라면, “prevLectureName”으로도 한 번 검사
                ChangeNamedLecture cnl = changeNamedLectureRepository.findByCurrentLectureName(lec.getName());
                if (cnl != null) {
                    String prevNoSpace = cnl.getPrevLectureName().replaceAll("\\s+", "");
                    for (TakenLecture tl : takenLectures) {
                        Optional<Lecture> tlLecOpt = lectureRepository.findById(tl.getLectureId());
                        if (tlLecOpt.isPresent()) {
                            String takenNameNoSpace = tlLecOpt.get().getName().replaceAll("\\s+", "");
                            if (takenNameNoSpace.equals(prevNoSpace)) {
                                userIndustryCount++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        //  c) “dual‐degree” 여부에 따른 충족 기준 설정
        boolean industryPassed;
        if (Boolean.TRUE.equals(user.getIsDualDegree())) {
            // dual‐degree: 1개 이상 이수해야 충족
            industryPassed = (userIndustryCount >= 1);
        } else {
            // 그 외(dual‐degree=false): 2개 이상 이수해야 충족
            industryPassed = (userIndustryCount >= 2);
        }

        //  d) DTO에 세팅
        dto.setIndustryPassed(industryPassed);
        logger.info("[산학협력] 사용자가 이수한 산학협력 과목 수 = {}, 충족={}", userIndustryCount, industryPassed);
        // ─────────────────────────────────────────────────────────────

        // ─────────────────────────────────────────────────────────────
        // 7) 교양영역 체크
        int distributionCredit = base.getDistributionAreaCredit();
        int areaCount = distributionCredit / 3;
        List<String> allAreas = List.of("역사와 철학", "문학과 예술", "인간과 사회", "연결과 통합");
        List<String> neededAreas = allAreas.subList(0, Math.min(areaCount, allAreas.size()));

        Set<String> takenAreas = new HashSet<>();
        for (TakenLecture tl : takenLectures) {
            requiredGeneralRepository.findById(tl.getLectureId())
                .ifPresent(rg -> takenAreas.add(rg.getSubjectField()));
        }
        List<String> distributionAreaTaken = new ArrayList<>(takenAreas);
        List<String> distributionAreaMissing = neededAreas.stream()
            .filter(area -> !takenAreas.contains(area))
            .collect(Collectors.toList());
        dto.setDistributionAreaTotal(areaCount);
        dto.setDistributionAreaTaken(distributionAreaTaken);
        dto.setDistributionAreaMissing(distributionAreaMissing);
        dto.setDistributionAreaPassed(distributionAreaMissing.isEmpty());

        logger.info("[교양영역] totalArea={}, taken={}, missing={}", areaCount,
                    distributionAreaTaken, distributionAreaMissing);

        // ─────────────────────────────────────────────────────────────
        // 8) 기초교양(교필) 체크
        List<GraduationReqBasicCourses> basicCourses = basicCoursesRepository.findByBaseId(base.getId());
        Set<String> basicTakenNames = new LinkedHashSet<>();
        Set<String> basicMissingNames = new LinkedHashSet<>();
        for (GraduationReqBasicCourses bc : basicCourses) {
            String courseNoSpace = bc.getCourseName().replaceAll("\\s+", "");
            List<Lecture> matchedLecs = lectureRepository.findAll().stream()
                .filter(lec -> lec.getName() != null
                    && lec.getName().replaceAll("\\s+", "").equals(courseNoSpace))
                .collect(Collectors.toList());

            boolean isTaken = matchedLecs.stream()
                .map(Lecture::getLectureId)
                .anyMatch(takenIds::contains);

            if (isTaken) {
                basicTakenNames.add(bc.getCourseName());
            } else {
                basicMissingNames.add(bc.getCourseName());
            }
        }
        dto.setBasicCoursesTotal(basicCourses.size());
        dto.setBasicCoursesTaken(new ArrayList<>(basicTakenNames));
        dto.setBasicCoursesMissing(new ArrayList<>(basicMissingNames));
        dto.setBasicCoursesPassed(basicMissingNames.isEmpty());

        logger.info("[교필] total={}, taken={}, missing={}", basicCourses.size(),
                    basicTakenNames, basicMissingNames);

        // ─────────────────────────────────────────────────────────────
        // 9) 총학점 체크 & 이수 과목 콘솔 출력 (중복된 과목명은 한 번만)
        int totalCreditRequired = base.getTotalCredit();

        Set<String> accountedCourseNamesNoSpace = new HashSet<>();
        int totalCreditCurrent = 0;
        List<String> totalCreditCourseNames = new ArrayList<>();

        for (TakenLecture tl : takenLectures) {
            Optional<Lecture> lecOpt = lectureRepository.findById(tl.getLectureId());
            if (lecOpt.isEmpty()) {
                continue;
            }
            Lecture lec = lecOpt.get();
            String rawName = lec.getName();
            if (rawName == null) {
                continue;
            }
            String noSpaceName = rawName.replaceAll("\\s+", "");

            if (accountedCourseNamesNoSpace.contains(noSpaceName)) {
                continue;
            }

            int credit = lec.getCredit() == null ? 0 : lec.getCredit();
            totalCreditCurrent += credit;
            totalCreditCourseNames.add(rawName);
            accountedCourseNamesNoSpace.add(noSpaceName);

            logger.info("[총학점] 포함된 과목: {} ({}학점)", rawName, credit);
        }

        int totalCreditMissing = Math.max(0, totalCreditRequired - totalCreditCurrent);
        dto.setTotalCreditRequired(totalCreditRequired);
        dto.setTotalCreditCurrent(totalCreditCurrent);
        dto.setTotalCreditMissing(totalCreditMissing);
        dto.setTotalCreditPassed(totalCreditMissing == 0);
        dto.setTotalCreditCourseNames(totalCreditCourseNames);

        logger.info("[총학점] required={}, current={}, missing={}",
                    totalCreditRequired, totalCreditCurrent, totalCreditMissing);
        logger.info("[총학점] 과목목록={}", totalCreditCourseNames);

        // ─────────────────────────────────────────────────────────────
        // 10) 비교과(어학·프로그래밍) 체크
        Optional<GraduationComparativeScore> compOpt = compScoreRepository.findById(base.getId()); // base.getId() 사용
        List<ExternalScore> externalScores = externalScoreRepository.findByUserId(userId);

        // (디버깅) 조회된 ExternalScore 엔티티 전체 출력
        logger.info("[Comparative] externalScores found = {}", externalScores.size());
        for (ExternalScore es : externalScores) {
            logger.info("  → ExternalScore { id={}, userId={}, engType='{}', engScore={}, extType='{}', extScore={}, date={} }",
                es.getExternalScoreId(),
                es.getUserId(),
                es.getEngScoreType(),
                es.getEngScore(),
                es.getExternalScoreType(),
                es.getExternalScore(),
                es.getAquisitionDate()
            );
        }

        if (compOpt.isPresent()) {
            GraduationComparativeScore comp = compOpt.get();

            // --- (A) TOEIC 기준과 사용자 점수 ---
            int requiredToeic = comp.getToeic() != null ? comp.getToeic() : 0;
            int userToeic = externalScores.stream()
                .filter(es -> es.getEngScoreType() != null && es.getEngScoreType().equalsIgnoreCase("TOEIC"))
                .map(ExternalScore::getEngScore)
                .findFirst()
                .orElse(-1);
            dto.setRequiredToeic(requiredToeic);
            dto.setUserToeic(userToeic < 0 ? 0 : userToeic);
            boolean toeicPassed = userToeic >= requiredToeic;
            logger.info("TOEIC: required={}, user={}, passed={}", requiredToeic, userToeic, toeicPassed);

            // --- (B) TEPS 기준과 사용자 점수 ---
            int requiredTeps = comp.getTeps() != null ? comp.getTeps() : 0;
            int userTeps = externalScores.stream()
                .filter(es -> es.getEngScoreType() != null && es.getEngScoreType().equalsIgnoreCase("TEPS"))
                .map(ExternalScore::getEngScore)
                .findFirst()
                .orElse(-1);
            dto.setRequiredTeps(requiredTeps);
            dto.setUserTeps(userTeps < 0 ? 0 : userTeps);
            boolean tepsPassed = userTeps >= requiredTeps;
            logger.info("TEPS: required={}, user={}, passed={}", requiredTeps, userTeps, tepsPassed);

            // --- (C) OPIC 기준과 사용자 점수 ---
            int requiredOpic = comp.getOpic() != null ? comp.getOpic() : 0;
            int userOpic = externalScores.stream()
                .filter(es -> es.getEngScoreType() != null && es.getEngScoreType().equalsIgnoreCase("OPIC"))
                .map(ExternalScore::getEngScore)
                .findFirst()
                .orElse(-1);
            dto.setRequiredOpic(requiredOpic);
            dto.setUserOpic(userOpic < 0 ? 0 : userOpic);
            boolean opicPassed = userOpic >= requiredOpic;
            logger.info("OPIC: required={}, user={}, passed={}", requiredOpic, userOpic, opicPassed);

            // --- (D) TOEIC_SPEAKING 기준과 사용자 점수 ---
            int requiredToeicSp = comp.getToeicSpeaking() != null ? comp.getToeicSpeaking() : 0;
            int userToeicSp = externalScores.stream()
                .filter(es -> es.getEngScoreType() != null && es.getEngScoreType().equalsIgnoreCase("TOEIC_SPEAKING"))
                .map(ExternalScore::getEngScore)
                .findFirst()
                .orElse(-1);
            dto.setRequiredToeicSpeaking(requiredToeicSp);
            dto.setUserToeicSpeaking(userToeicSp < 0 ? 0 : userToeicSp);
            boolean toeicSpeakingPassed = userToeicSp >= requiredToeicSp;
            logger.info("TOEIC_SPEAKING: required={}, user={}, passed={}", requiredToeicSp, userToeicSp, toeicSpeakingPassed);

            // ────────────────────────────────────────────────
            // (1) 어학 통과 여부: 네 가지 중 하나라도 통과하면 true
            boolean languagePassed = toeicPassed || tepsPassed || opicPassed || toeicSpeakingPassed;
            dto.setLanguagePassed(languagePassed);
            logger.info("LANGUAGE overall passed = {}", languagePassed);

            // --- (E) TOPCIT 기준과 사용자 점수 ---
            int requiredTopcit = comp.getTopcit() != null ? comp.getTopcit() : 0;
            int userTopcit = externalScores.stream()
                .filter(es -> es.getExternalScoreType() != null && es.getExternalScoreType().equalsIgnoreCase("TOPCIT"))
                .map(ExternalScore::getExternalScore)
                .findFirst()
                .orElse(-1);
            dto.setRequiredTopcit(requiredTopcit);
            dto.setUserTopcit(userTopcit < 0 ? 0 : userTopcit);
            boolean topcitPassed = userTopcit >= requiredTopcit;
            logger.info("TOPCIT: required={}, user={}, passed={}", requiredTopcit, userTopcit, topcitPassed);

            // --- (F) APC 기준과 사용자 점수 ---
            int requiredApc = comp.getApc() != null ? comp.getApc() : 0;
            int userApc = externalScores.stream()
                .filter(es -> es.getExternalScoreType() != null && es.getExternalScoreType().equalsIgnoreCase("APC"))
                .map(ExternalScore::getExternalScore)
                .findFirst()
                .orElse(-1);
            dto.setRequiredApc(requiredApc);
            dto.setUserApc(userApc < 0 ? 0 : userApc);
            boolean apcPassed = userApc >= requiredApc;
            logger.info("APC: required={}, user={}, passed={}", requiredApc, userApc, apcPassed);

            // ────────────────────────────────────────────────
            // (2) 프로그래밍 통과 여부: TOPCIT 또는 APC 하나라도 통과하면 true
            boolean programmingPassed = topcitPassed || apcPassed;
            dto.setProgrammingPassed(programmingPassed);
            logger.info("PROGRAMMING overall passed = {}", programmingPassed);

            // ────────────────────────────────────────────────
            // (3) 최종 비교과 통과 여부: 어학 && 프로그래밍 모두 통과
            boolean comparativePassed = languagePassed && programmingPassed;
            dto.setComparativePassed(comparativePassed);
            logger.info("COMPARATIVE overall passed = {}", comparativePassed);

        } else {
            logger.info("[Comparative] GraduationComparativeScore 레코드가 없어 비교과 체크 생략");
        }

        // 최종 DTO 상태 로그
        logger.info("[졸업요건] 최종 RequirementStatusDTO = {}", dto);
        return dto;
    }

    @Transactional
    public RetakeRecommendationDTO getRetakeRecommendations(Integer userId) {
        List<TakenLecture> takenLectures = takenLectureRepository.findByUserId(userId);
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for (TakenLecture tl : takenLectures) {
            String grade = tl.getScore();
            if ("C+".equals(grade) || "C0".equals(grade) || "F".equals(grade)) {
                String originalLectureId = tl.getLectureId();
                Optional<Lecture> lecOpt = lectureRepository.findById(originalLectureId);

                if (lecOpt.isPresent()) {
                    Lecture lec = lecOpt.get();
                    String displayName = lec.getName();
                    ChangeNamedLecture cnl = changeNamedLectureRepository.findByPrevLectureName(displayName);
                    if (cnl != null) {
                        displayName = cnl.getCurrentLectureName();
                    }
                    ids.add(originalLectureId);
                    names.add(displayName);
                } else {
                    ids.add(originalLectureId);
                    names.add(originalLectureId);
                }
            }
        }

        RetakeRecommendationDTO dto = new RetakeRecommendationDTO();
        dto.setRetakeLectureIds(ids);
        dto.setRetakeLectureNames(names);
        return dto;
    }
}
