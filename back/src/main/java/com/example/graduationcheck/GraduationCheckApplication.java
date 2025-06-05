package com.example.graduationcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraduationCheckApplication {
	public static void main(String[] args) {
		SpringApplication.run(GraduationCheckApplication.class, args);
		System.out.println("✅ 서버 실행 성공");
	}
}