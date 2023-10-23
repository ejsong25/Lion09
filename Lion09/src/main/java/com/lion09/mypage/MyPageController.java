package com.lion09.mypage;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;
import com.lion09.member.Member;
import com.lion09.member.MemberService;
import com.lion09.member.MemberUpdateForm;

@Controller
public class MyPageController {

	@Autowired
	@Qualifier("myPageServiceImpl")
	private MyPageService mypageService;
	
	@Autowired
	private MemberService memberService;

	//mypage 불러오기
	@GetMapping(value = "/myPage")
	public ModelAndView myPage(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("myPage");

		Member dto = mypageService.selectData(sessionInfo.getUserId());

		mav.addObject("dto",dto);
		
		return mav;
	}
	
	
	//닉네임 업데이트
	@PostMapping(value = "/mypage_update.action")
	public ModelAndView mypage_update(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,
			Member dto,RedirectAttributes redirectAttributes) throws Exception {

		ModelAndView mav = new ModelAndView();

		dto.setUserId(sessionInfo.getUserId());
		
		int nickNameCheck = mypageService.selectNickName(dto.getNickName());
		
		if(nickNameCheck==0) {
			mypageService.updateData(dto);
			mav.setViewName("redirect:/myPage");
			redirectAttributes.addFlashAttribute("successMessage", "닉네임이 변경되었습니다");
			
			return mav;
		}else {
			mav.setViewName("redirect:/myPage");
			redirectAttributes.addFlashAttribute("errorMessage", "닉네임이 이미 존재합니다");

			return mav;
		}
		
	}
	
	//주소 업데이트
	@PostMapping(value = "/address_update.action")
	public ModelAndView address_update(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo, Member dto) throws Exception {

		dto.setUserId(sessionInfo.getUserId());
		
		System.out.println(dto.getMyAddress());
		mypageService.updateAddress(dto);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/myPage");

		return mav;

	}
	
	//기본 프로필 사진으로 변경
	@PostMapping(value = "/mypageImg_default.action")
	public ModelAndView imgDefault(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {
		
		String userId = sessionInfo.getUserId();
		
		Member dto = mypageService.selectData(sessionInfo.getUserId());
		dto.setUserId(userId);
		
		//삭제할 파일 이름 추출
		String beforeFilename = dto.getProfileImgName();

		//삭제할 파일 경로
		String delete_pate = "C:\\git-lion\\Lion09\\Lion09\\src\\main\\resources\\static\\img\\mypage\\";

		//기본 프로필 이미지가 아닐 경우 삭제
		if(beforeFilename!="lion.png") {
			String filePathToDelete = delete_pate + beforeFilename;
			File fileToDelete = new File(filePathToDelete);
			
			if (fileToDelete.exists()) {
				fileToDelete.delete();  
			} 
		}
		
		//기본 이미지 lion.png로 변경
		mypageService.imgDefault(sessionInfo.getUserId());

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/myPage");

		return mav;
		
	}

	//프로필 사진 업데이트
	@PostMapping("/mypageImg_update.action")
	public ModelAndView imageUpdated(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo, 
			@RequestParam(value = "mypageFilename", required = false)MultipartFile mFile) throws Exception {

		//프로필 사진들 모아두는 폴더
		String upload_path = "/"; 

		String userId = sessionInfo.getUserId();
		
		Member dto = mypageService.selectData(userId);
		
		//삭제할 파일 이름 추출
		dto.setUserId(userId);
		String beforeFilename = dto.getProfileImgName();
		
		//삭제할 파일 경로
		String delete_pate = "C:\\git-lion\\Lion09\\Lion09\\src\\main\\resources\\static\\img\\mypage\\";

		//기본 프로필 이미지가 아닐 경우 삭제
		if(!beforeFilename.equals("lion.png")) {
			String filePathToDelete = delete_pate + beforeFilename;
			File fileToDelete = new File(filePathToDelete);
			
			if (fileToDelete.exists()) {
				fileToDelete.delete();  
			} 
		}
		
		//바꿀 파일 이름 추출
		String originalFilename = mFile.getOriginalFilename();

		//확장자 추출
	    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
		
	    //새 파일 이름 생성 (현재 시간 대신 "111" 추가)
	    String newFilename = originalFilename.replace(fileExtension, "_" + userId + fileExtension);

		//MyPageDTO에 파일 이름 설정
		dto.setProfileImgName(newFilename);

		mypageService.imgUpdate(dto);	

		//경로에 업로드
		mFile.transferTo(new File(upload_path + newFilename));  

		ModelAndView mav = new ModelAndView();
		
		//3.5초 후에 리다이렉트할 URL 설정
        String redirectUrl = "/myPage";
        
        //RedirectView를 사용하여 리다이렉션 설정
        RedirectView redirectView = new RedirectView(redirectUrl, true);

        //3.5초 대기 후 리다이렉트
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

//        if (!mFile.isEmpty()) {
//        	// 파일이 업로드되지 않았거나 비어있는 경우
//        	mav.setViewName("redirect:/myPage"); // 에러 페이지로 리다이렉트
//        	return mav;
//        }

        mav.setView(redirectView);

		return mav;

	}
	
	//반경 업데이트
	@PostMapping(value = "/updateRange.action")
	public ModelAndView updateRange(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo, Member mdto) throws Exception {

		mdto.setUserId(sessionInfo.getUserId());
		mypageService.updateRange(mdto);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/list1");

		return mav;

	}

	@GetMapping(value = "/update")
	public ModelAndView updatePwd(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("account");

		Member dto = mypageService.selectData(sessionInfo.getUserId());

		mav.addObject("dto",dto);
		
		return mav;
	}
	
	@PostMapping(value="/updatePwd.action")
	public ModelAndView updatePwd(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo, Member dto) 
			throws Exception {
		
		dto.setUserId(sessionInfo.getUserId());
		
		mypageService.updatePwd(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/update");
		
		return mav;
	}
	
	//리뷰
	@GetMapping(value = "/review")
	public ModelAndView review(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("review");

		Member mdto = mypageService.selectData(sessionInfo.getUserId());

		mav.addObject("mdto",mdto);

		return mav;
	}
	
	
	
}