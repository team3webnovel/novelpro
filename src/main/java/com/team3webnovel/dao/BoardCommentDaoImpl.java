package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.BoardCommentMapper;
import com.team3webnovel.vo.BoardCommentVo;

@Repository
public class BoardCommentDaoImpl implements BoardCommentDao {
    
	@Autowired
    private BoardCommentMapper commentMapper;

	@Override
	public List<BoardCommentVo> getList(int boardId) {
		return commentMapper.getComments(boardId);
	}

	@Override
	public void writeComment(BoardCommentVo vo) {
		commentMapper.writeComment(vo);
	}

	@Override
	public int deleteComment(Map<String, Integer> map) {
		return commentMapper.deleteComment(map);
	}

}
