// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const navigate = useNavigate();

	const handleLogin = () => {
		// TODO: 실제 로그인 API 연동 필요
		if (username && password) {
			localStorage.setItem('userId', username);
			navigate('/upload');
		} else {
			alert('ID와 비밀번호를 입력하세요');
		}
	};

	return (
		<div className="page-container">
			<div className="form-box">
				<h1>로그인</h1>
				<input type="number" placeholder="학번" value={username} onChange={e => setUsername(e.target.value)}/>
				<input type="password" placeholder="비밀번호" value={password} onChange={e => setPassword(e.target.value)} />
				<button onClick={handleLogin}>Login</button>
			</div>
		</div>
	);
};

export default LoginPage;
