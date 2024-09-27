package com.team3webnovel.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.ImageMapper;

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
	
	

}
