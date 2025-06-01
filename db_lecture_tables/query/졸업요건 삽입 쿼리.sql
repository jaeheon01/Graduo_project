-- base 테이블에 ID를 0,1로 직접 삽입
INSERT INTO GRADUO.graduation_requirement_base
    (id, department, start_year, end_year, total_credit, basic_credit, distribution_area_credit)
VALUES
    (0, 'software', 2021, 2024, 140, 30, 9),
    (1, 'software', 2025, 2025, 128, 30, 12);

-- detail 테이블에 base_id = 0,1을 그대로 사용하여 삽입
INSERT INTO GRADUO.graduation_requirement_detail
    (base_id, is_dual_degree, major_credit, industrial_course_count)
VALUES
    (0, TRUE,  73, 1),   -- 2021–2024, 복수전공
    (0, FALSE, 46, 2),   -- 2021–2024, 단일전공
    (1, TRUE,  64, 1),   -- 2025–2025, 복수전공
    (1, FALSE, 42, 2);   -- 2025–2025, 단일전공



INSERT INTO GRADUO.graduation_comparative_score
    (id, toeic, teps, toeic_speaking, opic, topcit, apc)
VALUES
    -- base_id = 0
    (0, 730, 605, 110, 100, 190, NULL),
    -- base_id = 1
    (1, 730, 605, 110, 100, 190, 1);
