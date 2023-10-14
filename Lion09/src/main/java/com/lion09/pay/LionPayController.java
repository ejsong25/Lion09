package com.lion09.pay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;


@RestController
public class LionPayController {
	
	@Autowired
	@Qualifier("lionPayServiceImpl")
	private LionPayService lionPayService;
	
	@GetMapping(value = "/LionPay")
	public ModelAndView index(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception{
		
		List<String> bankList = lionPayService.getBankList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("LionPay");
		
		LionPayDTO dto = lionPayService.getReadData(sessionInfo.getUserId());
		
		mav.addObject("dto",dto);
		mav.addObject("bankList",bankList);
		
		return mav;
	}
	
	@PostMapping(value = "/payCharge.action")
	public ModelAndView payCharge_ok(LionPayDTO dto, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception{

		lionPayService.updateBalData(dto, sessionInfo.getUserId());
	    
	    ModelAndView mav = new ModelAndView("redirect:/LionPay");
	    
	    return mav;
	}

	
	@GetMapping(value = "/updateAcc.action")
	public ModelAndView updateAcc(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {
		
	    LionPayDTO dto = lionPayService.getReadData(sessionInfo.getUserId());

	    if (dto == null) {
	        ModelAndView mav = new ModelAndView("redirect:/LionPay");
	        return mav;
	    }

	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("LionPay");
	    mav.addObject("dto", dto);

	    return mav;
	}

	
	@PostMapping(value = "/updateAcc_ok.action")
	public ModelAndView updateAcc_ok(LionPayDTO dto, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception{
		
		lionPayService.updateAccData(dto, sessionInfo.getUserId());
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		
		return mav;
	}
	
	@GetMapping(value = "/resetAcc.action")
	public ModelAndView resetAccData(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception{
		
		LionPayDTO dto = lionPayService.getReadData(sessionInfo.getUserId());
		
		if(dto==null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LionPay");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("LionPay");
		
		mav.addObject("dto",dto);
		
		return mav;
		
	}
	
	@PostMapping(value = "/resetAcc_ok.action")
	public ModelAndView resetAccData_(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception{
		
		lionPayService.resetAccData(sessionInfo.getUserId());
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		
		return mav;
	}
	
	@GetMapping(value = "/updatePwdData.action")
	public ModelAndView updatePwdData(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {
		
	    LionPayDTO dto = lionPayService.getReadData(sessionInfo.getUserId());

	    if (dto == null) {
	        ModelAndView mav = new ModelAndView("redirect:/LionPay");
	        return mav;
	    }

	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("LionPay");
	    mav.addObject("dto", dto);

	    return mav;
	}

	
	@PostMapping(value = "/updatePwdData_ok.action")
	public ModelAndView updatePwdData_ok(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo, HttpServletRequest request) throws Exception{
		
		String payPwd = request.getParameter("payPwd");
		lionPayService.updatePwdData(payPwd, sessionInfo.getUserId());
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		
		return mav;
	}
	
}
