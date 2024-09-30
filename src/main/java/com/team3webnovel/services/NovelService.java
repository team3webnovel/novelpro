package com.team3webnovel.services;

import com.team3webnovel.vo.NovelVo;
import java.util.List;

public interface NovelService {

    // 소설 리스트 조회
    List<NovelVo> getNovelList();

    // 특정 소설 조회
    NovelVo getNovelById(int novelId);

    // 소설 추가
    void insertNovel(NovelVo novel);

    // 소설 수정
    void updateNovel(NovelVo novel);

    // 소설 삭제
    void deleteNovel(int novelId);

    // 소설 검색 (제목이나 내용에서 검색)
    List<NovelVo> searchNovels(String keyword);
}