package com.team3webnovel.dao;

import com.team3webnovel.vo.PwVo;

public interface PwDao {
	
	
	PwVo getPasswordByName(String pwName);
	
}
