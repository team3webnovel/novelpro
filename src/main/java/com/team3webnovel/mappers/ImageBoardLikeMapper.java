package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageBoardLikeMapper {
	Integer check(Map<String, Integer> map);
	
	void pushLike(Map<String, Integer> map);
	
	void unlike(Map<String, Integer> map);
	
	List<Map<String, Object>> likeCounts();
}
