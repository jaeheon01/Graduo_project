// src/components/GraduationStatus.jsx
import React, { useState } from "react";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const COLORS = ["#4caf50", "#e0e0e0"];

export default function GraduationStatus({ status }) {
  if (!status) return null;

  // 변수 정의 (백엔드 동기화)
  const majorTaken = Array.isArray(status.majorRequiredTaken) ? status.majorRequiredTaken : [];
  const majorTotal = typeof status.majorRequiredTotal === "number" ? status.majorRequiredTotal : 0;
  const majorMissing = Array.isArray(status.majorRequiredMissing) ? status.majorRequiredMissing : [];
  const majorPassed = Boolean(status.majorRequiredPassed);

  const distTaken = Array.isArray(status.distributionAreaTaken) ? status.distributionAreaTaken : [];
  const distTotal = typeof status.distributionAreaTotal === "number" ? status.distributionAreaTotal : 0;
  const distMissing = Array.isArray(status.distributionAreaMissing) ? status.distributionAreaMissing : [];
  const distPassed = Boolean(status.distributionAreaPassed);

  const basicTaken = Array.isArray(status.basicCoursesTaken) ? status.basicCoursesTaken : [];
  const basicTotal = typeof status.basicCoursesTotal === "number" ? status.basicCoursesTotal : 0;
  const basicMissing = Array.isArray(status.basicCoursesMissing) ? status.basicCoursesMissing : [];
  const basicPassed = Boolean(status.basicCoursesPassed);

  const majorCreditCurrent = typeof status.majorCreditCurrent === "number" ? status.majorCreditCurrent : 0;
  const majorCreditTotal = typeof status.majorCreditRequired === "number" ? status.majorCreditRequired : 0;
  const majorCreditPassed = Boolean(status.majorCreditPassed);

  const totalCreditCurrent = typeof status.totalCreditCurrent === "number" ? status.totalCreditCurrent : 0;
  const totalCreditTotal = typeof status.totalCreditRequired === "number" ? status.totalCreditRequired : 0;
  const totalCreditPassed = Boolean(status.totalCreditPassed);

  const engPassed = Boolean(status.languagePassed);
  
  const progPassed = Boolean(status.programmingPassed);
  
  const industryPassed = Boolean(status.industryPassed);
  
  const categories = [
    {
      title: "전공필수",
      taken: majorTaken.length,
      total: majorTotal,
      passed: majorPassed,
      missing: majorMissing,
    },
    {
      title: "교양영역",
      taken: distTaken.length,
      total: distTotal,
      passed: distPassed,
      missing: distMissing,
    },
    {
      title: "교양필수",
      taken: basicTaken.length,
      total: basicTotal,
      passed: basicPassed,
      missing: basicMissing,
    },
    {
      title: "전공학점",
      taken: majorCreditCurrent,
      total: majorCreditTotal,
      passed: majorCreditPassed,
      missing: [],
    },
    {
      title: "총학점",
      taken: totalCreditCurrent,
      total: totalCreditTotal,
      passed: totalCreditPassed,
      missing: [],
    },
  ];

  const [openIndex, setOpenIndex] = useState(null);

  const toggle = (index) => {
    setOpenIndex(openIndex === index ? null : index);
  };

  return (
    <div className="flex flex-col items-center gap-8">
      {/* 도넛형 카드 영역 */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
        {categories.map((cat, index) => {
          const percent = Math.min(
            Math.round((cat.taken / cat.total) * 100),
            100
          );

          return (
            <div
              key={index}
              className={`w-[200px] p-4 border rounded-lg cursor-pointer shadow-sm hover:shadow-md transition-all duration-300 text-sm ${
                cat.passed ? "bg-green-50" : "bg-red-50"
              }`}
              onClick={() => toggle(index)}
            >
              <div className="font-semibold text-center mb-2">{cat.title}</div>

              <div className="w-36 h-36 mx-auto">
                <ResponsiveContainer width="100%" height="100%">
                  <PieChart>
                    <Pie
                      dataKey="value"
                      data={[
                        { name: "이수", value: cat.taken },
                        { name: "미이수", value: Math.max(cat.total - cat.taken, 0) },
                      ]}
                      innerRadius={35}
                      outerRadius={50}
                    >
                      {COLORS.map((color, idx) => (
                        <Cell key={`cell-${idx}`} fill={color} />
                      ))}
                    </Pie>
                    <Tooltip />
                  </PieChart>
                </ResponsiveContainer>
              </div>

              <div className="text-center mt-2">
                {cat.passed ? (
                  <span className="text-green-600 font-bold">충족</span>
                ) : (
                  <span className="text-red-600 font-bold">미달</span>
                )}
              </div>

              {openIndex === index && (
                <div className="mt-2 text-xs text-gray-700 animate-fadeIn">
                  <div>
                    총 {cat.total} 중 {cat.taken}개 이수 ({percent}% 완료)
                  </div>
                  {!cat.passed && cat.missing.length > 0 && (
                    <div className="mt-1">
                      누락 항목: {cat.missing.join(", ")}
                    </div>
                  )}
                </div>
              )}
            </div>
          );
        })}
      </div>

      {/* P/F 카드 영역 */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
        {/* 외부 어학 */}
        <div className={`w-[200px] p-4 border rounded-lg text-sm shadow-sm flex flex-col justify-between ${
          engPassed ? "bg-green-50" : "bg-red-50"
        }`}>
          <div className="font-semibold text-center mb-2">외부 어학 요건</div>
          <div className="text-center text-lg font-bold">
            {engPassed ? <span className="text-green-600">P</span> : <span className="text-red-600">F</span>}
          </div>
          {!engPassed && (
            <div className="text-xs text-gray-700 mt-2"></div>
          )}
        </div>

        {/* 외부 프로그래밍 */}
        <div className={`w-[200px] p-4 border rounded-lg text-sm shadow-sm flex flex-col justify-between ${
          progPassed ? "bg-green-50" : "bg-red-50"
        }`}>
          <div className="font-semibold text-center mb-2">외부 프로그래밍 요건</div>
          <div className="text-center text-lg font-bold">
            {progPassed ? <span className="text-green-600">P</span> : <span className="text-red-600">F</span>}
          </div>
          {!progPassed && (
            <div className="text-xs text-gray-700 mt-2"></div>
          )}
        </div>

        {/* 산학협력 */}
        <div className={`w-[200px] p-4 border rounded-lg text-sm shadow-sm flex flex-col justify-between ${
          industryPassed ? "bg-green-50" : "bg-red-50"
        }`}>
          <div className="font-semibold text-center mb-2">산학협력 요건</div>
          <div className="text-center text-lg font-bold">
            {industryPassed ? <span className="text-green-600">P</span> : <span className="text-red-600">F</span>}
          </div>
          {!industryPassed && (
            <div className="text-xs text-gray-700 mt-2"></div>
          )}
        </div>
      </div>
    </div>
  );
}
