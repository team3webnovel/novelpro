package com.team3webnovel.dto;

import java.util.List;

import com.team3webnovel.vo.GijeBoardVo;

public class BoardPageDto {
	private List<GijeBoardVo> boardList;
	private int totalPages;
	private int currentPage;

	public BoardPageDto(List<GijeBoardVo> boardList, int totalPages, int currentPage) {
        this.boardList = boardList;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

	public List<GijeBoardVo> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<GijeBoardVo> boardList) {
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
