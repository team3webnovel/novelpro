package com.team3webnovel.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3webnovel.dao.ImageBoardDao;
import com.team3webnovel.dao.ImageDao;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;

@Service
public class ImageServiceImpl implements ImageService {
    
    @Autowired
    private ImageBoardDao imageBoardDao;
    
    @Autowired
    private ImageDao imageDao; 
    
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
         return null;
      } else {
         imageVo = imageDao.getAllInformation(creationId);
         return imageVo;
      }
   }
   
    @Override
    public void updateImageTitle(ImageVo imageVo) {
        // DAO 호출하여 이미지 제목 업데이트
        imageDao.updateImageTitle(imageVo);
    }

   @Override
   public void deleteImageById(int creationId) {
      imageDao.deleteImageById(creationId);
      
   }

   @Override
   public void deleteCreationById(int creationId) {
      imageDao.deleteCreationById(creationId);
      
   }
   
   @Override
   public void updateCreationId(int creationId) {
      imageDao.updateCreationId(creationId);
      
   }
    
    
   
   // 성민

}
