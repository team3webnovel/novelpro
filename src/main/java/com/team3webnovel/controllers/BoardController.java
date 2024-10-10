package com.team3webnovel.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3webnovel.dto.BoardPageDto;
import com.team3webnovel.dto.BoardViewDto;
import com.team3webnovel.services.BoardCommentService;
import com.team3webnovel.services.BoardService;
import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.BoardVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardCommentService commentService;
	
	@GetMapping("/write")
	public String writeForm(Model model, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if (user == null) {
            return "redirect:/login"; // 세션이 없으면 로그인 페이지로 리다이렉트
        }

		return "board/write";
	}
	
	@PostMapping("/write")
	public String write(@ModelAttribute BoardVo vo, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		vo.setUserId(user.getUserId());
		boardService.write(vo);
		return "redirect:/board/list";
	}
	
	@GetMapping("/view/{boardId}")
	public String view(@PathVariable("boardId") int boardId, @RequestParam(defaultValue = "1") int page, Model model) {
		BoardViewDto boardViewDto = boardService.view(boardId);
		
		model.addAttribute("currentPage", page);
		model.addAttribute("board", boardViewDto.getBoardVo());
		model.addAttribute("comments", boardViewDto.getComments());
		return "board/view";
	}
	
	@GetMapping({"", "/", "/list"})
	public String list(@RequestParam(defaultValue = "1") int page, Model model) {
		int pageSize = 10;
		BoardPageDto boardPageDto = boardService.getPagingList(page, pageSize);
		
		model.addAttribute("list", boardPageDto.getBoardList());
		model.addAttribute("totalPages", boardPageDto.getTotalPages());
		model.addAttribute("currentPage", boardPageDto.getCurrentPage());
		
		return "board/board";
	}
	
	@DeleteMapping("/delete/{boardId}")
	@ResponseBody // JSON 응답을 반환하기 위해 사용
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("boardId") int boardId, HttpSession session) {
	    UserVo user = (UserVo) session.getAttribute("user");
	    Map<String, Object> response = new HashMap<>();
	    String message = boardService.delete(boardId, user.getUserId());
	    
	    if ("성공".equals(message)) {
	        response.put("success", true);
	        response.put("message", "게시글이 성공적으로 삭제되었습니다.");
	        return ResponseEntity.ok(response); // 성공 응답 반환
	    } else {
	        response.put("success", false);
	        response.put("message", "게시글 삭제에 실패하였습니다.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 실패 시 에러 응답 반환
	    }
	}

	@RequestMapping(value = "/comment/write", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> comment(HttpSession session, @RequestParam("boardId") int boardId, @RequestParam("comment") String comment) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			UserVo user = (UserVo) session.getAttribute("user");
			
			BoardCommentVo commentVo = new BoardCommentVo();
			commentVo.setBoardId(boardId);
			commentVo.setUserId(user.getUserId());
			commentVo.setContent(comment);
			
			commentService.writeComment(commentVo);
			response.put("success", true);
		} catch (Exception e) {
	        response.put("success", false);
	    }
		return response;
	}
	
	@RequestMapping(value="/comment/delete", method=RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteComment(HttpSession session, @RequestParam("commentId") int commentId){
		Map<String, Object> response = new HashMap<>();
		UserVo user = (UserVo) session.getAttribute("user");
		try {
			int success = commentService.deleteComment(commentId, user.getUserId());
			if (success == 1) {
				response.put("success", true);
			} else {
				response.put("success", false);
			}
		} catch(Exception e) {
			response.put("success", false);
		}
		return response;
	}
}
