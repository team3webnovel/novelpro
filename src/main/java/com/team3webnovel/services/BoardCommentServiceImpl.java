package com.team3webnovel.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.BoardCommentDaoImpl;
import com.team3webnovel.vo.BoardCommentVo;

@Service
public class BoardCommentServiceImpl implements BoardCommentService {

	@Autowired
	BoardCommentDaoImpl commentDao;

	@Override
	public void writeComment(BoardCommentVo vo) {
		commentDao.writeComment(vo);
	}

	@Override
	public int deleteComment(int commentId, int userId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("commentId", commentId);
		map.put("userId", userId);
		return commentDao.deleteComment(map);
	}
}
