import React, { useState } from "react";
import { login } from "../api/api";
import { useNavigate } from "react-router-dom";

export default function LoginForm() {
  const [userId, setUserId] = useState("");
  const [studentNumber, setStudentNumber] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      // userId는 숫자이므로 parseInt 권장
      await login({ userId: parseInt(userId, 10), studentNumber });
      // 로그인 성공 시 userId 저장
      localStorage.setItem("userId", userId);
      navigate("/upload");
    } catch (err) {
      setError(err.message || "로그인 실패: 아이디 또는 학번을 확인해주세요.");
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-white rounded-2xl shadow p-8 flex flex-col gap-5"
    >
      <div className="text-2xl font-bold text-center mb-2 text-blue-700">
        로그인
      </div>
      <input
        type="number"
        className="border rounded-xl px-4 py-2"
        placeholder="User ID (숫자)"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        required
      />
      <input
        type="text"
        className="border rounded-xl px-4 py-2"
        placeholder="학번 (예: 20210001)"
        value={studentNumber}
        onChange={(e) => setStudentNumber(e.target.value)}
        required
      />
      <button
        type="submit"
        className="bg-blue-500 text-white font-semibold rounded-xl py-2 mt-4 hover:bg-blue-600 transition"
      >
        로그인
      </button>
      {error && <div className="text-center text-red-500">{error}</div>}
    </form>
  );
}
