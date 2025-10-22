package com.kh.spring.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.BoardDTO;

@Mapper
public interface BoardMapper {

	int selectTotalCount();
	
	List<BoardDTO> findAll(RowBounds rowbounds);

	int save(BoardDTO board);

	int increaseCount(Long boardNo);

	BoardDTO findByBoardNo(Long boardNo);

}
