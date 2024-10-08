package com.team3webnovel.services;

import java.util.List;

import com.team3webnovel.dto.ImageBoardViewDto;
import com.team3webnovel.vo.ImageBoardVo;

public interface ImageBoardService {
	public List <ImageBoardVo> list();
	
	public void writeImageBoard(ImageBoardVo boardVo);
	
	public ImageBoardViewDto getImageBoardDetailAndComment(int boardId, int creationId);
	
}
