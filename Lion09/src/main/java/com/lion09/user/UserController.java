package com.lion09.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String singup(MemberForm form) {
		Member member = new Member();
	}
	
	
	
}
