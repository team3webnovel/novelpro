package com.team3webnovel.mappers;

import com.team3webnovel.vo.PwVo;

public interface PwMapper {
    PwVo getPasswordByName(String pwName);
}
