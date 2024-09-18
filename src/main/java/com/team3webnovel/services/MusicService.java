package com.team3webnovel.services;

import java.util.List;
import com.team3webnovel.vo.MusicVo;

public interface MusicService {
    List<MusicVo> generateMusic(String prompt, boolean makeInstrumental) throws Exception;
}
