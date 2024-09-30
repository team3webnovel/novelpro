package com.team3webnovel.dto;

import java.util.List;

import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.GijeBoardVo;

public class BoardViewDto {
	private GijeBoardVo boardVo;
	private List <BoardCommentVo> comments;
	
	public BoardViewDto(GijeBoardVo boardVo, List <BoardCommentVo> comments){
		this.boardVo = boardVo;
		this.comments = comments;
	}
	
	public GijeBoardVo getBoardVo() {
		return boardVo;
	}
	public void setBoardVo(GijeBoardVo boardVo) {
		this.boardVo = boardVo;
	}
	public List<BoardCommentVo> getComments() {
		return comments;
	}
	public void setComments(List<BoardCommentVo> comments) {
		this.comments = comments;
	}
}
