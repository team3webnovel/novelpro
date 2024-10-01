package com.team3webnovel.controllers.gije_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team3webnovel.services.ImageBoardServiceImpl;

@Controller
@RequestMapping("/gije")
public class ImageBoardController {

	@Autowired
	private ImageBoardServiceImpl imageBoardService;
	
	@GetMapping("/image/board")
	public String imageBoard(Model model) {
		model.addAttribute("list", imageBoardService.list());
		return "gijeTest/image_board";
	}
}
