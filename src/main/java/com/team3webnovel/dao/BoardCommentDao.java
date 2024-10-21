package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.BoardCommentVo;

public interface BoardCommentDao {
	List <BoardCommentVo> getList(int boardId);
	void writeComment(BoardCommentVo vo);
	int deleteComment(Map<String, Integer> map);
}
