package com.team3webnovel.mappers;

import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface NovelMapper {

    // 소설 추가
    int insertNovelDetail(NovelVo vo);

    // 특정 사용자 ID로 소설 리스트 조회
    List<NovelVo> getNovelListByUserId(int userId);

    // 소설 리스트 조회 (전체 소설)
    List<NovelVo> getNovelList();

    // 소설 수정
    int updateNovel(NovelVo novel);

    // 소설 삭제
    int deleteNovel(int novelId);

    // 소설 검색 (제목이나 내용에서 검색)
    List<NovelVo> searchNovels(String keyword);
}
