package com.team3webnovel.dto;

import java.util.List;

import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.BoardVo;

public class BoardViewDto {
	private BoardVo boardVo;
	private List <BoardCommentVo> comments;
	
	public BoardViewDto(BoardVo boardVo, List <BoardCommentVo> comments){
		this.boardVo = boardVo;
		this.comments = comments;
	}
	
	public BoardVo getBoardVo() {
		return boardVo;
	}
	public void setBoardVo(BoardVo boardVo) {
		this.boardVo = boardVo;
	}
	public List<BoardCommentVo> getComments() {
		return comments;
	}
	public void setComments(List<BoardCommentVo> comments) {
		this.comments = comments;
	}
}
