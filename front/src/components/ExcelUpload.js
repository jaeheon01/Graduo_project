import React, { useState } from 'react';
import axios from 'axios';

const ExcelUpload = () => {
	const [file, setFile] = useState(null);
	const [result, setResult] = useState(null);

	const handleChange = (e) => {
		setFile(e.target.files[0]);
	};

	const handleUpload = async () => {
		const formData = new FormData();
		formData.append("file", file);

		const res = await axios.post("http://localhost:8080/api/upload", formData, {
			headers: { "Content-Type": "multipart/form-data" }
		});
		setResult(res.data);
	};

	return (
		<div>
			<h2>엑셀 업로드</h2>
			<input type="file" accept=".xlsx" onChange={handleChange} />
			<button onClick={handleUpload}>업로드</button>

			{result && (
				<div>
					<h3>결과:</h3>
					<pre>{JSON.stringify(result, null, 2)}</pre>
				</div>
			)}
		</div>
	);
};

export default ExcelUpload;
