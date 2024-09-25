package com.team3webnovel.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.GijeBoardDao;
import com.team3webnovel.dto.BoardPageDto;
import com.team3webnovel.vo.GijeBoardVo;

@Service
public class GijeBoardService {
	
	@Autowired
	private GijeBoardDao boardDao;
	
//	public List <GijeBoardVo> getBoardList(){
//		return boardDao.findAll();
//	}
	
	public void write(GijeBoardVo vo) {
		boardDao.insert(vo);
	}
	
	public GijeBoardVo view(int boardId) {
		return boardDao.select(boardId);
	}
	
	public BoardPageDto getPagingList(int page, int pageSize){
		int totalPosts = boardDao.getTotal();
		int totalPages = (int)Math.ceil((double)totalPosts/pageSize);
		
		int startRow = (page - 1) * pageSize;
		int endRow = startRow + pageSize;
		
		Map <String, Integer> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("pageSize", pageSize);
		
		List <GijeBoardVo> boardList = boardDao.boardPaging(map);
		
		return new BoardPageDto(boardList, totalPages, page);
	}
}
