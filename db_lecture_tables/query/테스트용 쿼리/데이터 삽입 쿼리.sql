## lecture 테이블에 데이터 넣는 쿼리
INSERT INTO GRADUO.lecture (
    lecture_id, name, credit, code, semester
)
SELECT DISTINCT
    raw_lecture_import_2023.num,
    raw_lecture_import_2023.subject_name,
    raw_lecture_import_2023.credit,
    raw_lecture_import_2023.subject_code,
    '2023-1',
    NULL
FROM GRADUO.raw_lecture_import_2023
WHERE class_number IS NOT NULL
  AND class_number NOT IN (SELECT lecture_id FROM GRADUO.lecture);

SET SQL_SAFE_UPDATES = 0;

DELETE FROM GRADUO.major;
DELETE FROM GRADUO.required_general;


## major테이블에 데이터 넣는 쿼리
-- major 테이블 전용: 전공 과목만
INSERT INTO GRADUO.major (
    lecture_id, is_required, start_year, end_year, is_industry
)
SELECT
    num,
    CASE WHEN subject_type LIKE '%전필%' THEN TRUE ELSE FALSE END,
    2023,
    2023,
    CASE
        WHEN subject_name LIKE '%캡스톤%' OR subject_name LIKE '%산학%' OR subject_name LIKE '%프로젝트%'
        THEN TRUE ELSE FALSE
    END
FROM GRADUO.raw_lecture_import_2023
WHERE course_category LIKE '%전공%'
  AND num IN (SELECT lecture_id FROM GRADUO.lecture);



## required_general (교양필수)테이블에 데이터 넣는 쿼리
-- required_general 테이블 전용: 교양 과목만
INSERT INTO GRADUO.required_general (
    lecture_id, is_field_required, subject_field, start_student_num, end_student_num
)
SELECT
    num,
    CASE WHEN subject_type LIKE '%전필%' THEN TRUE ELSE FALSE END,
    CASE 
        WHEN subject_name LIKE '%역사%' OR subject_name LIKE '%철학%' THEN '역사와 철학'
        WHEN subject_name LIKE '%문학%' OR subject_name LIKE '%예술%' THEN '문학과 예술'
        WHEN subject_name LIKE '%사회%' OR subject_name LIKE '%심리%' THEN '인간과 사회'
        WHEN subject_name LIKE '%생물%' OR subject_name LIKE '%물리%' OR subject_name LIKE '%과학%' THEN '자연과 과학'
        ELSE 'Others'
    END,
    20200000,
    20239999
FROM GRADUO.raw_lecture_import_2023
WHERE course_category LIKE '%교양%'
  AND num IN (SELECT lecture_id FROM GRADUO.lecture);
