import './App.css';
import React from 'react';
import ExcelUpload from './components/ExcelUpload';

function App() {
	return (
		<div className="App">
			<h1>엑셀 업로드 및 DB 비교</h1>
			<ExcelUpload />
		</div>
	);
}

export default App;