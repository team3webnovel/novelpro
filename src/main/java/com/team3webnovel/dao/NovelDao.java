package com.team3webnovel.dao;

import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import java.util.List;

public interface NovelDao {
    
    // 소설 리스트 조회
    List<NovelVo> getNovelList();
    
    // 특정 사용자 ID로 소설 리스트 조회
    List<NovelVo> getNovelListByUserId(int userId);
    
    // 특정 소설 조회
    NovelVo getNovelById(int novelId);
    
    // 새 소설 생성
    int insertNovel(NovelVo novel);
    
    // 소설 수정
    int updateNovel(NovelVo novel);
    
    // 소설 삭제
    int deleteNovel(int novelId);
    
    // 소설 검색 (제목이나 내용에서 검색)
    List<NovelVo> searchNovels(String keyword);

}
