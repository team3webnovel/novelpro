package com.team3webnovel.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    // 새 소설 생성
    @Override
    @Transactional
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

    @Override
    public List<NovelVo> getNovelListByUserId(int userId) {
        // NovelDao를 사용하여 userId에 해당하는 소설 목록을 가져옵니다.
        return novelDao.getNovelListByUserId(userId);
    }

//  성민
	@Override
	public void insertNovelDetail(NovelVo vo) {
		novelDao.insertNovelDetail(vo);
	}


	@Override
	public List<NovelVo> getNovelDetailByNovelId(int novelId) {
		return novelDao.getNovelDetailByNovelId(novelId);
	}

	@Override
	public NovelVo getNovelByNovelId(int novelId) {
		return novelDao.getNovelByNovelId(novelId);
	}


	@Override
	public void updateStatus(NovelVo vo) {
		novelDao.updateStatus(vo);
	}


	@Override
	public List<NovelVo> getMainNovelList() {
		return novelDao.getMainNovelList();
	}


	@Override
	public NovelVo getNovelDetail(NovelVo vo) {
		return novelDao.getNovelDetail(vo);
	}

	@Override
	public void updateNovelDetail(NovelVo vo) {
		novelDao.updateNovelDetail(vo);
	}
	
	@Override
	public void updateEpisodeVisibility(NovelVo vo) {
		novelDao.updateEpisodeVisibility(vo);
	}


	@Override
	public void deleteEpisode(int novelId, int episodeNo) {
		novelDao.deleteEpisode(novelId, episodeNo);
		
	}
	
	@Override
	public boolean toggleLike(int userId, int novelId) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("novelId", novelId);
		map.put("userId", userId);

		
		boolean liked = novelDao.check(map);
		
		if (liked) {
            // 이미 좋아요가 눌린 상태라면, 좋아요 취소
            novelDao.unlike(map);
            return false; // 좋아요 취소됨
        } else {
            // 좋아요 추가
        	novelDao.like(map);
            return true; // 좋아요 추가됨
        }
	}
	
	
	
	
	

    
    
}
