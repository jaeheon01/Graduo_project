package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Entity: User(user_id PK)
 * JpaRepository를 상속하면 findById, existsById 등 기본 메서드가 모두 제공됩니다.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    // 별도의 메서드 선언 불필요
}
