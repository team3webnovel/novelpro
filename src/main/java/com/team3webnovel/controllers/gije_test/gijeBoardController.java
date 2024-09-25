package com.team3webnovel.controllers.gije_test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team3webnovel.services.GijeBoardService;
import com.team3webnovel.vo.GijeBoardVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/gije")
public class gijeBoardController {
	
	@Autowired
	private GijeBoardService boardService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<GijeBoardVo> boards = boardService.getBoardList();
		model.addAttribute("list", boards);
		return "gijeTest/list";
	}
	
	@GetMapping("/write")
	public String writeForm(Model model, HttpSession session) {
		
		if ((session.getAttribute("user_id") == null)) {
            return "redirect:/login"; // 세션이 없으면 로그인 페이지로 리다이렉트
        }

		return "gijeTest/write";
	}
	
	@PostMapping("/write")
	public String write(@ModelAttribute GijeBoardVo vo, HttpSession session) {
		vo.setUserId((Integer)session.getAttribute("user_id"));
		boardService.write(vo);
		return "redirect:/gije/list";
	}
	
	@GetMapping("/view/{boardId}")
	public String view(@PathVariable("boardId") int boardId, Model model) {
		model.addAttribute("board", boardService.view(boardId));
		return "gijeTest/view";
	}
}
