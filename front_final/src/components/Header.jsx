import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";

export default function Header() {
  const { pathname } = useLocation();
  const navigate = useNavigate();
  const authed = !!localStorage.getItem("userId");

  const handleLogout = () => {
    localStorage.removeItem("userId");
    navigate("/login");
  };

  return (
    <nav className="flex justify-between items-center py-4 px-6 bg-white shadow rounded-2xl mb-8">
      <Link to="/" className="text-2xl font-bold text-blue-600 tracking-wider">
        Graduo
      </Link>
      <div className="flex gap-6 items-center">
        {authed ? (
          <>
            <Link
              to="/upload"
              className={`
                font-semibold transition-colors duration-200
                ${pathname === "/upload"
                  ? "text-blue-500"
                  : "text-gray-700 hover:text-blue-500"}
              `}
            >
              성적표 업로드
            </Link>
            <Link
              to="/dashboard"
              className={`
                font-semibold transition-colors duration-200
                ${pathname === "/dashboard"
                  ? "text-blue-500"
                  : "text-gray-700 hover:text-blue-500"}
              `}
            >
              졸업 진단
            </Link>
            <button
              onClick={handleLogout}
              className="ml-2 px-4 py-1 bg-blue-100 text-blue-700 rounded-xl font-semibold shadow hover:bg-blue-200 transition"
            >
              로그아웃
            </button>
          </>
        ) : (
          <>
            <Link
              to="/login"
              className={`
                font-semibold transition-colors duration-200
                ${pathname === "/login"
                  ? "text-blue-500"
                  : "text-gray-700 hover:text-blue-500"}
              `}
            >
              로그인
            </Link>
            <Link
              to="/register"
              className={`
                font-semibold transition-colors duration-200
                ${pathname === "/register"
                  ? "text-blue-500"
                  : "text-gray-700 hover:text-blue-500"}
              `}
            >
              회원가입
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}
