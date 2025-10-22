package com.kh.spring.board.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {

	// 게시글 목록 조회 + 페이징처리
	// 총 게시글 수 조회
	//int selectListCount();
	// 목록조회
	Map<String, Object> findAll(Long page);
	
	// 게시글 작성 조회 불가
	int save (BoardDTO board, MultipartFile upfile, HttpSession session);
	
	// 게시물 작성
	/*
	 * insertBoard()
	 * save()
	 * 
	 */
	
	// 게시글 상세보기(조회수 증가)
	public BoardDTO findByBoardNo(Long boardNo);
	// 게시글 삭제하기
	
	// 게시글 수정하기
	
	//------------------------
	// 댓글 서비스
}
