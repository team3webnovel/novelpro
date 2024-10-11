package com.team3webnovel.services;

import java.math.BigDecimal;
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
	    // 좋아요 수를 불러오는 쿼리 실행
	    List<Map<String, Object>> likeCounts = imageBoardDao.likeCounts();
	    System.err.println(likeCounts);

	    for (Map<String, Object> likeCountMap : likeCounts) {
	    	System.err.println(likeCountMap);
	    	System.err.println(likeCountMap.get("BOARDID").getClass());
	    	System.err.println(likeCountMap.get("LIKE_COUNT").getClass());
	    }
	    
	    // 게시글 목록 불러오기
	    List<ImageBoardVo> imageBoardList = imageBoardDao.list();

	    // 좋아요 수를 각 게시글에 매핑
	    for (ImageBoardVo board : imageBoardList) {
	        int boardId = board.getBoardId(); // 게시글의 boardId 가져오기
	        
	        boolean likeFound = false; // 좋아요가 있는지 확인하기 위한 플래그
	        
	        // likeCounts에서 해당 boardId에 대한 좋아요 수 찾기
	        for (Map<String, Object> likeCountMap : likeCounts) {
	            BigDecimal boardIdFromMap = (BigDecimal)likeCountMap.get("BOARDID"); // "BOARDID" 키로 값 가져오기
	            if (boardIdFromMap != null && boardIdFromMap.intValue() == boardId) { // String을 Integer로 변환하여 비교
	            	BigDecimal bigFuckingDecimal = (BigDecimal)likeCountMap.get("LIKE_COUNT"); 
	                board.setLike(bigFuckingDecimal); // 게시글 객체에 좋아요 수 설정
	                System.err.println(board);
	                likeFound = true; // 좋아요 수를 찾았음
	                break; // 찾으면 다음 게시글로 넘어가기
	            }
	        }
	        
	        // 만약 likeFound가 false이면 좋아요가 없으므로 0으로 설정
	        if (!likeFound) {
	            board.setLike(0); // 좋아요 수가 없으면 0으로 설정
	        }
	    }
	    
	    return imageBoardList;
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
		
		Map <String, Integer> map = new HashMap<>();
		map.put("userId", userId);
		map.put("boardId", boardId);
		
		boolean liked = imageBoardDao.check(map);
		
		if (liked) {
            // 이미 좋아요가 눌린 상태라면, 좋아요 취소
            imageBoardDao.unlike(map);
            return false; // 좋아요 취소됨
        } else {
            // 좋아요 추가
            imageBoardDao.like(map);
            return true; // 좋아요 추가됨
        }
	}

}
