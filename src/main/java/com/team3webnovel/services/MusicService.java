package com.team3webnovel.services;

import com.team3webnovel.vo.MusicVo;
import java.util.List;
import java.util.Map;

public interface MusicService {
    List<MusicVo> generateMusic(String prompt, boolean makeInstrumental, Map<String, String> errorMap) throws Exception;
}
