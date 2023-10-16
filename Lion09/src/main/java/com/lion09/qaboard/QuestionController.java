package com.lion09.qaboard;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;



@Controller
public class QuestionController {
	

	@Autowired
	@Qualifier("questionServiceImpl")
	private QuestionService questionService;
	
	@Autowired
	MyUtil myutil;
	

	@GetMapping(value = "/qna")
	public ModelAndView created() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("qna");
		
		return mav;
		
	}
	
	//글 작성하기
	@PostMapping(value = "/qna") 
	public ModelAndView created_ok(QuestionDTO dto, @SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo)
		throws Exception {
		
		int maxNum = questionService.maxNum();
	
		dto.setNum(maxNum + 1);
		
		questionService.insertData(dto, sessionInfo.getUserId());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/qna_ok");
		
		return mav;
			
	}
	


	//고객센터 글 리스트
	@GetMapping(value = "/qna_list") 
	public ModelAndView qna_list(@Param("start")Integer start,@Param("end")Integer end, QuestionDTO dto,
	    @RequestParam(name = "pageNum", defaultValue = "1") int pageNum) throws Exception {
    
	    int currentPage = 1;
	    	    
	    int numPerPage = 5; // 페이지당 보여줄 항목 수d

	    // 현재 페이지 번호와 페이지당 항목 수를 기반으로 시작 인덱스와 끝 인덱스를 계산
	    start = (pageNum - 1) * numPerPage + 1;
	    end = pageNum * numPerPage;

	    List<QuestionDTO> lists = questionService.getLists(start,end);

	    ModelAndView mav = new ModelAndView();
	    
	    
	    // 전체 데이터의 개수를 가져옵니다.
	    int dataCount = questionService.getDataCount();
	    
	    // 총 페이지 수를 계산합니다.
	    int totalPage = myutil.getPageCount(numPerPage, dataCount);
	    
		if(currentPage>totalPage) {
			
			currentPage = totalPage;				
		}

	    // 페이지 인덱스를 생성합니다.
	    String listUrl = "/qna_list";
	    
	    String pageIndexList = myutil.pageIndexList(pageNum, totalPage, listUrl);
	    
	    String detailUrl = "/qna_detail?pageNum=" + totalPage;
	    mav.setViewName("qna_list"); // JSP

	    mav.addObject("lists", lists);
	    mav.addObject("dataCount", dataCount);
	    mav.addObject("pageIndexList", pageIndexList);
	    mav.addObject("detailUrl", detailUrl);

	    return mav;
	}

	//글 작성하기
	@GetMapping(value = "/qna_ok") 
	public ModelAndView qna_ok(HttpServletRequest request)
		throws Exception {	
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("qna_ok");
		
		return mav;
			
	}
	
	
	


	//글 상세보기
	@GetMapping("/qna_detail")
	public ModelAndView qna_detail(HttpServletRequest request) throws Exception {
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		questionService.updateHitCount(num);
		
		QuestionDTO dto = questionService.getReadData(num);
		
		if(dto==null) {
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/qna_list?pageNum=" + pageNum);
			
			return mav;
		}

		String param = "pageNum=" + pageNum;
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto",dto);
		mav.addObject("params",param);
		mav.addObject("pageNum",pageNum);
		
		mav.setViewName("qna_detail");
		
		return mav;

	}
	
	
	
	//글 수정하기
	@GetMapping("/qna_update")
	public ModelAndView qna_update(HttpServletRequest request) throws Exception {
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		QuestionDTO dto = questionService.getReadData(num);
		
		String param = "pageNum=" + pageNum;
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("qna_update");
		mav.addObject("dto", dto);
		mav.addObject("pageNum", pageNum);
		mav.addObject("params", param);
		
		return mav;
		
	}
	
	
	
	
	
	
	
	
	@PostMapping(value = "/qna_update_ok")
	public ModelAndView qna_update_ok(QuestionDTO dto,HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		String pageNum = request.getParameter("pageNum");
		

		questionService.updateData(dto);
		
		String param = "pageNum=" + pageNum;
		
		
		mav.setViewName("redirect:/qna_list?" + param);
		
		return mav;
		
	}
	
	
	
	//글 삭제
	@GetMapping("/qna_delete")
	public ModelAndView qna_delete(HttpServletRequest request) throws Exception {
		
	String pageNum = request.getParameter("pageNum");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		questionService.deleteData(num);
		
		String param = "pageNum=" + pageNum;
	
	ModelAndView mav = new ModelAndView();
		
	mav.setViewName("redirect:/qna_list?" + param);
		
		return mav;		
	
		
	}


}
