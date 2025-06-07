import React, { useState } from "react";
import { register } from "../api/api";
import { useNavigate } from "react-router-dom";

export default function RegisterForm() {
  const [form, setForm] = useState({
    userId: "",
    name: "",
    studentNumber: "",
    department: "",
    entranceYear: "",
    isDualDegree: false,
    engScoreType: "",
    engScore: "",
    externalScoreType: "",
    externalScore: "",
    aquisitionDate: "", // optional
  });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };
// 예시: src/components/RegisterForm.jsx
// const handleSubmit = async (e) => {
//   e.preventDefault();
//   console.log("▶▶▶ register() called, payload:", form);
//   try {
//     await register(form);
//     navigate("/login");
//   } catch (err) {
//     setError(err.message);
//   }
// };
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      // 숫자 필드들은 parseInt
      const payload = {
        userId: parseInt(form.userId, 10),
        name: form.name,
        studentNumber: form.studentNumber,
        department: form.department,
        entranceYear: parseInt(form.entranceYear, 10),
        isDualDegree: form.isDualDegree,
        engScoreType: form.engScoreType,
        engScore: parseInt(form.engScore, 10),
        externalScoreType: form.externalScoreType,
        externalScore: parseInt(form.externalScore, 10),
        // aquisitionDate는 빈 문자열이면 제외
        ...(form.aquisitionDate ? { aquisitionDate: form.aquisitionDate } : {}),
      };
      await register(payload);
      navigate("/login");
    } catch (err) {
      setError(err.message || "회원가입 실패: 정보를 확인해주세요.");
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-white rounded-2xl shadow p-8 flex flex-col gap-4"
    >
      <div className="text-2xl font-bold text-center mb-2 text-blue-700">
        회원가입
      </div>

      <input
        name="userId"
        type="number"
        className="border rounded-xl px-4 py-2"
        placeholder="User ID (숫자)"
        value={form.userId}
        onChange={handleChange}
        required
      />
      <input
        name="name"
        type="text"
        className="border rounded-xl px-4 py-2"
        placeholder="이름"
        value={form.name}
        onChange={handleChange}
        required
      />
      <input
        name="studentNumber"
        type="text"
        className="border rounded-xl px-4 py-2"
        placeholder="학번"
        value={form.studentNumber}
        onChange={handleChange}
        required
      />
      <input
        name="department"
        type="text"
        className="border rounded-xl px-4 py-2"
        placeholder="학과"
        value={form.department}
        onChange={handleChange}
        required
      />
      <input
        name="entranceYear"
        type="number"
        className="border rounded-xl px-4 py-2"
        placeholder="입학년도 (예: 2021)"
        value={form.entranceYear}
        onChange={handleChange}
        required
      />

      <label className="inline-flex items-center gap-2">
        <input
          name="isDualDegree"
          type="checkbox"
          checked={form.isDualDegree}
          onChange={handleChange}
          className="w-4 h-4"
        />
        <span className="text-gray-700">복수전공 여부</span>
      </label>

      <div className="flex gap-2">
        <select
          name="engScoreType"
          className="border rounded-xl px-2 py-2 flex-1"
          value={form.engScoreType}
          onChange={handleChange}
          required
        >
          <option value="">어학 시험 종류</option>
          <option value="TOEIC">TOEIC</option>
          <option value="TEPS">TEPS</option>
          <option value="OPIC">OPIC</option>
          <option value="TOEIC_SPEAKING">TOEIC Speaking</option>
        </select>
        <input
          name="engScore"
          type="number"
          className="border rounded-xl px-2 py-2 w-28"
          placeholder="점수"
          value={form.engScore}
          onChange={handleChange}
          required
        />
      </div>

      <div className="flex gap-2">
        <select
          name="externalScoreType"
          className="border rounded-xl px-2 py-2 flex-1"
          value={form.externalScoreType}
          onChange={handleChange}
          required
        >
          <option value="">프로그래밍 역량 종류</option>
          <option value="TOPCIT">TOPCIT</option>
          <option value="APC">APC</option>
        </select>
        <input
          name="externalScore"
          type="number"
          className="border rounded-xl px-2 py-2 w-28"
          placeholder="점수"
          value={form.externalScore}
          onChange={handleChange}
          required
        />
      </div>

      <input
        name="aquisitionDate"
        type="date"
        className="border rounded-xl px-4 py-2"
        value={form.aquisitionDate}
        onChange={handleChange}
        placeholder="취득일 (선택)"
      />

      <button
        type="submit"
        className="bg-blue-500 text-white font-semibold rounded-xl py-2 mt-4 hover:bg-blue-600 transition"
      >
        회원가입
      </button>
      {error && <div className="text-center text-red-500">{error}</div>}
    </form>
  );
}
