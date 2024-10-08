package com.team3webnovel.dto;

import java.util.List;

import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.ImageVo;

public class ImageBoardViewDto {
	private ImageVo imageVo;
	private List <BoardCommentVo> comments;
	
	public ImageBoardViewDto(ImageVo imageVo, List <BoardCommentVo> comments){
		this.imageVo = imageVo;
		this.comments = comments;
	}
	
	public ImageVo getImageVo() {
		return imageVo;
	}
	public void setImageVo(ImageVo boardVo) {
		this.imageVo = boardVo;
	}
	public List<BoardCommentVo> getComments() {
		return comments;
	}
	public void setComments(List<BoardCommentVo> comments) {
		this.comments = comments;
	}
}
