package com.team3webnovel.services;

import java.util.List;
import java.util.Map;

import com.team3webnovel.dto.ImageBoardViewDto;
import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.ImageBoardVo;

public interface ImageBoardService {
	public List <ImageBoardVo> list();
	
	public void writeImageBoard(ImageBoardVo boardVo);
	
	public String deleteImageBoard(int boardId, int userId);
	
	public ImageBoardViewDto getImageBoardDetailAndComment(int boardId, int creationId);
	
	void writeComment(BoardCommentVo vo);
	
	int deleteComment(int commentId, int userId);
	
//	public boolean toggleLike(int userId, int boardId);
}
