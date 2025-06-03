// src/pages/UploadPage.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const UploadPage = () => {
	const [file, setFile] = useState(null);
	const navigate = useNavigate();

	const handleFileChange = (e) => {
		setFile(e.target.files[0]);
	};

	const handleSubmit = async () => {
		if (!file) return;

		const currentUserID = localStorage.getItem('userId');

		if (!currentUserID) {
			alert('로그인이 필요합니다.');
			return;
		}

		const formData = new FormData();
		formData.append('file', file);
		formData.append('userId', currentUserID);

		// 파일 업로드 및 백엔드 전송
		const response = await axios.post('http://172.21.77.43:8080/api/transcript/upload', formData, {
			headers: {
				"Content-Type": "multipart/form-data"
			}
		});

		console.log('응답 상태:', response.status);

		if (response.status === 200) {
			console.log('✅ 파일 업로드 성공');
		} else {
			console.error('❌ 업로드 실패:', response.status);
		}


		navigate('/result');
	};

	return (
		<div className="page-container">
			<div className="form-box">
				<h1>성적표 업로드</h1>
				<input type="file" accept=".pdf" onChange={handleFileChange} />
				<button onClick={handleSubmit}>제출</button>
			</div>
		</div>
	);
};

export default UploadPage;
