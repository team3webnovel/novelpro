package com.team3webnovel.controllers;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3webnovel.services.ImageService;
import com.team3webnovel.services.MusicService;
import com.team3webnovel.services.NovelService;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.MusicVo;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
public class NovelController {

    @Autowired
    private NovelService novelService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private OpenAiService openAiService;

    // My Storage 페이지
    @GetMapping("/storage")
    public String showMyStoragePage(HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        CreationVo creationVo = new CreationVo();
        creationVo.setUserId(user.getUserId());
        creationVo.setArtForm(2);

        List<ImageVo> imageList = imageService.getImageDataByUserId(creationVo);
        model.addAttribute("imageList", imageList);

        List<NovelVo> novelList = novelService.getNovelListByUserId(user.getUserId());
        model.addAttribute("novelList", novelList);

        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId());
        model.addAttribute("musicList", musicList);

        return "storage/my_storage";
    }

    // 글쓰기 페이지로 이동
    @GetMapping("/write/{novelId}")
    public String showWritePage(@PathVariable("novelId") int novelId, HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        CreationVo vo = new CreationVo();
        vo.setUserId(user.getUserId());

        List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
        model.addAttribute("imageList", imageList);

        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId());
        model.addAttribute("musicList", musicList);

        return "storage/new_episode";
    }

    // 글쓰기 처리
    @PostMapping("/write/{novelId}")
    public String write(@ModelAttribute NovelVo vo, HttpSession session,
                        @RequestParam("illust") int illust,
                        @RequestParam("bgm") int bgm,
                        @RequestParam("title") String title,
                        @RequestParam("episode") int episode,
                        @RequestParam("content") String content) {

        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        vo.setUserId(user.getUserId());
        vo.setTitle(title);
        vo.setImageId(illust);
        vo.setBgmId(bgm);
        vo.setEpisodeNo(episode);
        vo.setContents(content);
        vo.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        novelService.insertNovelDetail(vo);

        return "redirect:/my_storage";
    }

    // 소설 생성 페이지로 이동
    @GetMapping("/cover")
    public String insertCoverPage(HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        CreationVo vo = new CreationVo();
        vo.setUserId(user.getUserId());

        List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
        model.addAttribute("imageList", imageList);

        return "storage/new_novel";
    }

    // 소설 생성 처리
    @PostMapping("/cover")
    public String writeNovel(@ModelAttribute NovelVo vo, HttpSession session,
                             @RequestParam("illust") int illust,
                             @RequestParam("title") String title,
                             @RequestParam("intro") String intro,
                             @RequestParam("genre") String genre) {

        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        vo.setUserId(user.getUserId());
        vo.setTitle(title);
        vo.setIntro(intro);
        vo.setGenre(genre);
        vo.setCreationId(illust);
        vo.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        novelService.insertNovel(vo);

        return "redirect:/my_storage";
    }

    // 소설 상세 페이지
    @GetMapping("/novel_detail/{novelId}")
    public String detailPage(@PathVariable("novelId") int novelId, Model model, HttpSession session) {
        NovelVo novelVo = novelService.getNovelByNovelId(novelId);
        List<NovelVo> detailList = novelService.getNovelDetailByNovelId(novelId);

        model.addAttribute("novelCover", novelVo);

        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null || novelVo.getUserId() != user.getUserId()) {
            detailList = detailList.stream()
                    .filter(novel -> "public".equals(novel.getVisibility()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("detailList", detailList);

        return (user == null || novelVo.getUserId() != user.getUserId()) ? 
                "storage/detail_user" : "storage/novel_detail";
    }

    // 에피소드 업데이트
    @GetMapping("/novel/episode/{novelId}/{episodeNo}")
    public String episodeUpdate(@PathVariable int novelId, 
                                @PathVariable int episodeNo, 
                                Model model, HttpSession session) {

        NovelVo novelVo = new NovelVo();
        novelVo.setNovelId(novelId);
        novelVo.setEpisodeNo(episodeNo);
        novelVo = novelService.getNovelDetail(novelVo);

        model.addAttribute("episode", novelVo);

        CreationVo vo = new CreationVo();
        vo.setUserId(novelVo.getUserId());

        List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
        model.addAttribute("imageList", imageList);

        List<MusicVo> musicList = musicService.getStoredMusicByUserId(novelVo.getUserId());
        model.addAttribute("musicList", musicList);

        return "storage/update_episode";
    }

    // 에피소드 업데이트 처리
    @PostMapping("/episode/update/{novelId}/{episodeNo}")
    public String episodeUpdateString(@PathVariable int novelId, 
                                      @PathVariable int episodeNo,
                                      @RequestParam("illust") int illust,
                                      @RequestParam("bgm") int bgm,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content) {

        NovelVo vo = new NovelVo();
        vo.setNovelId(novelId);
        vo.setEpisodeNo(episodeNo);
        vo.setTitle(title);
        vo.setImageId(illust);
        vo.setBgmId(bgm);
        vo.setContents(content);

        novelService.updateNovelDetail(vo);

        return "redirect:/novel_detail/" + novelId;
    }

    // 에피소드 공개/비공개 상태 업데이트
    @PostMapping("/updateVisibility")
    public void updateVisibility(@RequestParam int novelId, 
                                 @RequestParam int episodeNo, 
                                 @RequestParam String visibility) {

        NovelVo vo = new NovelVo();
        vo.setNovelId(novelId);
        vo.setEpisodeNo(episodeNo);
        vo.setVisibility(visibility);

        novelService.updateEpisodeVisibility(vo);
    }

    // 이미지 제목 업데이트
    @PostMapping("/update-image-title")
    @ResponseBody
    public Map<String, Object> updateImageTitle(@RequestBody ImageVo imageVo) {
        Map<String, Object> response = new HashMap<>();
        try {
            imageService.updateImageTitle(imageVo);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
        }
        return response;
    }

    // 이미지 삭제
    @PostMapping("/delete-image")
    @ResponseBody
    public Map<String, Object> deleteImage(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            int creationId = (int) requestData.get("creationId");
            imageService.updateCreationId(creationId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
        }
        return response;
    }
}
