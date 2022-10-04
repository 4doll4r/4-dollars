package com.example.fourdollars.member.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fourdollars.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
}
