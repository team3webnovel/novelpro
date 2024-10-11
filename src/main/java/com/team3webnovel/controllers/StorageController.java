package com.team3webnovel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team3webnovel.services.ImageService;
import com.team3webnovel.services.MusicService;
import com.team3webnovel.services.NovelService;
import com.team3webnovel.services.VideoService;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.MusicVo;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;
import com.team3webnovel.vo.VideoVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/storage")
public class StorageController {
	
	@Autowired ImageService imageService;	
	
	@Autowired NovelService novelService;
	
    @Autowired MusicService musicService;
    
    @Autowired VideoService videoService;
	
    @RequestMapping({"/", "/main", ""})
    public String showMyStoragePage(HttpSession session, Model model) {
        // 세션에서 로그인한 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");
        session.removeAttribute("imageGenerated");  // 상태 체크 후 세션에서 제거
        
        if (user == null) {
            // 로그인이 되어 있지 않으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        
        // 사용자의 이미지 리스트 가져오기
        CreationVo creationVo = new CreationVo();
        creationVo.setUserId(user.getUserId());
        creationVo.setArtForm(2); // 예: 소설 형식을 나타내는 코드 2
        
        // 이미지 데이터 모델에 추가 및 로그 출력
        List<ImageVo> imageList = imageService.getImageDataByUserId(creationVo);
        model.addAttribute("imageList", imageList);
        System.err.println("Image List: " + imageList);  // 이미지 리스트 로그 출력

        // 사용자 ID로 소설 리스트 가져오기
        List<NovelVo> novelList = novelService.getNovelListByUserId(user.getUserId());
        model.addAttribute("novelList", novelList);
        System.err.println("Novel List: " + novelList);  // 소설 리스트 로그 출력
        
        // userId와 artForm = 1인 음악들을 가져오기
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId()); // null을 사용하여 전체 데이터를 가져올 수도 있음
        
        List<VideoVo> videoList = videoService.getVideoDataByUserId(creationVo); // null을 사용하여 전체 데이터를 가져올 수도 있음
        model.addAttribute("videoList", videoList);
        System.err.println("비디오정보!!!!!!!!!!" + videoList);
        // 가져온 음악 데이터를 모델에 추가
        model.addAttribute("musicList", musicList);

        // 마이 스토리지 페이지로 이동
        return "storage/my_storage"; // JSP 파일 경로
    }
}
