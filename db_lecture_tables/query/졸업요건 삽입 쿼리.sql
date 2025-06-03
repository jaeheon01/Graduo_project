-- base 테이블에 ID를 0,1로 직접 삽입
INSERT INTO GRADUO.graduation_requirement_base
    (id, department, start_year, end_year, total_credit, distribution_area_credit)
VALUES
    (0, 'software', 2021, 2024, 140, 9),
    (1, 'software', 2025, 2025, 128, 12);

-- detail 테이블에 base_id = 0,1을 그대로 사용하여 삽입
INSERT INTO GRADUO.graduation_requirement_detail
    (base_id, is_dual_degree, major_credit, industrial_course_count)
VALUES
    (0, TRUE,  46, 1),   -- 2021–2024, 복수전공
    (0, FALSE, 73, 2),   -- 2021–2024, 단일전공
    (1, TRUE,  42, 1),   -- 2025–2025, 복수전공
    (1, FALSE, 64, 2);   -- 2025–2025, 단일전공

INSERT INTO GRADUO.graduation_comparative_score
    (id, toeic, teps, toeic_speaking, opic, topcit, apc)
VALUES
    -- base_id = 0
    (0, 730, 605, 110, 100, 190, NULL),
    -- base_id = 1
    (1, 730, 605, 110, 100, 190, 1);
    
INSERT INTO GRADUO.graduation_req_basic_courses (base_id, course_name)
VALUES
    -- base_id = 0
    (0, '아주인성'),
    (0, '영어1'),
    (0, '영어2'),
    (0, '글쓰기'),
    (0, 'SW커리어세미나'),
    (0, '수학1'),
    (0, '수학2'),
    (0, '확률및통계1'),
    (0, '선형대수1'),
    (0, '물리학'),
    (0, '생명과학'),
    (0, '물리학실험'),

    -- base_id = 1
    (1, '아주인-신입생을 위한 마중물'),
    (1, '아주상상프로젝트'),
    (1, '영어'),
    (1, '대학글쓰기'),
    (1, '수학1'),
    (1, '수학2'),
    (1, '물리학'),
    (1, '생명과학'),
    (1, '물리학실험'),
    (1, 'SW커리어세미나'),
    (1, '확률및통계1'),
    (1, '선형대수1');

    

