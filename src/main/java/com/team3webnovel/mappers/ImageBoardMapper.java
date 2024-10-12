package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.team3webnovel.vo.ImageBoardVo;

@Mapper
public interface ImageBoardMapper {
	List<ImageBoardVo> getImageBoardList();
	
	void writeImageBoard(ImageBoardVo boardVo);
	
	int deleteImageBoard(Map <String, Integer> map);
	
	int publicCheck(int boardId);
}
