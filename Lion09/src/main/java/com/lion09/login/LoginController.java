package com.lion09.login;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;
import com.lion09.member.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			result.addError(new ObjectError("form", "아이디 또는 비밀번호가 맞지 않습니다."));
			return "login_form"; 
		}
		
		//로그인 성공 - 세션 생성
		HttpSession session = request.getSession();
		
		//정보 담기
		SessionInfo memberSession = new SessionInfo();
		memberSession.setUserId(loginMember.getUserId());
		memberSession.setNickName(loginMember.getNickName());
		
		session.setAttribute(SessionConst.LOGIN_MEMBER, memberSession);
		log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null)
			session.invalidate();
		System.out.println("logout");
		return "redirect:/";
	}
}
