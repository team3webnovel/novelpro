package com.team3webnovel.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.team3webnovel.vo.ImageBoardVo;

@Mapper
public interface ImageBoardMapper {
	List<ImageBoardVo> getImageBoardList();
}
