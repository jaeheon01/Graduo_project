// src/pages/MainPage.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';

const MainPage = () => {
	const navigate = useNavigate();

	return (
		<div>
			<h1>Graduo 시스템에 오신 것을 환영합니다</h1>
			<button onClick={() => navigate('/login')}>로그인 하러 가기</button>
		</div>
	);
};

export default MainPage;
