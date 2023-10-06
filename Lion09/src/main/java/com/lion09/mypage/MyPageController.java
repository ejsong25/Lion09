package com.lion09.mypage;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MyPageController {

	@Autowired
	@Qualifier("myPageServiceImpl")
	private MyPageService mypageService;

	//mypage 불러오기
	@GetMapping(value = "/myPage")
	public ModelAndView myPage() throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("myPage");

		MyPageDTO dto = mypageService.selectData();

		mav.addObject("dto",dto);
		
		return mav;
	}
	
	//닉네임 업데이트
	@PostMapping(value = "/mypage_update.action")
	public ModelAndView mypage_update(MyPageDTO dto) throws Exception {

		mypageService.updateData(dto);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/myPage");

		return mav;

	}
	
	//기본 프로필 사진으로 변경
	@PostMapping(value = "/mypageImg_default.action")
	public ModelAndView imgDefault() throws Exception {
		
		MyPageDTO dto = mypageService.selectData();
		
		//삭제할 파일 이름 추출
		String beforeFilename = dto.getProfileImgName();

		//삭제할 파일 경로
		String delete_pate = "C:\\git-lion\\Lion09\\Lion09\\Lion09\\src\\main\\resources\\static\\img\\mypage\\";

		//기본 프로필 이미지가 아닐 경우 삭제
		if(beforeFilename!="lion.png") {
			String filePathToDelete = delete_pate + beforeFilename;
			File fileToDelete = new File(filePathToDelete);
			
			if (fileToDelete.exists()) {
				fileToDelete.delete();  
			} 
		}
		
		//기본 이미지 lion.png로 변경
		mypageService.imgDefault(dto);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/myPage");

		return mav;
		
	}

	//프로필 사진 업데이트
	@PostMapping("/mypageImg_update.action")
	public ModelAndView imageUpdated(
			@RequestParam("mypageFilename") MultipartFile mFile) throws Exception {

		//프로필 사진들 모아두는 폴더
		String upload_path = "/"; 

		MyPageDTO dto = mypageService.selectData();
		
		//삭제할 파일 이름 추출
		String beforeFilename = dto.getProfileImgName();

		//삭제할 파일 경로
		String delete_pate = "C:\\git-lion\\Lion09\\Lion09\\Lion09\\src\\main\\resources\\static\\img\\mypage\\";

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
	    String newFilename = originalFilename.replace(fileExtension, "_111" + fileExtension);

		//MyPageDTO에 파일 이름 설정
		dto.setProfileImgName(newFilename);

		mypageService.imgUpdate(dto);	

		//경로에 업로드
		mFile.transferTo(new File(upload_path + newFilename));  

		ModelAndView mav = new ModelAndView();
		
		//2.5초 후에 리다이렉트할 URL 설정
        String redirectUrl = "/myPage";

        //RedirectView를 사용하여 리다이렉션 설정
        RedirectView redirectView = new RedirectView(redirectUrl, true);

        //3.5초 대기 후 리다이렉트
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        mav.setView(redirectView);

		return mav;

	}


}
