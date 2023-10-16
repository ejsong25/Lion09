package com.lion09.member;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	//회원가입
	@GetMapping("/signup")
	public String createMemberForm(@ModelAttribute(name = "memberForm") MemberForm form) { //데이터 들고 view
		return "signup_form";
	}

	@PostMapping("/signup") //넘어온 데이터 검증
	public String signup(@Valid MemberForm form, BindingResult result) {
		if(result.hasErrors()) {
			return "signup_form";
		}
		
		//두 개 패스워드 불일치 검증
		if(!form.getUserPwd1().equals(form.getUserPwd2())) {
			result.rejectValue("password2", "passwordIncorrect",
					"2개 패스워드 값이 일치하지 않습니다.");
			return "signup_form";
		}
		
		try {
			Member member = new Member();
			member.setUserId(form.getUserId());
			member.setUserPwd(form.getUserPwd1());
			member.setUserName(form.getUserName());
			member.setEmail(form.getEmail());
			member.setNickName(form.getNickName());
			member.setProfileImgName("lion.png");
			
			memberService.join(member);
			
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			result.rejectValue("signupFailed", "이미 등록된 사용자입니다");
			return "signup_form";
			
		}catch (Exception e) {
			e.printStackTrace();
			result.rejectValue("signupFailed", e.getMessage());
			return "signup_form";
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/update")
	public String createUpdateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo, Model model) throws Exception {
		Member loginMember = memberService.getUser(sessionInfo.getUserId());
		model.addAttribute("loginMember", loginMember);
		return "account";
	}
	
	@PostMapping("/update")
	public String update(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo, @Valid MemberUpdateForm form, BindingResult result) throws Exception {
		if(result.hasErrors())
			return "account"; 
		Member member = memberService.getUser(sessionInfo.getUserId());
		member.setEmail(form.getEmail());
		member.setUserPwd(form.getUserPwd());
		return "redirect:/";
	}
	
}
