package com.team3webnovel.services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.ImageBoardDao;
import com.team3webnovel.dao.ImageDao;
import com.team3webnovel.mappers.ImageMapper;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;
    
    @Autowired
    private ImageBoardDao imageBoardDao;
    
    @Autowired
    private ImageDao imageDao; 

    @Override
    public List<ImageVo> generateImage(String prompt, boolean makeHighResolution, Map<String, String> errorMap) {
        try {
            // 이미지 생성 로직 (AI 모델이나 외부 API 호출 로직)
            Map<String, Object> imageParams = new HashMap<>();
            imageParams.put("prompt", prompt);
            imageParams.put("makeHighResolution", makeHighResolution);

            // creation 테이블에 데이터 삽입
            imageMapper.insertCreation(imageParams);
            Integer creationId = imageMapper.getLastCreationId();
            imageParams.put("creationId", creationId);

            // image_data 테이블에 데이터 삽입
            imageMapper.insertImageData(imageParams);

            // 생성된 이미지 리스트를 반환
            return imageMapper.getImageByUserIdAndArtForm((Integer) imageParams.get("userId"), 2);

        } catch (Exception e) {
            // 에러 발생 시 에러 맵에 메시지 추가
            errorMap.put("error", "이미지 생성 중 오류가 발생했습니다: " + e.getMessage());
            return null;
        }
    }
    
    

    @Override
    public List<ImageVo> getStoredImageByUserId(Integer userId) {
        try {
            // userId와 artForm이 2인 이미지를 가져옴
            return imageMapper.getImageByUserIdAndArtForm(userId, 2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ImageVo getImageByCreationId(int creationId) {
        try {
            return imageMapper.getImageByCreationId(creationId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    // 성민
	@Override
	public void insertCreation(Map<String, Object> creationData) {
		imageDao.insertCreation(creationData);
	}

	@Override
	public int getMax() {
		return imageDao.getMax();
	}

	@Override
	public void imageGenerate(Map<String, Object> imageData) {
		imageDao.imageGenerate(imageData);
	}
	
	@Override
	public void fontGenerate(Map<String, Object> imageData) {
		imageDao.fontGenerate(imageData);
	}

	@Override
	public List<ImageVo> getImageDataByUserId(CreationVo vo) {
		return imageDao.getImageDataByUserId(vo);
	}

	@Override
	public ImageVo getAllInformation(int boardId, int creationId) {
		ImageVo imageVo = new ImageVo();
		int publicCheck = imageBoardDao.publicCheck(boardId);
		if (publicCheck == 1) {
			return imageVo;
		} else {
			imageVo = imageDao.getAllInformation(creationId);
			return imageVo;
		}
	}
	// 성민
}
