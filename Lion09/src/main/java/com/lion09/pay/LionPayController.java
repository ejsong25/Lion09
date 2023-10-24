package com.lion09.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;
import com.lion09.board.Post;
import com.lion09.board.PostService;
import com.lion09.member.Member;
import com.lion09.mypage.MyPageService;
import com.lion09.order.Order;
import com.lion09.order.OrderStatus;


@RestController
public class LionPayController {
	
	@Autowired
	PayUtil PayUtil;
	
	@Autowired
	@Qualifier("lionPayServiceImpl")
	private LionPayService lionPayService;
	
	@Autowired
	@Qualifier("postServiceImpl")
	private PostService postService;
	
	@Autowired
	@Qualifier("myPageServiceImpl")
	private MyPageService mypageService;
	
	@GetMapping(value = "/LionPay")
	public ModelAndView index(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception{
		
		List<String> bankList = lionPayService.getBankList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("LionPay");
		
		String userId = sessionInfo.getUserId();
		LionPayDTO dto = lionPayService.getReadData(userId);
		List<ListDTO> listDto = lionPayService.getListData(userId);
		
		mav.addObject("dto",dto);
		mav.addObject("bankList",bankList);
		mav.addObject("lists",listDto);
		
		return mav;
	}
	
	@GetMapping(value = "/LionPayList")
	public ModelAndView LionPayList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,
			@Param("start")Integer start,@Param("end")Integer end,
			@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,HttpServletRequest request) throws Exception{
		
		String userId = sessionInfo.getUserId();
		
		int currentPage = 1;
		int numPerPage = 5;
		
		start = (pageNum - 1) * numPerPage + 1;
		end = pageNum * numPerPage;
		List<ListDTO> listDto = lionPayService.getLists(start, end, userId);
				
		ModelAndView mav = new ModelAndView();
		
		int dataCount = lionPayService.getDataCount(userId)-1;
		int totalPage = PayUtil.getPageCount(numPerPage, dataCount);

		if(currentPage > totalPage)
			currentPage = totalPage;

		String listUrl = "/LionPayList";
		
		String pageIndexList = PayUtil.pageIndexList(pageNum, totalPage, listUrl);
		
		LionPayDTO dto = lionPayService.getReadData(userId);
		
		mav.setViewName("LionPayList");
		mav.addObject("dto",dto);
		mav.addObject("lists",listDto);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("dataCount", dataCount);
		
		return mav;
	}
	
