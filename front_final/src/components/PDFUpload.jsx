import React, { useRef, useState } from "react";
import { uploadTranscriptPDF } from "../api/api";
import { useNavigate } from "react-router-dom";

export default function PDFUpload() {
  const inputRef = useRef(null);
  const [status, setStatus] = useState("");
  const navigate = useNavigate();

  const handleUpload = async (e) => {
    e.preventDefault();
    const file = inputRef.current.files[0];
    if (!file) return;
    setStatus("업로드 중...");
    try {
      await uploadTranscriptPDF(file);
      setStatus("성적표 업로드 완료!");
      setTimeout(() => navigate("/dashboard"), 1000);
    } catch (err) {
      setStatus(err.message || "업로드 실패. PDF 파일을 확인해주세요.");
    }
  };

  return (
    <form
      onSubmit={handleUpload}
      className="bg-white rounded-2xl shadow p-8 flex flex-col items-center gap-5"
    >
      <div className="font-semibold text-gray-700">PDF 성적표 업로드</div>
      <input
        type="file"
        accept="application/pdf"
        ref={inputRef}
        className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-xl file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
        required
      />
      <button
        type="submit"
        className="bg-blue-500 text-white font-semibold rounded-xl py-2 px-6 hover:bg-blue-600 transition"
      >
        업로드
      </button>
      {status && <div className="text-center text-sm mt-3">{status}</div>}
    </form>
  );
}
