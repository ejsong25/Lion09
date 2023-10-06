package com.lion09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.lion09.SessionConst;
import com.lion09.member.Member;

@Controller
public class LionController {
	
	@GetMapping("/")
	public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
		model.addAttribute("loginMember", loginMember);
		return "index";
	}
	
	@GetMapping("/faqs")
	public String faqs() {
		return "faqs";
	}
	
	@GetMapping("/qna")
	public String qna() {
		return "qna";
	}
	
	@GetMapping("/qna_ok")
	public String qna_ok() {
		return "qna_ok";
	}
	
	@GetMapping("/list1")
	public String list1() {
		return "list1";
	}
	
	@GetMapping("/list2")
	public String list2() {
		return "list2";
	}
	
	@GetMapping("/wishList")
	public String wishList() {
		return "wishList";
	}
	
	@GetMapping("/account")
	public String account() {
		return "account";
	}
	
	@GetMapping("/LionPay")
	public String LionPay() {
		return "LionPay";
	}
	
	@GetMapping("/myPage")
	public String myPage() {
		return "myPage";
	}
}
