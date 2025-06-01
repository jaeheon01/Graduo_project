SELECT 
    l.lecture_id,
    l.name AS lecture_name,
    l.credit,
    l.code AS class_number,
    l.semester AS year,
    l.lecture_department,

    m.is_required,
    m.start_year AS major_start_year,
    m.end_year AS major_end_year,
    m.is_industry,

    g.is_field_required,
    g.subject_field,
    g.start_student_num,
    g.end_student_num

FROM GRADUO.lecture l
LEFT JOIN GRADUO.major m ON l.lecture_id = m.lecture_id
LEFT JOIN GRADUO.required_general g ON l.lecture_id = g.lecture_id
ORDER BY l.lecture_id;
