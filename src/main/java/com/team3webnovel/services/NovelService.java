package com.team3webnovel.services;

import java.util.List;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

public interface NovelService {

    // 소설 리스트 조회
    List<NovelVo> getNovelList();

    // 특정 소설 조회
    List<NovelVo> getNovelListByUserId(int userId);

    // 소설 추가
    void insertNovelDetail(NovelVo novel); // 소설 작성

    // 소설 수정
    void updateNovel(NovelVo novel);

    // 소설 삭제
    void deleteNovel(int novelId);

    // 소설 검색 (제목이나 내용에서 검색)
    List<NovelVo> searchNovels(String keyword);

	List<NovelVo> getNovelListByUserId(UserVo vo);

}
