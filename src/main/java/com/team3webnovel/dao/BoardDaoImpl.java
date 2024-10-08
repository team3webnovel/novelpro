package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.BoardMapper;
import com.team3webnovel.vo.BoardVo;

@Repository
public class BoardDaoImpl implements BoardDao {

    @Autowired
    private BoardMapper BoardMapper;

    @Override
    public void insert(BoardVo board) {
    	BoardMapper.write (board);
    }
    
    @Override
    public BoardVo select(int boardId) {
    	return BoardMapper.view (boardId);
    }
    
    @Override
    public int getTotal() {
    	return BoardMapper.getTotalCount();
    }
    
    @Override
    public List <BoardVo> boardPaging(Map <String, Integer> map) {
    	return BoardMapper.pagingBoardList(map);
    }
    
    @Override
    public int delete(Map <String, Integer> map) {
    	return BoardMapper.deleteBoard(map);
    }
    
    @Override
    public void viewCount(int boardId) {
    	BoardMapper.viewCount(boardId);
    }

}
