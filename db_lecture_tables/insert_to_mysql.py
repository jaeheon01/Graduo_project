import pandas as pd
import pymysql

def none_if_nan(x):
    return None if pd.isna(x) else x

# ✅ 1. subject_field 포함된 CSV 로드
df = pd.read_csv("./dataset/dataset_v2.csv", encoding="utf-8-sig")
df["학점"] = pd.to_numeric(df["학점"], errors="coerce").fillna(0).astype(int)
df["교과목 년도"] = pd.to_numeric(df["교과목 년도"], errors="coerce")
df["교과목코드"] = df["교과목코드"].astype(str)

# ✅ 2. 산업전공 키워드 정의
industry_keywords = [
    "IT집중교육1", "IT집중교육2", "AI집중교육1", "AI집중교육2",
    "자기주도프로젝트", "자기주도연구1", "자기주도연구2",
    "SW현장실습1", "SW현장실습2", "SW현장실습3", "SW현장실습4", "SW현장실습5", "SW현장실습6",
    "창업실습1", "창업실습2", "SW캡스톤디자인"
]

# ✅ 3. DB 연결
conn = pymysql.connect(
    host="localhost",
    user="root",
    password="0000",
    db="graduo",
    charset="utf8mb4",
    autocommit=True
)
cursor = conn.cursor()

# ✅ 4. lecture 테이블 삽입
insert_lecture = """
INSERT IGNORE INTO GRADUO.lecture
(lecture_id, name, credit, code, year, lecture_department)
VALUES (%s, %s, %s, %s, %s, %s)
"""
for _, row in df.iterrows():
    cursor.execute(insert_lecture, (
        none_if_nan(row["교과목코드"]),
        none_if_nan(row["과목명"]),
        none_if_nan(row["학점"]),
        none_if_nan(row["수강번호"]),
        none_if_nan(str(row["교과목 년도"])),
        none_if_nan(row["개설학과"])
    ))

# ✅ 5. major 테이블 삽입
major_df = df[df["교과구분"].isin(["전필", "전선"])]
for lecture_id in major_df["교과목코드"].unique():
    sub = major_df[major_df["교과목코드"] == lecture_id]
    is_required = 1 if sub.iloc[0]["교과구분"] == "전필" else 0

    is_industry = int(any(k in str(name) for name in sub["과목명"] for k in industry_keywords))

    cursor.execute("""
    INSERT IGNORE INTO GRADUO.major (lecture_id, is_required, is_industry)
    VALUES (%s, %s, %s)
    """, (lecture_id, is_required, is_industry))

# ✅ 6. required_general 테이블 삽입 (subject_field 매핑 성공한 것만)
general_df = df[df["교과구분"].isin(["교필", "교선", "영역별교양"])]
general_df = general_df[general_df["subject_field"].notna()]  # 필터링

for lecture_id in general_df["교과목코드"].unique():
    sub = general_df[general_df["교과목코드"] == lecture_id]
    gubun = sub.iloc[0]["교과구분"]
    is_field_required = 1 if gubun in ["교필", "영역별교양"] else 0
    subject_field = sub.iloc[0]["subject_field"]

    try:
        start = int(sub["교과목 년도"].min()) * 10000
        end = int(sub["교과목 년도"].max()) * 10000 + 9999
    except:
        continue

    cursor.execute("""
        INSERT IGNORE INTO GRADUO.required_general (
            lecture_id, is_field_required, subject_field
        ) VALUES (%s, %s, %s)
        """, (
            lecture_id,
            is_field_required,
            none_if_nan(str(subject_field)) if pd.notna(subject_field) else None
        ))

print(general_df["subject_field"].value_counts(dropna=False))

# 각 과목에 대해 is_industry 업데이트
for name in industry_keywords:
    cursor.execute("""
        UPDATE GRADUO.major
        SET is_industry = 1
        WHERE lecture_id = (
            SELECT lecture_id
            FROM GRADUO.lecture
            WHERE name = %s
            LIMIT 1
        )
    """, (name,))

# ✅ 마무리
cursor.close()
conn.close()
print("✅ subject_field 포함 DB 삽입 완료")
