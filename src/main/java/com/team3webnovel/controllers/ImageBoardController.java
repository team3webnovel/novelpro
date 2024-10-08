package com.team3webnovel.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3webnovel.services.ImageBoardServiceImpl;
import com.team3webnovel.services.ImageService;
import com.team3webnovel.vo.ImageBoardVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/image/board")
public class ImageBoardController {

	@Autowired
	private ImageBoardServiceImpl imageBoardService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping({"", "/", "list"})
	public String imageBoard(Model model) {
		model.addAttribute("list", imageBoardService.list());
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
	
	@PostMapping("/detail/{creationId}")
	@ResponseBody
	public ImageVo getImageDetail(@PathVariable("creationId") int creationId, @RequestParam("boardId") int boardId ) {
		
		return imageService.getAllInformation(boardId, creationId);
	}
}