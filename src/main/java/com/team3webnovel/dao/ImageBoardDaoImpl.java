package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.ImageBoardCommentMapper;
import com.team3webnovel.mappers.ImageBoardLikeMapper;
import com.team3webnovel.mappers.ImageBoardMapper;
import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.ImageBoardVo;

@Repository
public class ImageBoardDaoImpl implements ImageBoardDao {

	@Autowired
	private ImageBoardMapper imageBoardMapper;
	
	@Autowired
	private ImageBoardCommentMapper commentMapper;
	
	@Autowired
	private ImageBoardLikeMapper likeMapper;
	
	@Override
	public List<ImageBoardVo> list() {
		return imageBoardMapper.getImageBoardList();
	}

	@Override
	public void writeImageBoard(ImageBoardVo boardVo) {
		imageBoardMapper.writeImageBoard(boardVo);
	}
	
	@Override
	public int deleteImageBoard(Map <String, Integer> map) {
		return imageBoardMapper.deleteImageBoard(map);
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

	@Override
	public boolean check(Map<String, Integer> map) {
		Integer result = likeMapper.check(map);
		int liked = (result != null) ? result : 0;
		if (liked == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void like(Map<String, Integer> map) {
		likeMapper.pushLike(map);
	}

	@Override
	public void unlike(Map<String, Integer> map) {
		likeMapper.unlike(map);
	}

	@Override
	public List<Map<String, Object>> likeCounts() {
		return likeMapper.likeCounts();
	}
}
