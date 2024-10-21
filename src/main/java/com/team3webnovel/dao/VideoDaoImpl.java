package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.dao.VideoDao;
import com.team3webnovel.mappers.VideoMapper;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.VideoVo;

@Repository
public class VideoDaoImpl implements VideoDao {

    @Autowired 
    private VideoMapper videoMapper;
    
    // 비디오 생성 작업 삽입
    @Override
    public void insertCreation(Map<String, Object> creationData) {
        videoMapper.insertCreation(creationData);
    }

    // 가장 최근 생성된 creation_id 조회
    @Override
    public int getMax() {
        return videoMapper.getLastCreationId();
    }

    // 비디오 데이터 삽입
    @Override
    public void videoGenerate(Map<String, Object> videoData) {
        videoMapper.insertVideoData(videoData);
    }

    // 특정 사용자의 비디오 데이터를 조회
    @Override
    public List<VideoVo> getVideoDataByUserId(CreationVo vo) {
        return videoMapper.getVideoDataByUserId(vo);
    }

    // creationId로 비디오 정보 조회
    @Override
    public VideoVo getAllInformation(int creationId) {
        return videoMapper.getAllInformation(creationId);
    }

	@Override
	public void updateVideoTitle(VideoVo videoVo) {
		videoMapper.updateVideoTitle(videoVo);
		
	}

	@Override
	public void deleteVideoById(int creationId) {
		videoMapper.deleteVideoById(creationId);
		
	}

	@Override
	public void deleteCreationById(int creationId) {
		videoMapper.deleteCreationById(creationId);
		
	}

	@Override
	public void updateCreationId(int creationId) {
		videoMapper.updateCreationId(creationId);
		
	}

    
}
