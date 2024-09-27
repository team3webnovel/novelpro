package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.vo.GijeBoardVo;

@Repository
public class GijeBoardDao {

    @Autowired
    private SqlSession sqlSession;

//    public List<GijeBoardVo> findAll() {
//        return sqlSession.selectList("gijeBoard.boardList");
//    }

    public void insert(GijeBoardVo board) {
    	sqlSession.insert("gijeBoard.write", board);
    }
    
    public GijeBoardVo select(int boardId) {
    	return sqlSession.selectOne("gijeBoard.view", boardId);
    }
    
    public int getTotal() {
    	return sqlSession.selectOne("gijeBoard.getTotalCount");
    }
    
    public List <GijeBoardVo> boardPaging(Map <String, Integer> map) {
    	return sqlSession.selectList("gijeBoard.pagingBoardList", map);
    }
    
    public int delete(Map <String, Integer> map) {
    	return sqlSession.delete("gijeBoard.deleteBoard", map);
    }
    
    public void viewCount(int boardId) {
    	sqlSession.update("gijeBoard.viewCount", boardId);
    }

}
