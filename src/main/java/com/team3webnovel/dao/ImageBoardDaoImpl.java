package com.team3webnovel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.ImageBoardMapper;
import com.team3webnovel.vo.ImageBoardVo;

@Repository
public class ImageBoardDaoImpl implements ImageBoardDao {

	@Autowired
	private ImageBoardMapper imageBoardMapper;
	
	@Override
	public List<ImageBoardVo> list() {
		return imageBoardMapper.getImageBoardList();
	}

	@Override
	public void writeImageBoard(ImageBoardVo boardVo) {
		imageBoardMapper.writeImageBoard(boardVo);
	}

	@Override
	public int publicCheck(int boardId) {
		return imageBoardMapper.publicCheck(boardId);
	}

}
