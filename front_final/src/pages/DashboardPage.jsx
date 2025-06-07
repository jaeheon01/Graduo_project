// src/pages/DashboardPage.jsx
import React, { useEffect, useState } from "react";
import GraduationStatus from "../components/GraduationStatus";
import RetakeRecommendation from "../components/RetakeRecommendation";
import {
  fetchGraduationStatus,
  fetchRetakeRecommendation,
} from "../api/api";

export default function DashboardPage() {
  const [status, setStatus] = useState(null);
  const [retake, setRetake] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function load() {
      try {
        setLoading(true);
        const statusData = await fetchGraduationStatus();
        console.log("▶▶▶ [Dashboard] statusData:", statusData);
        const retakeData = await fetchRetakeRecommendation();
        console.log("▶▶▶ [Dashboard] retakeData:", retakeData);
        setStatus(statusData);
        setRetake(retakeData);
      } catch (err) {
        console.error("▶▶▶ Dashboard 로딩 오류:", err);
        setError(err.message || "대시보드 로딩 실패");
      } finally {
        setLoading(false);
      }
    }
    load();
  }, []);

  if (loading)
    return <div className="text-center py-12">불러오는 중...</div>;
  if (error)
    return (
      <div className="text-center py-12 text-red-500">
        {error}
      </div>
    );

  return (
    <div className="flex flex-col gap-6">
      <GraduationStatus status={status} />
      <RetakeRecommendation retake={retake} />
    </div>
  );
}
