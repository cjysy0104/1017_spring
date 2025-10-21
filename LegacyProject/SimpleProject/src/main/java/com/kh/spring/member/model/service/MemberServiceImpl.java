package com.kh.spring.member.model.service;

import java.security.InvalidAlgorithmParameterException;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidArgumentsException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.UserIdNotFoundException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dao.MemberRepository;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	/*
	 * SRP(Single Responsibility Principle)
	 * 단일책임원칙 위반
	 * 
	 * 하나의 클래스는(메서드) 하나의 책임만을 가져야 함 == 애가 수정되는 이유는 딱 하나여야함
	 * 
	 * 책임을 분리하면 끝
	 */
	
	//@Autowired
//	private final SqlSessionTemplate sqlSession;
//	//@Autowired
//	private final MemberRepository memberRepository;
	//@Autowired
	private final BCryptPasswordEncoder passwordEncoder;
	
	private final MemberValidator validator;
	
	private final MemberMapper mapper;
	
	/*
	@Autowired
	public MemberServiceImpl(SqlSessionTemplate sqlSession,
							 MemberRepository memberRepository,
							 BCryptPasswordEncoder passwordEncoder) {
		this.sqlSession = sqlSession;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}
	>> 생성자 의존성 주입: 귀찬타 => Lombok: @RequiredArgsConstructor 사용	
	*/
	@Override
	public MemberDTO login(MemberDTO member) {
		// log.info("call me");
		/*
		 * 기존) SqlSession session = Template.getSqlSession(); ....
		 * 
		 */

		// ver 1.0
		//return memberRepository.login(sqlSession, member);
		
		// 사용자는 평문을 입력하지만 실제 DB컬럼에는 암호문이 들어있기 떄문에
		// 비밀번호를 비교하는 SELECT문을 사용할 수 없음
		
		///////////////////////////////////////////////////////////////////
		MemberDTO loginMember = mapper.login(member);
//		
////		if(loginMember == null) {
////			throw new UserIdNotFoundException("아이디 또는 비밀번호가 올바르지 않습니다.");
////		}
//		
//		// 1절
//		log.info("사용자가 입력한 비밀번호 평문: {}", member.getUserPwd());
//		log.info("DB에 저장된 비밀번호 암호문: {}", loginMember.getUserPwd());
//		
//		// 아이디만 가지고 조회를 하기 때문에
//		// 비밀번호 검증 후
//		// 비밀번호가 유효하다면 성공
//		// 비밀번호가 유효하지 않는다면 실패
//		if(passwordEncoder.matches(member.getUserPwd(),
//							  loginMember.getUserPwd())) {
//			return loginMember;
//		}
//		
//		return null;
		///////////////////////////////////////////////////////////////////	
		return validateLoginMember(loginMember, member.getUserPwd());
	}
	
	private MemberDTO validateLoginMember(MemberDTO loginMember, String userPwd) {
		
		if(loginMember == null) {
			throw new UserIdNotFoundException("아이디가 또는 비밀번호가 틀림.");
		}
		
		if(passwordEncoder.matches(userPwd, loginMember.getUserPwd())) {
			return loginMember;
		}
		return null;
		
	}
	
	@Override
	public void signup(MemberDTO member) {
		
//		// 사용자 정의 exception
//		if (member == null) {
//			throw new NullPointerException("잘못된 접근입니다.");
//		}
//
//		if (member.getUserId().length() > 20) {
//			throw new TooLargeValueException("아이디 값이 너무 길어용");
//		}
//
//		if (member.getUserId() == null ||
//			member.getUserId().trim().isEmpty() ||
//			member.getUserPwd() == null ||
//			member.getUserPwd().trim().isEmpty()
//				) {
//			//throw new InvalidAlgorithmParameterException(); 
//			throw new InvalidArgumentsException("유효하지 않는 값입니다.");
		
		// id 중복체크 생략
		
		// DAO 가서 INSERT 하기 전 비밀번호 암호화
			//log.info("사용자가 입력한 비밀번호 평문: {}", member.getUserPwd()); //plaintext라고함
			// 암호화하기 ==> 인코더 가지고 .encode()호출 
			//log.info("암호화한 후 : {}", passwordEncoder.encode(member.getUserPwd()));
			// >> VO 만들어서 하는게 정석: 생략
		
		validator.validatedMember(member);
		String encPwd = passwordEncoder.encode(member.getUserPwd());
		member.setUserPwd(encPwd);
		mapper.signup(member);
		
	}
		


	@Override
	public void update(MemberDTO member, HttpSession session) {
		
		MemberDTO sessionMember = ((MemberDTO)session.getAttribute("loginMember"));
		// 본격적인 개발자 영역
		// 앞단에서 넘어온 ID값과 현재 로그인된 사용자의 ID값이 일치하는가?
		
		// 실제 DB에 ID값이 존재하는 회원인가?
		
		// USERNAME 컬럶에 넣을 값이 USERNAME컬럼 크기보다 크지 않은가?
		
		// EMAIL 컬럼에 넣을 값이 EMAIL컬럼 크기보다 크지 않은가?
		
		// (EMAIL컬럼에 넣을 값이 실제 EMAIL형식과 일치하는가?)
		log.info("{}", sessionMember);
		validator.validateUpdateMember(member, sessionMember);
		
		// (성공이면) DB가서 UPDATE
		int result = mapper.update(member);
		
		if(result != 1) {
			throw new AuthenticationException("문제발생. 관리자에게 문의.");
		}
		// 성공한 회원의 정보로 SessionScope에 존재하는 loginMember키값의 Member객체 필드값 갱신해주기
		sessionMember.setUserName(member.getUserName());
		sessionMember.setEmail(member.getEmail());
	}

	@Override
	public void delete(String userPwd, HttpSession session) {
		
		// 제 1원칙: 기능이 동작할 것.
		
		MemberDTO sessionMember = ((MemberDTO)session.getAttribute("loginMember"));
		
		if(sessionMember == null) {
			throw new AuthenticationException("로그인이 필요합니다.");
		}
		
		if(passwordEncoder.matches(userPwd, sessionMember.getUserPwd())) {
			throw new AuthenticationException("비밀번호가 일치하지 않습니다");
		}
		
		// DELETE FROM MEMBER WHERE USER_ID = 현재 로그인된 사용자의 아이디
		int result = mapper.delete(sessionMember.getUserId());
		
		if(result != 1) {
			throw new AuthenticationException("관리자에게 문의");
		}
		
		// 리팩토링
		/*
		 * 쉬운데 어려움(복잡함)
		 */
	}

}
