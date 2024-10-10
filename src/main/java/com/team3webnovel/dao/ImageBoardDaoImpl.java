package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.team3webnovel.mappers.ImageBoardCommentMapper;
import com.team3webnovel.mappers.ImageBoardMapper;
import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.ImageBoardVo;

@Repository
public class ImageBoardDaoImpl implements ImageBoardDao {

	@Autowired
	private ImageBoardMapper imageBoardMapper;
	
	@Autowired
	private ImageBoardCommentMapper commentMapper;

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
