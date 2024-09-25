package com.team3webnovel.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.team3webnovel.vo.PwVo;

@Mapper
public interface PwMapper {
    PwVo getPasswordByName(String pwName);

}