	@PostMapping(value = "/payCharge.action")
	@ResponseBody
	public Map<String, Object> payCharge_ok(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,
			LionPayDTO dto, ListDTO listDto, HttpServletRequest request) throws Exception{
	    Map<String, Object> response = new HashMap<>();

	    int rechargeAmount = Integer.parseInt(request.getParameter("rechargeAmount"));
	    String userId = sessionInfo.getUserId();
	    
	    //현재 값 - balance, rechargeAmount
	    LionPayDTO dto1 = lionPayService.getReadData(userId);
	    int currentBalance;
	    
	    if(dto1.getBalance() == null) {
	    	currentBalance = 0;
	    }else {
	    	currentBalance = dto1.getBalance();
	    }
	    int afterBalance = currentBalance + rechargeAmount;

	    //100만원 이하일 때만 충전 가능
	    if (afterBalance <= 1000000) {
	    	//입출금 내역
	    	listDto.setNum(lionPayService.maxNum(userId) + 1);
	    	listDto.setUserId(userId);
	    	listDto.setRechargeAmount(rechargeAmount);
	    	
	    	dto1.setUserId(userId);
	    	dto1.setBalance(afterBalance);
	    	dto1.setRechargeAmount(rechargeAmount);
	    	
	        lionPayService.updateBalData(dto1);
	        lionPayService.updateRechargeAmt(dto1);
	        lionPayService.insertData(listDto,userId); // 충전 기록 저장
	        
	        response.put("listDto", listDto);
	        response.put("canCharge", true); // 충전 가능
	    } else {
	        response.put("canCharge", false); // 최대 보유금액 초과로 충전 불가
	    }
	    
	    response.put("dto1", dto1);
	    response.put("listDto", listDto);

	    return response;
	}

	
	@RequestMapping(value = "/updateAcc_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView updateAcc_ok(LionPayDTO dto,@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,
			HttpServletRequest request) throws Exception{
		
		dto.setUserId(sessionInfo.getUserId());
		
		lionPayService.updateAccData(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		mav.addObject("dto",dto);
		
		return mav;
	}
	
	@RequestMapping(value = "/resetAcc_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView resetAccData(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,
			HttpServletRequest request) throws Exception{
		
		LionPayDTO dto = lionPayService.getReadData(sessionInfo.getUserId());
		dto.setUserId(sessionInfo.getUserId());
		lionPayService.resetAccData(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		mav.addObject("dto",dto);
		
		return mav;
		
	}
	
	@GetMapping(value = "/updatePwdData.action")
	public ModelAndView updatePwdData(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {
		String userId = sessionInfo.getUserId();
	    LionPayDTO dto = lionPayService.getReadData(userId);

	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("LionPay");
	    mav.addObject("dto", dto);

	    return mav;
	}

	
	@PostMapping(value = "/updatePwdData_ok.action")
	public ModelAndView updatePwdData_ok(LionPayDTO dto,@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo, HttpServletRequest request) throws Exception{
		
		dto.setUserId(sessionInfo.getUserId());
		lionPayService.updatePwdData(dto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/LionPay");
		
		return mav;
	}
	
	@GetMapping("/payMoney")
	public ModelAndView payMoney(ListDTO listDto, Order Odto, Post dto,Member member,
			@SessionAttribute(name = SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,
			HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String userId = sessionInfo.getUserId();
		int postId = dto.getPostId();
		int id = postService.maxId();
		int price = Integer.parseInt(request.getParameter("price"));
		
		member = mypageService.selectData(sessionInfo.getUserId());
	    dto = postService.getReadData(postId);
	    String status = postService.getReadStatus(postId);
	    
	    System.out.println(postId);
	    System.out.println(dto);
		
		LionPayDTO payDto = lionPayService.getReadData(userId);
		
		if(payDto.getBalance() < price) {
//			mav.addObject("balanceError", true);
//			mav.addObject("errorMessage", "페이머니 충전 후 결제해주세요.");
			mav.setViewName("redirect:/LionPay");
			
			return mav;
		}
		

		
		//참여하기
		Odto.setMember(member); //userId
		Odto.setPost(dto); //postId
		Odto.setOrderPrice(price); //orderPrice
		Odto.setId((long) id + 1); //orderId 
		Odto.setTitle(dto.getTitle());

		  OrderStatus orderStatus = null; // 초기화
		    
		    
		    if (status != null) {
		        try {
		            orderStatus = OrderStatus.valueOf(status);	   
		        } catch (IllegalArgumentException e) {
		            // OrderStatus 열거형(enum)에 해당 상수가 없는 경우 처리 (예: 로깅)
		            e.printStackTrace();
		        }
		    }
		    
		    Odto.setStatus(orderStatus);

		postService.insertOrder1(Odto);
		postService.updateOrder(postId);
		
		int newBalance = payDto.getBalance() - price;
		LionPayDTO newDto = new LionPayDTO();
		newDto.setUserId(userId);
		newDto.setBalance(newBalance);
		lionPayService.updateBalData(newDto);
		
		listDto.setNum(lionPayService.maxNum(userId) + 1);
    	listDto.setUserId(userId);
    	listDto.setUsage(price);
    	listDto.setAccountName(lionPayService.getReadData(userId).getAccountName());
		lionPayService.insertUsage(listDto, userId);
		
		
		mav.setViewName("redirect:/detail?postId=" + postId);
		
		mav.addObject("lists",listDto);
		
		return mav;
		
	}
	
}
