package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.team3webnovel.vo.BoardVo;

@Mapper
public interface BoardMapper {

	void write(BoardVo board);
	
	BoardVo view(int boardId);
	
	int getTotalCount();
	
	List<BoardVo> pagingBoardList(Map <String, Integer> map);
	
	int deleteBoard(Map <String, Integer> map);
	
	void viewCount(int boardId);
}
