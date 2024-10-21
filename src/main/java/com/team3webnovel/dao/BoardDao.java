package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.BoardVo;

public interface BoardDao {
	public void insert (BoardVo board);
	public BoardVo select(int boardId);
	public int getTotal();
	public List<BoardVo>boardPaging(Map <String, Integer> map);
	public int delete(Map <String, Integer> map);
	public void viewCount(int boardId);
}
