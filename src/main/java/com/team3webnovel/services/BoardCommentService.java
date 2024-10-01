package com.team3webnovel.services;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.BoardCommentVo;

public interface BoardCommentService {

	void writeComment(BoardCommentVo vo);
	int deleteComment(int commentId, int userId);
}