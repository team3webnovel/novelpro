package com.team3webnovel.dao;

import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import java.util.List;
import java.util.Map;

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
    
//  성민
    void insertNovelDetail(NovelVo vo);
    
    List<NovelVo> getNovelDetailByNovelId(int novelId);
    
    NovelVo getNovelByNovelId(int novelId);
    
    void updateStatus(NovelVo vo);
    
    List<NovelVo> getMainNovelList();
    
    NovelVo getNovelDetail(NovelVo vo);
    
    void updateNovelDetail(NovelVo vo);
    
    void updateEpisodeVisibility(NovelVo vo);

	void deleteEpisode(int novelId, int episodeNo);
	
	
	boolean check (Map<String, Integer> map);
	
	void like(Map<String, Integer> map);
	
	void unlike(Map<String, Integer> map);
    

}
