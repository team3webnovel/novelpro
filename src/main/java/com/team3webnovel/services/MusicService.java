package com.team3webnovel.services;

<<<<<<< HEAD
import java.util.List;
import com.team3webnovel.vo.MusicVo;

public interface MusicService {
    List<MusicVo> generateMusic(String prompt, boolean makeInstrumental) throws Exception;
=======
import com.team3webnovel.vo.MusicVo;
import java.util.List;
import java.util.Map;

public interface MusicService {
    List<MusicVo> generateMusic(String prompt, boolean makeInstrumental, Map<String, String> errorMap) throws Exception;
>>>>>>> refs/remotes/kyogre/kyogre
}
