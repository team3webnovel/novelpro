package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.team3webnovel.vo.NovelVo;

@Mapper
public interface NovelMapper {

    // 소설 추가
    int insertNovel(NovelVo vo);

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
    
    // 소설의 디테일 정보 가져오기
    List<NovelVo> getEpisodesByNovelId(int novelId);
    
//    성민
    void insertNovelDetail(NovelVo vo);
    
    List<NovelVo> getNovelDetailByNovelId(int novelId);
    
    NovelVo getNovelByNovelId(int novelId);
    
    void updateStatus(NovelVo vo);
    
    List<NovelVo> getMainNovelList();
    
    NovelVo getNovelDetail(NovelVo vo);
    
    void updateNovelDetail(NovelVo vo);
    
    void updateEpisodeVisibility(NovelVo vo);

    void deleteEpisode(Map<String, Object> params);
    
    
	int check(Map<String, Integer> map);
	
	void pushLike(Map<String, Integer> map);
	
	void unlike(Map<String, Integer> map);
}
