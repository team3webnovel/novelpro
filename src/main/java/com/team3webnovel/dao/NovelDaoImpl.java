package com.team3webnovel.dao;

import com.team3webnovel.mappers.NovelMapper;
import com.team3webnovel.vo.NovelVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class NovelDaoImpl implements NovelDao {

    @Autowired
    private SqlSession sqlSession; // MyBatis의 SqlSession을 이용
    
    @Autowired
    private NovelMapper novelMapper;

    private static final String NAMESPACE = "com.team3webnovel.mappers.NovelMapper";

    // 소설 리스트 조회
    @Override
    public List<NovelVo> getNovelList() {
        return sqlSession.selectList(NAMESPACE + ".getNovelList");
    }

    // 특정 소설 조회
    @Override
    public NovelVo getNovelById(int novelId) {
        return sqlSession.selectOne(NAMESPACE + ".getNovelById", novelId);
    }

    // 소설 추가
    @Override
    public int insertNovel(NovelVo novel) {
        return novelMapper.insertNovel(novel);
    }

    // 소설 수정
    @Override
    public int updateNovel(NovelVo novel) {
        return sqlSession.update(NAMESPACE + ".updateNovel", novel);
    }

    // 소설 삭제
    @Override
    public int deleteNovel(int novelId) {
        return sqlSession.delete(NAMESPACE + ".deleteNovel", novelId);
    }

    // 소설 검색 (제목이나 내용에서 검색)
    @Override
    public List<NovelVo> searchNovels(String keyword) {
        return sqlSession.selectList(NAMESPACE + ".searchNovels", "%" + keyword + "%");
    }
}