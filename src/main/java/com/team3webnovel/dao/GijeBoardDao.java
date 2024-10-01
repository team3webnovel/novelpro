package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.GijeBoardVo;

public interface GijeBoardDao {
	public void insert (GijeBoardVo board);
	public GijeBoardVo select(int boardId);
	public int getTotal();
	public List<GijeBoardVo>boardPaging(Map <String, Integer> map);
	public int delete(Map <String, Integer> map);
	public void viewCount(int boardId);
}
