package com.team3webnovel.dao;

import java.util.List;

import com.team3webnovel.vo.ImageBoardVo;

public interface ImageBoardDao {
	public List <ImageBoardVo> list();
	
	public void writeImageBoard(ImageBoardVo boardVo);
	
	public int publicCheck(int boardId);
}
