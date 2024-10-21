package com.team3webnovel.services;

import com.team3webnovel.dao.VideoDao;
import com.team3webnovel.mappers.VideoMapper;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoDao videoDao;

    @Override
    public List<VideoVo> generateVideo(String prompt, boolean makeHighResolution, Map<String, String> errorMap) {
        try {
            // 비디오 생성 로직 (AI 모델이나 외부 API 호출 로직)
            Map<String, Object> videoParams = new HashMap<>();
            videoParams.put("prompt", prompt);
            videoParams.put("makeHighResolution", makeHighResolution);

            // creation 테이블에 데이터 삽입
            videoMapper.insertCreation(videoParams);
            Integer creationId = videoMapper.getLastCreationId();
            videoParams.put("creationId", creationId);

            // video_data 테이블에 데이터 삽입
            videoMapper.insertVideoData(videoParams);

            // 생성된 비디오 리스트를 반환
            return videoMapper.getVideoByUserIdAndArtForm((Integer) videoParams.get("userId"), 2);

        } catch (Exception e) {
            // 에러 발생 시 에러 맵에 메시지 추가
            errorMap.put("error", "비디오 생성 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<VideoVo> getStoredVideoByUserId(Integer userId) {
        try {
            // userId와 artForm이 2인 비디오를 가져옴
            return videoMapper.getVideoByUserIdAndArtForm(userId, 2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public VideoVo getVideoByCreationId(int creationId) {
        try {
            return videoMapper.getVideoByCreationId(creationId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insertCreation(Map<String, Object> creationData) {
        videoDao.insertCreation(creationData);
    }

    @Override
    public int getMax() {
        return videoDao.getMax();
    }

    @Override
    public void videoGenerate(Map<String, Object> videoData) {
        videoDao.videoGenerate(videoData);
    }

    @Override
    public List<VideoVo> getVideoDataByUserId(CreationVo vo) {
        return videoDao.getVideoDataByUserId(vo);
    }

    @Override
    public VideoVo getAllInformation(int board, int creationId) {
        return videoMapper.getAllInformation(creationId);
    }

	
	@Override
	public void updateVideoTitle(VideoVo videoVo) {
		videoDao.updateVideoTitle(videoVo);
		
	}

	@Override
	public void deleteVideoById(int creationId) {
		videoDao.deleteVideoById(creationId);
		
	}

	@Override
	public void deleteCreationById(int creationId) {
		videoDao.deleteCreationById(creationId);
		
	}

	@Override
	public void updateCreationId(int creationId) {
		videoDao.updateCreationId(creationId);
		
	}
    
    
}
