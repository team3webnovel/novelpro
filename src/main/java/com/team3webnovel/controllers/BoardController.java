package com.team3webnovel.controllers;

import com.team3webnovel.vo.BoardVo;
import com.team3webnovel.dao.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardDao boardDao;  // 인터페이스로 DI

    // 게시글 목록 페이지
    @GetMapping("/board")
    public String getPostList(Model model) {
        List<BoardVo> posts = boardDao.getAllPosts();
        model.addAttribute("posts", posts);
        return "users/board"; // board.jsp 파일을 렌더링
    }

    // 게시글 상세 페이지
    @GetMapping("/post/{id}")
    public String getPostDetail(@PathVariable("id") int id, Model model) {
        BoardVo post = boardDao.getPostById(id);
        if (post == null) {
            return "error/404";  // 게시글을 찾을 수 없을 경우 404 페이지로 이동
        }
        model.addAttribute("post", post);
        return "users/post"; // post_detail.jsp 파일을 렌더링
    }
}
