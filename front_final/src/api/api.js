const BASE_URL = "http://localhost:8080";

function getUserId() {
  return localStorage.getItem("userId");
}

/**
 * 로그인
 *  - 실제 매핑: GET /api/user/login?userId={userId}&studentNumber={studentNumber}
 */
export async function login({ userId, studentNumber }) {
  const url = new URL(`${BASE_URL}/api/user/login`);
  url.searchParams.append("userId", userId);
  url.searchParams.append("studentNumber", studentNumber);

  const res = await fetch(url.toString(), {
    method: "GET",
  });
  if (!res.ok) {
    // 예: 401 Unauthorized 등
    const text = await res.text().catch(() => "");
    throw new Error(text || "로그인 실패");
  }
  return; // 로그인 성공
}

/**
 * 회원가입
 *  - 실제 매핑: POST /api/user/signup
 *  - DTO 필드명(키)도 스프링 DTO(SignUpRequest)와 정확히 일치해야 합니다.
 */
export async function register(form) {
  const res = await fetch(`${BASE_URL}/api/user/signup`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(form),
  });
  if (!res.ok) {
    // Bad Request 등
    const data = await res.json().catch(() => ({}));
    throw new Error(data.message || "회원가입 실패");
  }
  return; // 회원가입 성공
}

/**
 * PDF 성적표 업로드
 *  - 실제 매핑: POST /api/transcript/upload
 *  - (TranscriptController 클래스의 @RequestMapping("/api") + @PostMapping("/transcript/upload"))
 */
export async function uploadTranscriptPDF(file) {
  const userId = getUserId();
  if (!userId) throw new Error("로그인 후 이용해 주세요.");

  const formData = new FormData();
  formData.append("file", file);
  formData.append("userId", userId);

  const res = await fetch(`${BASE_URL}/api/transcript/upload`, {
    method: "POST",
    body: formData,
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "성적표 업로드 실패");
  }
  return; // 업로드 성공
}

/**
 * 졸업요건 조회
 *  - 실제 매핑: GET /api/graduation/{userId}/requirements
 *  - (GraduationController 클래스의 @RequestMapping("/api/graduation"))
 */
export async function fetchGraduationStatus() {
  const userId = getUserId();
  if (!userId) throw new Error("로그인 후 이용해 주세요.");

  const res = await fetch(
    `${BASE_URL}/api/graduation/${userId}/requirements`,
    { method: "GET" }
  );
  if (!res.ok) {
    throw new Error("졸업요건 조회 실패");
  }
  return res.json();
}

/**
 * 재수강 추천 조회
 *  - 실제 매핑: GET /api/graduation/{userId}/retake
 */
export async function fetchRetakeRecommendation() {
  const userId = getUserId();
  if (!userId) throw new Error("로그인 후 이용해 주세요.");

  const res = await fetch(
    `${BASE_URL}/api/graduation/${userId}/retake`,
    { method: "GET" }
  );
  if (!res.ok) {
    throw new Error("재수강 추천 조회 실패");
  }
  return res.json();
}
