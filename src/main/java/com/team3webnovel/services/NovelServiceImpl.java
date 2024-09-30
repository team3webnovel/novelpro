package com.team3webnovel.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.NovelDao;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelDao novelDao; // NovelDao를 주입받아 사용

    // 소설 리스트 조회
    @Override
    public List<NovelVo> getNovelList() {
        return novelDao.getNovelList();
    }


    // 소설 추가
    @Override
    @Transactional
    public void insertNovelDetail(NovelVo novel) {
        novelDao.insertNovelDetail(novel);
    }

    // 소설 수정
    @Override
    
    public void updateNovel(NovelVo novel) {
        novelDao.updateNovel(novel);
    }

    // 소설 삭제
    @Override
    public void deleteNovel(int novelId) {
        novelDao.deleteNovel(novelId);
    }

    // 소설 검색 (제목이나 내용에서 검색)
    @Override
    public List<NovelVo> searchNovels(String keyword) {
        return novelDao.searchNovels(keyword);
    }

	@Override
	public List<NovelVo> getNovelListByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<NovelVo> getNovelListByUserId(UserVo vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
