package com.team3webnovel.services;

import com.team3webnovel.dto.BoardPageDto;
import com.team3webnovel.dto.BoardViewDto;
import com.team3webnovel.vo.GijeBoardVo;

public interface GijeBoardService {
	public void write(GijeBoardVo vo);
	public BoardViewDto view(int boardId);
	public BoardPageDto getPagingList(int page, int pageSize);
	public String delete(int boardId, int userId);
}
