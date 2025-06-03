import React, { useEffect, useState } from 'react';

const ResultPage = () => {
	const [result, setResult] = useState(null);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState(null);

	useEffect(() => {
		const fetchResult = async () => {
			const userId = localStorage.getItem('userId');

			if (!userId) {
				setError('로그인이 필요합니다.');
				setLoading(false);
				return;
			}

			try {
				const res = await fetch(`http://localhost:8080/api/result?userId=${userId}`);
				if (!res.ok) {
					throw new Error('서버 오류 또는 결과 없음');
				}
				const data = await res.json();
				setResult(data);
			} catch (err) {
				console.error('결과 불러오기 실패:', err);
				setError('결과를 불러오는 중 오류가 발생했습니다.');
			} finally {
				setLoading(false);
			}
		};

		fetchResult();
	}, []);

	return (
		<div className="page-container">
			<div className="form-box">
				<h1>결과 확인</h1>
				<p>업로드된 성적표와 DB 비교 결과를 확인하세요.</p>

				{loading && <p>결과를 불러오는 중...</p>}
				{error && <p style={{ color: 'red' }}>{error}</p>}

				{!loading && result && (
					<div>
						<h3>🔍 비교 결과</h3>
						{result.mismatches.length === 0 ? (
							<p>🎉 모든 성적이 일치합니다!</p>
						) : (
							<table className="result-table">
								<thead>
									<tr>
										<th>과목</th>
										<th>업로드된 성적</th>
										<th>DB 성적</th>
									</tr>
								</thead>
								<tbody>
									{result.mismatches.map((item, idx) => (
										<tr key={idx}>
											<td>{item.subject}</td>
											<td>{item.uploadedGrade}</td>
											<td>{item.dbGrade}</td>
										</tr>
									))}
								</tbody>
							</table>
						)}
					</div>
				)}
			</div>
		</div>
	);
};

export default ResultPage;
