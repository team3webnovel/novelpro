package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.MusicMapper;
import com.team3webnovel.vo.MusicVo;

@Repository
public class MusicDaoImpl implements MusicDao{

	@Autowired
	private MusicMapper musicMapper;
	
	@Override
	public void insertCreation(Map<String, Object> params) {
		musicMapper.insertCreation(params);
	}

	@Override
	public Integer getLastCreationId() {
		return musicMapper.getLastCreationId();
	}

	@Override
	public void insertMusicData(Map<String, Object> params) {
		musicMapper.insertMusicData(params);
		
	}

	@Override
	public List<MusicVo> getMusicByUserIdAndArtForm(Integer userId, int artForm) {
		return musicMapper.getMusicByUserIdAndArtForm(userId, artForm);
	}

	@Override
	public MusicVo getMusicByCreationId(int creationId) {
		return musicMapper.getMusicByCreationId(creationId);
	}

}
