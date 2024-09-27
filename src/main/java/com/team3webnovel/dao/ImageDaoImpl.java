package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.ImageMapper;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;

@Repository
public class ImageDaoImpl implements ImageDao {

	@Autowired 
	private ImageMapper imageMapper;
	
	@Override
	public void insertCreation(Map<String, Object> creationData) {
		imageMapper.insertCreation(creationData);
		
	}

	@Override
	public int getMax() {
		return imageMapper.getLastCreationId();
	}

	@Override
	public void imageGenerate(Map<String, Object> imageData) {
		imageMapper.insertImageData(imageData);
	}

	@Override
	public List<ImageVo> getImageDataByUserId(CreationVo vo) {
	    return imageMapper.getImageDataByUserId(vo);  // 조회된 결과를 반환
	}

	
	

}
