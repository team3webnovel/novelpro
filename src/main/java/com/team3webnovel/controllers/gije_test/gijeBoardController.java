package com.team3webnovel.controllers.gije_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team3webnovel.dto.BoardPageDto;
import com.team3webnovel.services.GijeBoardService;
import com.team3webnovel.vo.GijeBoardVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/gije")
public class gijeBoardController {
	
	@Autowired
	private GijeBoardService boardService;
	
//	@GetMapping("/list")
//	public String list(Model model) {
//		List<GijeBoardVo> boards = boardService.getBoardList();
//		model.addAttribute("list", boards);
//		return "gijeTest/list";
//	}
	
	@GetMapping("/write")
	public String writeForm(Model model, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if (user == null) {
            return "redirect:/login"; // 세션이 없으면 로그인 페이지로 리다이렉트
        }

		return "gijeTest/write";
	}
	
	@PostMapping("/write")
	public String write(@ModelAttribute GijeBoardVo vo, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		vo.setUserId(user.getUserId());
		boardService.write(vo);
		return "redirect:/gije/board";
	}
	
	@GetMapping("/view/{boardId}")
	public String view(@PathVariable("boardId") int boardId, @RequestParam(defaultValue = "1") int page, Model model) {
		model.addAttribute("board", boardService.view(boardId));
		model.addAttribute("currentPage", page);
		return "gijeTest/view";
	}
	
	@GetMapping("/board")
	public String list(@RequestParam(defaultValue = "1") int page, Model model) {
		int pageSize = 10;
		BoardPageDto boardPageDto = boardService.getPagingList(page, pageSize);
		
		model.addAttribute("list", boardPageDto.getBoardList());
		model.addAttribute("totalPages", boardPageDto.getTotalPages());
		model.addAttribute("currentPage", boardPageDto.getCurrentPage());
		
		return "gijeTest/list";
	}
	
	@PostMapping("/delete/{boardId}")
	public String delete(@PathVariable("boardId") int boardId, HttpSession session, RedirectAttributes redirectAttributes) {
	    UserVo user = (UserVo) session.getAttribute("user");
	    String message = boardService.delete(boardId, user.getUserId());
	    
	    if ("성공".equals(message)) {
	        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
	    } else {
	        redirectAttributes.addFlashAttribute("message", "게시글 삭제에 실패하였습니다.");
	    }
	    
	    return "redirect:/gije/board"; // 게시글 목록 페이지로 리디렉션
	}
}
