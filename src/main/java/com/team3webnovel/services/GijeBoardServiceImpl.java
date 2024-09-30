package com.team3webnovel.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.BoardCommentDaoImpl;
import com.team3webnovel.dao.GijeBoardDaoImpl;
import com.team3webnovel.dao.UserDao;
import com.team3webnovel.dto.BoardPageDto;
import com.team3webnovel.dto.BoardViewDto;
import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.GijeBoardVo;
import com.team3webnovel.vo.UserVo;

@Service
public class GijeBoardServiceImpl implements GijeBoardService {
	
	@Autowired
	private GijeBoardDaoImpl boardDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BoardCommentDaoImpl commentDao;
	
	@Override
	public void write(GijeBoardVo vo) {
		boardDao.insert(vo);
	}
	
	@Override
	public BoardViewDto view(int boardId) {
	    boardDao.viewCount(boardId);

	    List<UserVo> userList = userDao.getUserName();
	    Map<Integer, String> userIdToNameMap = new HashMap<>();

	    for (UserVo user : userList) {
	        userIdToNameMap.put(user.getUserId(), user.getUsername());
	    }

	    GijeBoardVo boardVo = boardDao.select(boardId);

	    boardVo.setUserName(userIdToNameMap.get(boardVo.getUserId()));

	    List<BoardCommentVo> commentList = commentDao.getList(boardId);

	    for (BoardCommentVo comment : commentList) {
	        comment.setUserName(userIdToNameMap.get(comment.getUserId()));
	    }

	    return new BoardViewDto(boardVo, commentList);
	}

	@Override
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
		List <UserVo> userList = userDao.getUserName();
		
		for (GijeBoardVo boardVo : boardList) {
			for (UserVo user : userList) {
				if (boardVo.getUserId() == user.getUserId()) {
					boardVo.setUserName(user.getUsername());
					break;
				}
			}
		}
		
		return new BoardPageDto(boardList, totalPages, page);
	}
	
	@Override
	public String delete(int boardId, int userId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("boardId", boardId);
		map.put("userId", userId);
		
		int success = boardDao.delete(map);
		String message;
		if (success == 1) {
			message = "성공";
		} else {
			message = "실패";
		}
		return message;
	}
}
