package com.team3webnovel.dao;

import com.team3webnovel.mappers.NovelMapper;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NovelDaoImpl implements NovelDao {

    @Autowired
    private NovelMapper novelMapper; // MyBatis Mapper 주입

    // 소설 리스트 조회
    @Override
    public List<NovelVo> getNovelList() {
        return novelMapper.getNovelList();  // Mapper를 통한 DB 조회
    }

    // 특정 사용자 ID로 소설 리스트 조회
    @Override
    public List<NovelVo> getNovelListByUserId(int userId) {
        return novelMapper.getNovelListByUserId(userId);  // Mapper를 통한 사용자 ID로 소설 리스트 조회
    }

    // 소설 추가
    @Override
    public int insertNovel(NovelVo novel) {
        return novelMapper.insertNovel(novel);  // Mapper를 통한 DB Insert
    }

    // 소설 수정
    @Override
    public int updateNovel(NovelVo novel) {
        return novelMapper.updateNovel(novel);  // Mapper를 통한 DB Update
    }

    // 소설 삭제
    @Override
    public int deleteNovel(int novelId) {
        return novelMapper.deleteNovel(novelId);  // Mapper를 통한 DB Delete
    }

    // 소설 검색 (제목이나 내용에서 검색)
    @Override
    public List<NovelVo> searchNovels(String keyword) {
        return novelMapper.searchNovels(keyword);  // Mapper를 통한 소설 검색
    }

	@Override
	public NovelVo getNovelById(int novelId) {
		// TODO Auto-generated method stub
		return null;
	}

	
//	성민
	@Override
	public void insertNovelDetail(NovelVo vo) {
		novelMapper.insertNovelDetail(vo);
	}

	@Override
	public List<NovelVo> getNovelDetailByNovelId(int novelId) {
		return novelMapper.getNovelDetailByNovelId(novelId);
	}
	
	@Override
	public NovelVo getNovelByNovelId(int novelId) {
		return novelMapper.getNovelByNovelId(novelId);
	}

	@Override
	public void updateStatus(NovelVo vo) {
		novelMapper.updateStatus(vo);
	}

	@Override
	public List<NovelVo> getMainNovelList() {
		return novelMapper.getMainNovelList();
	}

	@Override
	public NovelVo getNovelDetail(NovelVo vo) {
		return novelMapper.getNovelDetail(vo);
	}

	@Override
	public void updateNovelDetail(NovelVo vo) {
		novelMapper.updateNovelDetail(vo);
	}
	
	@Override
	public void updateEpisodeVisibility(NovelVo vo) {
		novelMapper.updateEpisodeVisibility(vo);
	}
	
	

    // 소설 ID로 소설 조회
//    @Override
//    public NovelVo getNovelById(UserVo novelId) {
//        return novelMapper.getNovelListByUserId(novelId);  // Mapper를 통한 소설 조회
//    }
}
