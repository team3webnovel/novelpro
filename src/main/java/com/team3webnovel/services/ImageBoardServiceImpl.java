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
	public String deleteImageBoard(int boardId, int userId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("boardId", boardId);
		map.put("userId", userId);
		
		int success = imageBoardDao.deleteImageBoard(map);
		String message;
		if (success == 1) {
			message = "성공";
		} else {
			message = "실패";
		}
		return message;
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

	@Override
	public boolean toggleLike(int userId, int boardId) {
		
		boolean liked = imageBoardDao
		
		if (liked) {
            // 이미 좋아요가 눌린 상태라면, 좋아요 취소
            likeRepository.deleteByUserIdAndBoardId(userId, boardId);
            return false; // 좋아요 취소됨
        } else {
            // 좋아요 추가
            Like like = new Like(userId, boardId);
            likeRepository.save(like);
            return true; // 좋아요 추가됨
        }
	}

}
