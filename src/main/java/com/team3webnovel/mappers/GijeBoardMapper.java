package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.team3webnovel.vo.GijeBoardVo;

@Mapper
public interface GijeBoardMapper {

	void write(GijeBoardVo board);
	
	GijeBoardVo view(int boardId);
	
	int getTotalCount();
	
	List<GijeBoardVo> pagingBoardList(Map <String, Integer> map);
	
	int deleteBoard(Map <String, Integer> map);
	
	void viewCount(int boardId);
}
