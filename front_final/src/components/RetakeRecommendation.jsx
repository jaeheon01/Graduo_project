import React from "react";

export default function RetakeRecommendation({ retake }) {
  // retake가 null/undefined면 아무 것도 렌더링하지 않음
  if (!retake) return null;

  // 안전하게 배열로 초기화
  const ids = Array.isArray(retake.retakeLectureIds)
    ? retake.retakeLectureIds
    : [];
  const names = Array.isArray(retake.retakeLectureNames)
    ? retake.retakeLectureNames
    : [];

  // 두 배열 중 하나라도 비어 있으면 “추천 없음” 메시지
  if (names.length === 0 || ids.length === 0) {
    return (
      <div className="bg-white rounded-2xl shadow p-6 mt-6">
        <div className="font-semibold text-gray-700">
          재수강 추천 과목이 없습니다.
        </div>
      </div>
    );
  }

  return (
    <div className="bg-white rounded-2xl shadow p-6 mt-6">
      <div className="text-xl font-bold text-blue-700 mb-2">
        재수강 추천 과목
      </div>
      <ul className="space-y-2">
        {names.map((name, idx) => (
          <li
            key={ids[idx] ?? idx}
            className="flex justify-between items-center p-2 rounded-lg bg-yellow-50"
          >
            <span>{name}</span>
            <span className="font-semibold text-gray-600">
              ID: {ids[idx] ?? "-"}
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
}
