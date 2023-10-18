package com.lion09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.lion09.member.Member;

@Controller
public class LionController {
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/faqs")
	public String faqs() {
		return "faqs";
	}
	
	@GetMapping("/wishList")
	public String wishList() {
		return "wishList";
	}
	
	@GetMapping("/account")
	public String account() {
		return "account";
	}
	
}
