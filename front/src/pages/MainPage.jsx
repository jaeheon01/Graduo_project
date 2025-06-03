// src/pages/MainPage.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';

const MainPage = () => {
	const navigate = useNavigate();

	return (
		<div className="page-container">
			<div className="form-box">
				<div className="title">
					<h3>아주대학교 소프트웨어학과<br />졸업요건 충족 확인 사이트</h3>
					<h1 style={{ color: '#1a2c5b', margin: '16px 0' }}>Graduo</h1>
				</div>
				<button onClick={() => navigate('/login')}>로그인 하러 가기</button>
			</div>
		</div>
	);
};

export default MainPage;
