package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.GijeBoardMapper;
import com.team3webnovel.vo.GijeBoardVo;

@Repository
public class GijeBoardDaoImpl implements GijeBoardDao {

    @Autowired
    private GijeBoardMapper gijeBoard;

    @Override
    public void insert(GijeBoardVo board) {
    	gijeBoard.write (board);
    }
    
    @Override
    public GijeBoardVo select(int boardId) {
    	return gijeBoard.view (boardId);
    }
    
    @Override
    public int getTotal() {
    	return gijeBoard.getTotalCount();
    }
    
    @Override
    public List <GijeBoardVo> boardPaging(Map <String, Integer> map) {
    	return gijeBoard.pagingBoardList(map);
    }
    
    @Override
    public int delete(Map <String, Integer> map) {
    	return gijeBoard.deleteBoard(map);
    }
    
    @Override
    public void viewCount(int boardId) {
    	gijeBoard.viewCount(boardId);
    }

}
