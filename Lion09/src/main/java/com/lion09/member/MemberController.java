package com.lion09.member;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;
import com.lion09.board.PostService;
import com.lion09.pay.LionPayService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@Autowired
	@Qualifier("lionPayServiceImpl")
	private LionPayService lionPayService;
	
	@Autowired
	@Qualifier("postServiceImpl")
	private PostService postService;
	
	//회원가입
	@GetMapping("/signup")
	public String createMemberForm(@ModelAttribute(name = "memberForm") MemberForm form) { //데이터 들고 view
		return "signup_form";
	}

	@PostMapping("/signup") //넘어온 데이터 검증
	public String signup(@Valid MemberForm form, BindingResult result) throws Exception {
		if(result.hasErrors()) {
			return "signup_form";
		}
		
		//아이디 중복 체크
		if(memberService.getUser(form.getUserId()) != null) {
			result.addError(new FieldError("form", "userId", "아이디 중복입니다."));
			return "signup_form";
		}
		
		//두 개 패스워드 불일치 검증
		if(!form.getUserPwd1().equals(form.getUserPwd2())) {
			result.addError(new FieldError("form", "userPwd2", "2개 패스워드 값이 일치하지 않습니다."));
			return "signup_form";
		}
		
		try {
			Member member = new Member();
			member.setUserId(form.getUserId());
			member.setUserPwd(form.getUserPwd1());
			member.setUserName(form.getUserName());
			member.setEnergy(40);
			member.setMyRange(3);
			member.setEmail(form.getEmail());
			member.setNickName(form.getNickName());
			member.setProfileImgName("lion.png");
			
			memberService.join(member);
			
//		} catch (DataIntegrityViolationException e) {
//			e.printStackTrace();
//			result.addError(new ObjectError("form", "이미 등록된 사용자입니다"));
//			return "signup_form";
			
		}catch (Exception e) {
			e.printStackTrace();
			result.rejectValue("signupFailed", e.getMessage());
			return "signup_form";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/delMem")
	public String delMem(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,
			RedirectAttributes redirectAttributes, Model model) throws Exception {
		String userId = sessionInfo.getUserId();

		//라이온페이 계좌 삭제
		if(lionPayService.getReadData(userId).getBalance() > 0) {
			//잔액이 남아 있을 경우 탈퇴 불가
			redirectAttributes.addFlashAttribute("err", "잔액이 남아있습니다");
			return "redirect:/LionPay";
		}
		if(postService.cannotRemovePosts(userId)>0) {
			//모집중인 게시글에 참여자가 있는 경우 탈퇴 불가
			redirectAttributes.addFlashAttribute("err", "[탈퇴 불가] 모집 중인 게시글이 존재합니다.");
			return "redirect:/myList";
		}
		//참여 중인 게시글이 있을 경우 탈퇴 불가
		
		//계좌 삭제
		lionPayService.deleteLionPay(userId);
		//라이온페이 이용내역 삭제
		lionPayService.deleteLionPayLists(userId);
		//게시글 삭제
		postService.deleteAllPosts(userId);
		//회원 탈퇴
		memberService.delUser(userId);
		//채팅내역 삭제
		
		redirectAttributes.addFlashAttribute("err", "탈퇴 처리 완료되었습니다.");
		return "redirect:/logout";
	}
}
