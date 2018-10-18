package com.example.crawl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.crawl.service.PostService;
import com.example.crawl.views.PostCrawler;

@Controller
public class MainController {
	
	@Autowired(required=true)
	private PostService postService;
	
	@RequestMapping(value="/crawl", method=RequestMethod.POST)
	public String index(@ModelAttribute("crawler") PostCrawler crawler, ModelMap modelMap) {
		modelMap.addAttribute("crawler", crawler);
		postService.crawl(crawler);
		return "redirect:/";
	}
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		return "index";
	}
}
