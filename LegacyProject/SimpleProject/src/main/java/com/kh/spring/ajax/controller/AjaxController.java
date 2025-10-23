package com.kh.spring.ajax.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController //@ResponseBody 에노테이션을 생략할 수 있다.
public class AjaxController {

	/*
	 * 응답할 데이터를 문자열로 반환
	 * ModelAndView의 viewName필드에 return한 문자열 값이 대입
	 * => DispatcherServlet
	 * => ViewResovler
	 * 
	 * 반환값이 String 값이  View의 정보가 아닌 응답데이터임을 명시해서
	 * => MessageConverter라는 빈ㅇ로 이동하게끔 
	 */
	@ResponseBody
	@GetMapping(value="test",produces="text/html; charset=UTF-8")
	public String ajaxReturn(@RequestParam(name="input") String value) {
		log.info("넘어옴: {}", value);
		String lunchMenu = "짬뽕";
		return lunchMenu;
	}
	
	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService)
	{
		this.boardService = boardService;
	}
	
	@ResponseBody
	@PostMapping(value = "replies", produces="text/html; charset=UTF-8")
	public String insertReply(ReplyDTO reply, HttpSession session) {
//		log.info("{}", reply);
		int result = boardService.insertReply(reply, session);
		return result > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@GetMapping(value = "board/{num}", produces="application/json; charset=UTF-8")
	public BoardDTO detail(@PathVariable(value="num") Long boardNo) {
//		log.info("게시글 번호: {}", boardNo);
		BoardDTO board = boardService.findByBoardNo(boardNo);
		log.info("{}", board);
		
		return board;
	}
}
