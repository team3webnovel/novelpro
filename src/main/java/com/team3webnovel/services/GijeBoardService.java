package com.team3webnovel.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.GijeBoardDao;
import com.team3webnovel.vo.GijeBoardVo;

@Service
public class GijeBoardService {
	
	@Autowired
	private GijeBoardDao boardDao;
	
	public List <GijeBoardVo> getBoardList(){
		return boardDao.findAll();
	}
	
	public void write(GijeBoardVo vo) {
		boardDao.insert(vo);
	}
	
	public GijeBoardVo view(int boardId) {
		return boardDao.select(boardId);
	}
}
