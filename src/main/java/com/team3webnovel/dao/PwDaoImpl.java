package com.team3webnovel.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.PwMapper;
import com.team3webnovel.vo.PwVo;

@Repository
public class PwDaoImpl implements PwDao {

	@Autowired
	private PwMapper pwMapper;
	
	@Override
	public PwVo getPasswordByName(String pwName) {
		return pwMapper.getPasswordByName(pwName);
	}

}
