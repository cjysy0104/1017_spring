package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberRepository;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public MemberDTO login(MemberDTO member) {
		// log.info("call me");
		/*
		 * 기존) SqlSession session = Template.getSqlSession(); ....
		 * 
		 */

		// ver 1.0
		return memberRepository.login(sqlSession, member);
	}

	@Override
	public void signup(MemberDTO member) {
		
		
		if (member == null) {

			return;
		}

		if (member.getUserId().length() > 20) {
			throw new TooLargeValueException("아이디 값이 너무 길어용");
		}

//		if (member.getUserId() == null) {
//			return;
//		}
	}

	@Override
	public void update(MemberDTO member) {
	}

	@Override
	public void delete(MemberDTO member) {
	}

}
