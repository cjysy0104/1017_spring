package com.kh.spring.ajax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.spring.board.model.service.BoardService;

@Controller
public class AjaxForwardController {
	
	@GetMapping("page")
	public String toAjax() {
		return "ajax/ajax";
	}
	
}
