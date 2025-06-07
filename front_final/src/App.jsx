// src/App.jsx
import React, { useRef, useState } from "react";
// import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/Header";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import UploadPage from "./pages/UploadPage";
import DashboardPage from "./pages/DashboardPage";

function RequireAuth({ children }) {
  const authed = !!localStorage.getItem("userId");
  return authed ? children : <Navigate to="/login" replace />;
}

export default function App() {
  return (
    <Router>
      <Header />
      <div className="container mx-auto py-6 px-3">
        <Routes>
          {/* 기본 진입 경로 */}
          <Route
            path="/"
            element={
              !!localStorage.getItem("userId")
                ? <Navigate to="/upload" replace />
                : <Navigate to="/login" replace />
            }
          />

          {/* 인증이 필요 없는 경로 */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />

          {/* 인증이 필요한 경로 */}
          <Route
            path="/upload"
            element={
              <RequireAuth>
                <UploadPage />
              </RequireAuth>
            }
          />
          <Route
            path="/dashboard"
            element={
              <RequireAuth>
                <DashboardPage />
              </RequireAuth>
            }
          />

          {/* 그 외 잘못된 경로는 로그인으로 */}
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </div>
    </Router>
  );
}
