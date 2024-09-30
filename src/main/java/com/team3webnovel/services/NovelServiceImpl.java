package com.team3webnovel.services;

import com.team3webnovel.dao.NovelDao;

import com.team3webnovel.services.NovelService;
import com.team3webnovel.vo.NovelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelDao novelDao;

    // 소설 리스트 조회
    @Override
    public List<NovelVo> getNovelList() {
        return novelDao.getNovelList();
    }

    // 특정 소설 조회
    @Override
    public NovelVo getNovelById(int novelId) {
        return novelDao.getNovelById(novelId);
    }

    // 소설 추가
    @Override
    public void insertNovel(NovelVo novel) {
        novelDao.insertNovel(novel);
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
}