package com.team3webnovel.services;

import java.util.List;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

public interface NovelService {

    // 소설 리스트 조회
    List<NovelVo> getNovelList();

    // 특정 소설 조회
    List<NovelVo> getNovelListByUserId(int userId);

    // 새 소설 생성
    void insertNovel(NovelVo novel); // 소설 작성

    // 소설 수정
    void updateNovel(NovelVo novel);

    // 소설 삭제
    void deleteNovel(int novelId);
    
    void deleteEpisode(int novelId, int episodeNo);

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
    
    public boolean toggleLike(int userId, int boardId);
}
