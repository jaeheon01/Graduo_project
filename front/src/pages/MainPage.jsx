// src/pages/MainPage.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';

const MainPage = () => {
	const navigate = useNavigate();

	return (
		<div>
			<div className='title'>
				<h3>아주대학교 소프트웨어학과 졸업요건 충족 확인 사이트</h3>
				<h1>Graduo</h1>
			</div>
			<button onClick={() => navigate('/login')}>로그인 하러 가기</button>
		</div>
	);
};

export default MainPage;
