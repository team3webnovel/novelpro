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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3webnovel.dto.ImageBoardViewDto;
import com.team3webnovel.services.ImageBoardServiceImpl;
import com.team3webnovel.vo.BoardCommentVo;
import com.team3webnovel.vo.ImageBoardVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/images/board")
public class ImageBoardController {

	@Autowired
	private ImageBoardServiceImpl imageBoardService;
	
	@GetMapping({"", "/", "list"})
	public String imageBoard(Model model, HttpSession session) {
		model.addAttribute("list", imageBoardService.list());
		UserVo user = (UserVo) session.getAttribute("user");
		model.addAttribute("userId", user.getUserId());
		return "board/image_board";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map <String, Object> writeImageBoard(HttpSession session, @RequestBody Map<String, String> params){
		String publicOption = params.get("publicOption");
	    int creationId = Integer.parseInt(params.get("creationId"));
	    String comment = params.get("comment");
		
		Map<String, Object> response = new HashMap<>();
		int publicCheck = 0;
		if (!"public".equals(publicOption)) {
			publicCheck = 1;
		}
		
		UserVo user = (UserVo) session.getAttribute("user");
		
		ImageBoardVo boardVo = new ImageBoardVo(user.getUserId(), creationId, comment, publicCheck);
		
		try {
			imageBoardService.writeImageBoard(boardVo);
			response.put("success", true);
		} catch (Exception e) {
	        response.put("success", false);
	    }
		
		return response;
	}
	
	@DeleteMapping("/delete/{boardId}")
	@ResponseBody // JSON 응답을 반환하기 위해 사용
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("boardId") int boardId, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
	    Map<String, Object> response = new HashMap<>();
	    String message = imageBoardService.deleteImageBoard(boardId, user.getUserId());
	    
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
	
	@PostMapping("/detail/{creationId}")
	@ResponseBody
	public ImageBoardViewDto getImageDetail(@PathVariable("creationId") int creationId, @RequestParam("boardId") int boardId ) {
		return imageBoardService.getImageBoardDetailAndComment(boardId, creationId);
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
			
			imageBoardService.writeComment(commentVo);
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
			int success = imageBoardService.deleteComment(commentId, user.getUserId());
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
	


	@PostMapping("/like/{boardId}")
	public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable int boardId, @RequestBody Map<String, Object> requestBody){
		String userIdStr = (String) requestBody.get("userId");
		int userId = Integer.parseInt(userIdStr);
		boolean liked = imageBoardService.toggleLike(userId, boardId);
		
		Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("liked", liked); // true: 좋아요 추가됨, false: 취소됨

        return ResponseEntity.ok(response);
	}

}
