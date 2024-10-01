package com.team3webnovel.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.ImageBoardDaoImpl;
import com.team3webnovel.dao.ImageDao;
import com.team3webnovel.vo.ImageBoardVo;
import com.team3webnovel.vo.ImageVo;

@Service
public class ImageBoardServiceImpl implements ImageBoardService {

	@Autowired
	private ImageBoardDaoImpl imageBoardDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Override
	public List<ImageBoardVo> list() {
//		ImageVo imageInfo = imageDao.getAllInformation(0);
		return imageBoardDao.list();
	}

}
