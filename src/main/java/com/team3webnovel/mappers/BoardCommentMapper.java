package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.team3webnovel.vo.BoardCommentVo;

@Mapper
public interface BoardCommentMapper {
	List <BoardCommentVo> getComments(int boardId);
	
	void writeComment(BoardCommentVo vo);
	
	int deleteComment(Map<String, Integer> map);
}
