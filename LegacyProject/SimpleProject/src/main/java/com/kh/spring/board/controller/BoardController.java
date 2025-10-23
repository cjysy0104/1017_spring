package com.kh.spring.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("boards")
	public String findAll(@RequestParam(name="page", defaultValue = "1") Long page
																		, Model model) {
		log.info("{}", page);
		
		// 페이징처리
		// 게시글 몇개
		// 한 페이지에 몇개
		Map<String, Object> map = boardService.findAll(page);
		model.addAttribute("map", map);
		
		return "boards/list";
	}
	
	/*
	 * mapping
	 * 
	 * SELECT == GET / INSERT == POST
	 * 
	 * 전체조회 == boards @GET
	 * 상세조회 == boards
	 * 작성 == boards		@POST => 같은 매핑이여도 메서드 차이로 구별됨: controller
	 */
	
	@GetMapping("boards/form")
	public String toFrom() {
		return "boards/form";
	}
	
	@PostMapping("boards")
	public String save(BoardDTO board, MultipartFile upfile, HttpSession session) {
		log.info("게시글 버노{}", board);
		//MultipartFile 객체에 필드값 확인해야함
		
		// 파일 존재 유무 체크
		
		// 세션 체크
		
		
		boardService.save(board, upfile, session);
		return "redirect:boards";
	}
	
	@GetMapping("boards/{id}")
	public String toDetail(@PathVariable(name="id") Long boardNo,
													Model model) {
		log.info("{}", boardNo);
		
		// 조회수 증가
		// 조회수 증가에 성공했다면 SESLECT
		// 만약에 없는 게시글 번호라면 예외 발생
		BoardDTO board = boardService.findByBoardNo(boardNo);
		model.addAttribute("board", board);
		return "boards/detail";
	}
	
	
	
	
}
