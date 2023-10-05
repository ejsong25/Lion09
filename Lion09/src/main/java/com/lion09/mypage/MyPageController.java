package com.lion09.mypage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyPageController {
	
	@Autowired
	@Qualifier("myPageServiceImpl")
	private MyPageService mypageService;
	
	
	@GetMapping(value = "/myPage")
	public ModelAndView myPage() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("myPage");
		
		MyPageDTO dto = mypageService.selectData();
		
		mav.addObject("dto",dto);
		
		return mav;
	}
	
//	@RequestMapping(value = "/updated_ok.action", 
//			method = {RequestMethod.GET,RequestMethod.POST})
	@PostMapping(value = "/mypage_update.action")
	public ModelAndView mypage_update(MyPageDTO dto) throws Exception {
		
		mypageService.updateData(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/myPage");
		
		return mav;
		
	}
	

}
