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
			navigate('/upload');
		}
	};

	return (
		<div>
			<h2>로그인</h2>
			<input placeholder="ID" onChange={e => setUsername(e.target.value)} />
			<input placeholder="비밀번호" type="password" onChange={e => setPassword(e.target.value)} />
			<button onClick={handleLogin}>로그인</button>
		</div>
	);
};

export default LoginPage;
