package com.lion09.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lion09.SessionConst;
import com.lion09.member.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	private final LoginService loginService;
	
	@GetMapping("/login")
	public String createLoginForm(@ModelAttribute(name = "loginForm")LoginForm form) {
		return "login_form";
	}
	
	@PostMapping("/login")
	public String login(@Valid LoginForm form, BindingResult result,
			HttpServletRequest request) {
		if(result.hasErrors())
			return "login_form";
		
		Member loginMember = 
				loginService.login(form.getUserId(), form.getUserPwd());
		
		if(loginMember == null) {
			result.reject("loginFail",
					"아이디 또는 비밀번호가 맞지 않습니다.");
			return "login_form"; 
		}
		
		//로그인 성공 - 세션 생성
		HttpSession session = request.getSession();
		//정보 담기
		session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null)
			session.invalidate();
		return "redirect:/";
	}
}
