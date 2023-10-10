package com.lion09.pay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class LionPayController {
	
	@Autowired
	@Qualifier("lionPayServiceImpl")
	private LionPayService lionPayService;
	
	@GetMapping(value = "/LionPay")
	public ModelAndView index() throws Exception{
		
		List<String> bankList = lionPayService.getBankList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("LionPay");
		
		LionPayDTO dto = lionPayService.getReadData();
		
		mav.addObject("dto",dto);
		mav.addObject("bankList",bankList);
		
		return mav;
	}
	
	@PostMapping(value = "/payCharge.action")
	public ModelAndView payCharge_ok(LionPayDTO dto, HttpServletRequest request) throws Exception{

		lionPayService.updateBalData(dto);
	    
	    ModelAndView mav = new ModelAndView("redirect:/LionPay");
	    
	    return mav;
	}

	
	@GetMapping(value = "/updateAcc.action")
	public ModelAndView updateAcc(HttpServletRequest request) throws Exception {
		
	    LionPayDTO dto = lionPayService.getReadData();

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
	public ModelAndView updateAcc_ok(LionPayDTO dto, HttpServletRequest request) throws Exception{
		
		lionPayService.updateAccData(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		
		return mav;
	}
	
	@GetMapping(value = "/resetAcc.action")
	public ModelAndView resetAccData(HttpServletRequest request) throws Exception{
		
		LionPayDTO dto = lionPayService.getReadData();
		
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
	public ModelAndView resetAccData(LionPayDTO dto, HttpServletRequest request) throws Exception{
		
		lionPayService.resetAccData(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		
		return mav;
	}
	
	@GetMapping(value = "/updatePwdData.action")
	public ModelAndView updatePwdData(HttpServletRequest request) throws Exception {
		
	    LionPayDTO dto = lionPayService.getReadData();

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
	public ModelAndView updatePwdData_ok(HttpServletRequest request) throws Exception{
		
		String payPwd = request.getParameter("payPwd");
		lionPayService.updatePwdData(payPwd);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		
		return mav;
	}
	
}
