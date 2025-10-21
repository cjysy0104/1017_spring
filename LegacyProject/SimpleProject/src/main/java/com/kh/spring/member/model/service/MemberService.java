package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import com.kh.spring.member.model.dto.MemberDTO;

public interface MemberService {

	// I/F : 추상메서드, default메서드, static메서드
	
	// 로그인 ==> 반환타입 + 매개변수는 생각해둘 수 있음
	MemberDTO login(MemberDTO member);
	
	// 회원가입
	// MyBatis: 1. 정수값 반환
	//			2. 아무 값도 반환하지 않음(void) ==> 예외처리를 빢빢하게 하겠다.
	// Hibernate: 가입된 회원의 정보를 반환해줌 / 실패 시 null
	
	// 회원가입
	void signup(MemberDTO member);
	
	// 정보수정
	void update(MemberDTO member, HttpSession session);
	
	// 탈퇴
	void delete(String userPwd, HttpSession session);
}
