package com.team3webnovel.services;

import com.team3webnovel.dto.BoardPageDto;
import com.team3webnovel.dto.BoardViewDto;
import com.team3webnovel.vo.BoardVo;

public interface BoardService {
	public void write(BoardVo vo);
	public BoardViewDto view(int boardId);
	public BoardPageDto getPagingList(int page, int pageSize);
	public String delete(int boardId, int userId);
}
