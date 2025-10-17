package com.kh.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.member.model.vo.Member;

@Controller
public class MemberController {

//	@RequestMapping("login")
//	public void test1() {
//		System.out.println("로그인 기능");
//	}
//	
//	@RequestMapping("insert")
//	public void test2() {
//		System.out.println("가입 기능");
//	}
//	
//	@RequestMapping("delete")
//	public void test3() {
//		System.out.println("탈퇴 기능");
//	}
	@RequestMapping("login")
	public void login(Member member) {
		// 1. 값뽑기
		// 2. 데이터 가공 >> spring이 해줌
		System.out.println(member);
	}
}
