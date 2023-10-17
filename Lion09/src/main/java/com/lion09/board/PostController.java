package com.lion09.board;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lion09.board.PostUtil;
import com.lion09.member.Member;
import com.lion09.mypage.MyPageService;
import com.lion09.SessionConst;
import com.lion09.SessionInfo;
import com.lion09.board.Post;
import com.lion09.board.PostService;

@Controller
public class PostController {
	
	@Autowired
	@Qualifier("postServiceImpl")
	private PostService postService;
	
	@Autowired
	PostUtil postUtil;
	
	@RequestMapping(value = "index")
	public ModelAndView index() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		
		return mav;
		
	}
	
	@Autowired
	@Qualifier("myPageServiceImpl")
	private MyPageService mypageService;
	
	//글쓰기 페이지 불러오기
	@GetMapping("/write.action")
	public ModelAndView write(@SessionAttribute(SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("write"); 
		
		Member dto = mypageService.selectData(sessionInfo.getUserId());
		
		mav.addObject("dto",dto);
		
		return mav;
	}
	
	@PostMapping(value = "/write_ok.action")
	public ModelAndView write_ok(@RequestPart("chooseFilename") MultipartFile cFile,HttpServletRequest request,
			@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) 
			throws Exception {
		ModelAndView mav = new ModelAndView();

		Post dto = new Post();
		//닉네임 설정
		dto.setTitle(request.getParameter("title"));
		dto.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
		dto.setProductsPrice(Integer.parseInt(request.getParameter("productsPrice")));
		dto.setRecruitment(Integer.parseInt(request.getParameter("recruitment")));	    

		String plainText = request.getParameter("contents").replaceAll("\\<.*?\\>", "");
		dto.setContents(plainText);
		
		dto.setMyAddr(request.getParameter("myAddress"));

		if (!cFile.isEmpty()) {
			// 파일을 저장할 디렉토리 경로 설정
			String fileDir = "C:\\git-lion\\Lion09\\Lion09\\Lion09\\src\\main\\resources\\static\\img\\postimg\\";

			// 업로드한 파일의 원래 파일 이름 가져오기
			String originalFilename = cFile.getOriginalFilename();

			// 파일 확장자 추출
			String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

			// 서버에 저장할 새 파일명 생성 (파일명 중복 방지를 위해 현재 시간을 이용)
			String newFilename = System.currentTimeMillis() + fileExtension;

			// 파일을 서버에 저장
			File newFile = new File(fileDir, newFilename);
			cFile.transferTo(newFile);

			// 데이터베이스에 파일 경로 저장
			String fullPath = newFilename;

			dto.setChooseFile(fullPath);
			
			// 데이터베이스에 데이터 추가
			int maxPostId = postService.maxPostId();
			dto.setPostId(maxPostId + 1);
			postService.insertData(dto);

			mav.setViewName("redirect:/list1");
			
		}else {//사진입력을 안했을 경우(무조건 postLion.jpg 입력)
			
			String newFilename = "postLion.jpg";
			
			dto.setChooseFile(newFilename);
			
			// 데이터베이스에 데이터 추가
			int maxPostId = postService.maxPostId();
			dto.setPostId(maxPostId + 1);
			postService.insertData(dto);

			mav.setViewName("redirect:/list1");
			
		}

		return mav;
	}
	
	@GetMapping("/list1")
	public ModelAndView list(
			@Param("start")Integer start,@Param("end")Integer end,
			@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
			Post dto,HttpServletRequest request) throws Exception {	
		
		int currentPage = 1;
		
		int numPerPage = 12;
		
	    start = (pageNum - 1) * numPerPage + 1;
	    end = pageNum * numPerPage;
	    
	    List<Post> lists = postService.getLists(start, end);
	    
	    ModelAndView mav = new ModelAndView();
		
	    int dataCount = postService.getDataCount();
	    
	    int totalPage = postUtil.getPageCount(numPerPage, dataCount);

	 
	    if (currentPage > totalPage) {
	        currentPage = totalPage;
	    }



	    String listUrl = "/list1";
		
	    String pageIndexList = postUtil.pageIndexList(pageNum, totalPage, listUrl);

	    String detailUrl = "/detail?pageNum=" + totalPage;

	  
		mav.setViewName("/list1"); 
		mav.addObject("lists", lists);
		mav.addObject("dataCount", dataCount);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("detailUrl", detailUrl);
		
		return mav;
		
	}
	
	
	
	@GetMapping("/detail")
	public ModelAndView detail(HttpServletRequest request) throws Exception {
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			
			currentPage = Integer.parseInt(pageNum);
						
		}
	
		
		
		postService.updateHitCount(postId);
		
		Post dto = postService.getReadData(postId);
		
		if(dto==null) {
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/list1?pageNum=" + pageNum);
			
			return mav;
		}
		
		
		
		
		String param = "pageNum=" + pageNum;
	
		
		ModelAndView mav = new ModelAndView();
		

		
		//좋아요 부분
		int likeState = postService.findPostlikeState();
		
		PostLikeDTO likedto = new PostLikeDTO();
		
		likedto.setLikeState(likeState);
		
		mav.addObject("likedto",likedto);
		
		
		
		
		mav.addObject("dto",dto);
		mav.addObject("params",param);
		mav.addObject("pageNum",pageNum);
		
		mav.setViewName("/detail");
		
		return mav;
		
	}
	
	//좋아요 관심목록에 추가
	@PostMapping(value = "/insertLike.action")
	public ModelAndView insertLike(PostLikeDTO likedto) throws Exception {
		
		postService.insertPostlike(likedto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/list1");
		
		return mav;
		
	}
	
	//관심목록에서 삭제
	@PostMapping(value = "/deleteLike.action")
	public ModelAndView delteLike(PostLikeDTO likedto) throws Exception {
		
		postService.deletePostlike(likedto);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/list1");
		
		return mav;
		
	}

	
}
