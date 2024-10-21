package com.team3webnovel.dto;

import java.util.List;

import com.team3webnovel.vo.BoardVo;

public class BoardPageDto {
	private List<BoardVo> boardList;
	private int totalPages;
	private int currentPage;

	public BoardPageDto(List<BoardVo> boardList, int totalPages, int currentPage) {
        this.boardList = boardList;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

	public List<BoardVo> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<BoardVo> boardList) {
		this.boardList = boardList;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
