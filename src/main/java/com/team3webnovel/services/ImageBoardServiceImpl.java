package com.team3webnovel.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.ImageBoardDaoImpl;
import com.team3webnovel.dao.ImageDao;

import com.team3webnovel.dao.UserDao;
import com.team3webnovel.dto.ImageBoardViewDto;
import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.ImageBoardVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.UserVo;

@Service
public class ImageBoardServiceImpl implements ImageBoardService {

	@Autowired
	private ImageBoardDaoImpl imageBoardDao;

	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<ImageBoardVo> list() {
//		ImageVo imageInfo = imageDao.getAllInformation(0);
		return imageBoardDao.list();
	}

	@Override
	public void writeImageBoard(ImageBoardVo boardVo) {
		imageBoardDao.writeImageBoard(boardVo);
	}

	@Override
	public ImageBoardViewDto getImageBoardDetailAndComment(int boardId, int creationId) {		
		ImageVo imageVo = new ImageVo();
		int publicCheck = imageBoardDao.publicCheck(boardId);
		if (publicCheck == 1) {
			imageVo = null;
		} else {
			imageVo = imageDao.getAllInformation(creationId);
		}
		
		List<UserVo> userList = userDao.getUserName();
		
		Map<Integer, String> userIdToNameMap = new HashMap<>();

	    for (UserVo user : userList) {
	        userIdToNameMap.put(user.getUserId(), user.getUsername());
	    }
		
		List<BoardCommentVo> commentList = imageBoardDao.getList(boardId);
		
		for (BoardCommentVo comment : commentList) {
	        comment.setUserName(userIdToNameMap.get(comment.getUserId()));
	    }
		
		return new ImageBoardViewDto(imageVo, commentList);
	}

	@Override
	public void writeComment(BoardCommentVo vo) {
		imageBoardDao.writeComment(vo);
	}

	@Override
	public int deleteComment(int commentId, int userId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("commentId", commentId);
		map.put("userId", userId);
		return imageBoardDao.deleteComment(map);
	}

}
