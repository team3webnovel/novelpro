package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.ImageBoardVo;

public interface ImageBoardDao {
	public List <ImageBoardVo> list();
	
	public void writeImageBoard(ImageBoardVo boardVo);
	
	public int deleteImageBoard(Map <String, Integer> map);
	
	public int publicCheck(int boardId);
	
	List <BoardCommentVo> getList(int boardId);
	void writeComment(BoardCommentVo vo);
	int deleteComment(Map<String, Integer> map);
}
