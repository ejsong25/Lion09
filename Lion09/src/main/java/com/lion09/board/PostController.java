package com.lion09.board;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.lion09.board.PostUtil;
import com.lion09.member.Member;
import com.lion09.member.MemberForm;
import com.lion09.mypage.MyPageService;
import com.lion09.order.Order;
import com.lion09.order.OrderStatus;
import com.lion09.pay.LionPayService;
import com.lion09.SessionInfo;
import com.lion09.board.Post;
import com.lion09.board.PostService;
import com.lion09.SessionConst;

@Controller
public class PostController {

	@Autowired
	@Qualifier("postServiceImpl")
	private PostService postService;
	
	@Autowired
	@Qualifier("lionPayServiceImpl")
	private LionPayService lionPayService;

	@Autowired
	PostUtil postUtil;

	@RequestMapping(value = "/" , method = {RequestMethod.GET})
	public ModelAndView index(Post dto) throws Exception {

		ModelAndView mav = new ModelAndView();
	
		List<Post> lists = postService.deadlineProduct();
			
		List<Post> lists1 = postService.hitProduct();
		
		String deadLineUrl = "/detail?postId=";	
		
		
		mav.addObject("lists", lists);
		mav.addObject("lists1", lists1);
		mav.addObject("deadLineUrl",deadLineUrl);
		mav.addObject("dto",dto);

		mav.setViewName("index");

		return mav;

	}

	@Autowired
	@Qualifier("myPageServiceImpl")
	private MyPageService mypageService;

	//글쓰기 페이지 불러오기
	@GetMapping("/write.action")
	public ModelAndView write(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		ModelAndView mav = new ModelAndView();

		Member dto = mypageService.selectData(sessionInfo.getUserId());

		mav.addObject("dto",dto);

		if(dto.getMyAddress()==null) {
			mav.setViewName("/addressInsert");
		}else {
			mav.setViewName("/write"); 
		}

		return mav;
	}


	//@PostMapping(value="/write_ok.action")
	@RequestMapping(value="/write_ok.action", method = {RequestMethod.POST})
	public ModelAndView write_ok(@RequestPart("chooseFileName") MultipartFile cFile,
			@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,Post dto,HttpServletRequest request) throws Exception {


		ModelAndView mav = new ModelAndView();

		Member Mdto = mypageService.selectData(sessionInfo.getUserId());
		String nickName = Mdto.getNickName();
		dto.setNickName(nickName);
		dto.setMyAddr(Mdto.getMyAddress());
		dto.setUserId(sessionInfo.getUserId());


		int maxPostId = postService.maxPostId();
		dto.setPostId(maxPostId + 1);

		String plainText = request.getParameter("contents").replaceAll("\\<.*?\\>", "");
		dto.setContents(plainText);



		if (!cFile.isEmpty()) {
			// 파일 업로드를 위한 경로 설정
			String uploadDir = "C:\\Users\\septw\\git\\gitLion\\Lion09\\src\\main\\resources\\static\\img\\postimg\\";

			// 업로드한 파일의 원래 파일 이름 가져오기
			String originalFilename = cFile.getOriginalFilename();

			//확장자 추출
			String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

			// 현재 시간을 나타내는 날짜 포맷 생성
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

			// 현재 날짜 및 시간을 가져와 포맷팅
			Date now = new Date(System.currentTimeMillis());
			String timestamp = dateFormat.format(now);


			//새 파일 이름 생성 
			String newFilename = originalFilename.replace(fileExtension, "_" +dto.getPostId() + timestamp +fileExtension);

			// 파일을 서버에 저장
			File newFile = new File(uploadDir, newFilename);
			cFile.transferTo(newFile);

			// 데이터베이스에 파일 경로 저장
			//String fullPath = uploadDir + newFilename; // 웹 경로로 저장

			dto.setChooseFile(newFilename);

			// 데이터베이스에 데이터 추가

			postService.insertData(dto);

			mav.setViewName("redirect:/list1");

		} else {
			// 사진 입력을 안했을 경우(무조건 lion.jpg 입력)
			String newFilename = "lion.png";
			dto.setChooseFile(newFilename);

			postService.insertData(dto);

			mav.setViewName("redirect:/list1");
		}


		mav.setViewName("redirect:/list1");
		return mav;
	}

	@RequestMapping(value = "/list1", 
			method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView list1(@Param("start") Integer start, @Param("end") Integer end,
			@RequestParam(name = "pageNum", defaultValue = "1") String pageNum,
			@Param("searchKey") String searchKey,@Param("searchValue") String searchValue,
			@Param("categoryId") String categoryId,Post dto,
			HttpServletRequest request,@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		ModelAndView mav = new ModelAndView();
		Member mdto = mypageService.selectData(sessionInfo.getUserId());
		mdto.setUserId(sessionInfo.getUserId());

		if(mdto.getMyAddress()==null) {
			mav.setViewName("/addressInsert");
			return mav;
		}
		
		
		int currentPage = 1;

		if(pageNum!=null) {

			currentPage = Integer.parseInt(pageNum);

		}

		searchKey = request.getParameter("searchKey");
		searchValue = request.getParameter("searchValue");

		if (searchValue == null || searchValue.isEmpty()) {
			searchKey = "title";
			searchValue = "";
		} else {
			if (request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");

			}

		}

		int numPerPage = 12;

		start = (currentPage - 1) * numPerPage + 1;
		end = currentPage * numPerPage;


		String param = "";
		if (searchValue != null && !searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");

		}


		int dataCount = postService.getDataCount(searchKey, searchValue);

		int totalPage = postUtil.getPageCount(numPerPage, dataCount);

		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		String listUrl = "/list1";

		if(!param.equals("")) {
			listUrl = listUrl + "?" + param;
		}

		if (!param.equals("")) {
			listUrl = listUrl + "?" + param;
		}

		String pageIndexList = postUtil.pageIndexList(currentPage, totalPage, listUrl);

		String detailUrl = "/detail?pageNum=" + totalPage;

		if (!param.equals("")) {
			detailUrl = detailUrl + "&" + param;
		}


		//반경조회해서 가져오기
		List<Member> findList = mypageService.findLocationsNearby(mdto);
		List<Post> allLists = postService.getLists(start, end, searchKey, searchValue);
		List<Post> lists = new ArrayList<>();

		int myCategoryId = (categoryId != null) ? Integer.parseInt(categoryId) : 0;
		
		if(myCategoryId == 0) {
			for (Member member : findList) {
				String myAddress = member.getMyAddress();
				
				for (Post post : allLists) {
					String myAddr = post.getMyAddr();
					if (myAddr.equals(myAddress)) {
						lists.add(post);
					} 
				}
				
			}
		}else {
			for (Member member : findList) {
				String myAddress = member.getMyAddress();
				for (Post post : allLists) {
					String myAddr = post.getMyAddr();
					
					if (myAddr.equals(myAddress)&&myCategoryId==post.getCategoryId()) {
						lists.add(post);
					} 
				}
				
			}
		}
		
		mav.addObject("mdto",mdto);
		mav.addObject("findList",findList);

		mav.setViewName("/list1"); 
		mav.addObject("lists", lists);
		mav.addObject("dataCount", dataCount);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("detailUrl", detailUrl);


		return mav;

	}
	
	@GetMapping("/detail")
	public ModelAndView detail(HttpServletRequest request, @SessionAttribute(SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo) throws Exception {

	    int postId = Integer.parseInt(request.getParameter("postId"));
	    String pageNum = request.getParameter("pageNum");
	    
	    
	    Member mdto = mypageService.selectData(sessionInfo.getUserId());

    
	    String userId = mdto.getUserId();

	    Member member = new Member(); // Member 엔티티의 인스턴스 생성
	    Post post = new Post(); // Post 엔티티의 인스턴스 생성

	    // 참여하기
	    Order Odto = new Order();
	    
	    member = mypageService.selectData(userId);
	    post = postService.getReadData(postId);
	    String status = postService.getReadStatus(postId);
	   
	    Odto.setMember(member);
	    Odto.setPost(post);

	    int count  = postService.findOrderCount(Odto);
	   
	    System.out.println(count);
	    
	    Odto.setCount(count);
	    
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
	    
	    
	    PostLikeDTO likedto = new PostLikeDTO();
	    likedto.setUserId(userId);
	    likedto.setPostId(postId);

	    int likeState = postService.findPostlikeState(likedto);
	    likedto.setLikeState(likeState);

	    int currentPage = 1;

	    if (pageNum != null) {
	        currentPage = Integer.parseInt(pageNum);
	    }

	    postService.updateHitCount(postId);

	    Post dto = postService.getReadData(postId);

	    if (dto == null) {
	        ModelAndView mav = new ModelAndView();
	        mav.setViewName("redirect:/list1?pageNum=" + pageNum);
	        return mav;
	    }

	    String param = "pageNum=" + pageNum;
	    
	    // 라이언페이
	 	String payPwd = lionPayService.getReadData(userId).getPayPwd();

	    ModelAndView mav = new ModelAndView();

	    // 좋아요 부분
	    mav.addObject("likedto", likedto);

	    // 참여하기 부분
	    mav.addObject("Odto", Odto);
	    mav.addObject("mdto", mdto);
	    mav.addObject("dto", dto);
	    mav.addObject("params", param);
	    mav.addObject("pageNum", pageNum);
	    mav.addObject("payPwd",payPwd);

	    mav.setViewName("/detail");

	    System.out.println(mav);
	    
	    return mav;
	}

	//좋아요 관심목록에 추가
	@PostMapping(value = "/insertLike.action")
	public ModelAndView insertLike(PostLikeDTO likedto,
			@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,Post dto) throws Exception {

		int postId = dto.getPostId();

		//좋아요
		likedto.setUserId(sessionInfo.getUserId());
		likedto.setPostId(postId);

		int likeState = postService.findPostlikeState(likedto);
		likedto.setLikeState(likeState);

		postService.insertPostlike(likedto);
		postService.updateLike(postId);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/detail?postId=" + postId);

		return mav;

	}

	//관심목록에서 삭제
	@PostMapping(value = "/deleteLike.action")
	public ModelAndView delteLike(PostLikeDTO likedto,Post dto,
			@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		int postId = dto.getPostId();

		likedto.setUserId(sessionInfo.getUserId());
		likedto.setPostId(dto.getPostId());

		postService.deletePostlike(likedto);
		postService.deleteLike(postId);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/detail?postId=" + postId);

		return mav;

	}

	//관심목록 불러오기
	@GetMapping(value = "/wishList")
	public ModelAndView likeList(@Param("start") Integer start,@Param("end") Integer end,@Param("userId") String userId,
			@RequestParam(name = "pageNum", defaultValue = "1") String pageNum, Post dto,
			@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		ModelAndView mav = new ModelAndView();

		int currentPage = 1;

		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		int numPerPage = 12;

		start = (currentPage - 1) * numPerPage + 1;
		end = currentPage * numPerPage;

		userId = sessionInfo.getUserId();

		List<Post> likeList = postService.likeList(start,end,userId);
		
		int dataCount = postService.getLikeCount(userId);

		int totalPage = postUtil.getPageCount(numPerPage, dataCount);
		
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		
		String listUrl = "/wishList";

		String pageIndexList = postUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		String detailUrl = "/detail?pageNum=" + totalPage;

		mav.setViewName("/wishList");
		mav.addObject("userId", userId);
		mav.addObject("likeList", likeList);
		mav.addObject("dataCount", dataCount);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("detailUrl", detailUrl);
//		mav.addObject("currentPage", currentPage); 
//		mav.addObject("totalPage", totalPage); 
		    
		return mav;

	}


	//게시글 수정하기
	@GetMapping("/writeUpdated")
	public ModelAndView writeUpdated(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();


		Member Mdto = mypageService.selectData(sessionInfo.getUserId());

		String userId = Mdto.getUserId();

		int postId = Integer.parseInt(request.getParameter("postId"));
		String pageNum = request.getParameter("pageNum");




		Post dto =  postService.getReadData(postId);

		String param = "pageNum=" + pageNum;

		mav.addObject("dto", dto);
		mav.addObject("params",param);
		mav.addObject("pageNum", pageNum);
		mav.setViewName("writeUpdated");


		return mav;
	}



	//게시글 수정하기
	@Transactional
	@RequestMapping(value="/writeUpdated_ok", method = {RequestMethod.POST})
	public ModelAndView writeUpdated_ok(@RequestParam(name = "postId", defaultValue = "1") int postId,HttpServletRequest request) throws Exception {


		ModelAndView mav = new ModelAndView();

		Post dto = new Post();

		dto.setPostId(postId);

		dto.setTitle(request.getParameter("title"));

		dto.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));

		dto.setProductsPrice(Integer.parseInt(request.getParameter("productsPrice")));

		dto.setRecruitment(Integer.parseInt(request.getParameter("recruitment")));


		String plainText = request.getParameter("contents").replaceAll("\\<.*?\\>", "");
		dto.setContents(plainText);
		dto.setMyAddr(request.getParameter("myAddr")); // "myAddress"에서 "myAddr"로 수정



		postService.updateData(dto);


//		String pageNum = request.getParameter("pageNum");
//		String param = "pageNum=" + pageNum;

		mav.setViewName("redirect:/list1?");

		return mav;



	}


	//게시글 이미지 업데이트
	@RequestMapping(value="/chooseFile_update.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView imageUpdated(@RequestParam(name = "postId", defaultValue = "1") int postId,
			HttpServletRequest request,
			@RequestPart("chooseFile") MultipartFile cFile) throws Exception {

		ModelAndView mav = new ModelAndView();

		//int postId = Integer.parseInt(request.getParameter("postId"));
		String postIdString = request.getParameter("postId");
		if (postIdString == null || postIdString.isEmpty()) {
			mav.addObject("error", "postId가 비어있습니다."); // 오류 메시지 설정
			mav.setViewName("error_page"); // 오류 페이지로 리다이렉트 또는 뷰 이름 설정
			return mav;
		}

		try {
			postId = Integer.parseInt(postIdString);


			//이미지 사진들 모아두는 폴더
			String upload_path = "C:\\Users\\septw\\git\\gitLion\\Lion09\\src\\main\\resources\\static\\img\\postimg\\"; 

			Post dto = postService.getReadData(postId);

			//삭제할 파일 이름 추출
			String beforeFilename = dto.getChooseFile();

			//삭제할 파일 경로
			String delete_pate = "C:\\Users\\septw\\git\\gitLion\\Lion09\\src\\main\\resources\\static\\img\\postimg\\";

			//게시글 이미지가 기존의 이미지가 아닐 경우 삭제
			if(!beforeFilename.equals("lion.png")) {
				String filePathToDelete = delete_pate + beforeFilename;
				File fileToDelete = new File(filePathToDelete);

				if (fileToDelete.exists()) {
					fileToDelete.delete();  
				} 
			}


			//바꿀 파일 이름 추출
			String originalFilename = cFile.getOriginalFilename();

			//확장자 추출
			String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

			//새 파일 이름 생성 (현재 시간 대신 "111" 추가)
			String newFilename = originalFilename.replace(fileExtension, "_111" + fileExtension);

			//MyPageDTO에 파일 이름 설정
			dto.setChooseFile(newFilename);

			postService.imgUpdate(dto);	

			//경로에 업로드
			cFile.transferTo(new File(upload_path + newFilename));  

			String pageNum = request.getParameter("pageNum");
			String param = "pageNum=" + pageNum +"&" + "postId=" + postId;

			//리다이렉트할 URL 설정
			String redirectUrl = "/writeUpdated?" + param ;

			//RedirectView를 사용하여 리다이렉션 설정
			RedirectView redirectView = new RedirectView(redirectUrl, true);

			//대기 후 리다이렉트
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}


			mav.setView(redirectView);


		} catch (NumberFormatException e) {
			mav.addObject("error", "postId 파싱 중 오류가 발생했습니다."); // 오류 메시지 설정
			mav.setViewName("error_page"); // 오류 페이지로 리다이렉트 또는 뷰 이름 설정
		}
		return mav;
	}




	//이미지파일 삭제
	//@PostMapping(value = "/chooseFile_delete.action")
	@RequestMapping(value="/chooseFile_delete.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView imgDefault(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();

		int postId = Integer.parseInt(request.getParameter("postId"));

		String pageNum = request.getParameter("pageNum");
		String param = "pageNum=" + pageNum +"&" + "postId=" + postId;


		Post dto = postService.getReadData(postId);

		//삭제할 파일 이름 추출
		String beforeFilename = dto.getChooseFile();


		//삭제할 파일 경로
		String delete_pate = "C:\\Users\\septw\\git\\gitLion\\Lion09\\src\\main\\resources\\static\\img\\postimg\\";

		//기본 사진 이미지가 아닐 경우 삭제		
		if(beforeFilename.equalsIgnoreCase("lion.png")){
			mav.setViewName("redirect:/writeUpdated?" + param );
			return mav;
		}

		if(beforeFilename!="lion.png") {
			String filePathToDelete = delete_pate + beforeFilename;
			File fileToDelete = new File(filePathToDelete);

			if (fileToDelete.exists()) {
				fileToDelete.delete();  
			} 
		}
		//기본 이미지 lion.png로 변경
		postService.imgDefault(dto);

		mav.setViewName("redirect:/writeUpdated?" + param );

		return mav;

	}

	@RequestMapping(value="/deleted_ok.action", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleted_ok(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();

		Post dto = new Post();

		int postId = Integer.parseInt(request.getParameter("postId"));
		String pageNum = request.getParameter("pageNum");


		postService.deleteData(postId);

		String param = "pageNum=" + pageNum;

		// 데이터 업데이트 후 데이터 다시 읽기
		Post updatedPost = postService.getReadData(dto.getPostId());


		mav.setViewName("redirect:/list1?" + param);

		return mav;


	}



	@GetMapping("/myList")
	public ModelAndView myList(@Param("start") Integer start, @Param("end") Integer end,
			@RequestParam(name = "pageNum", defaultValue = "1") String pageNum,
			HttpServletRequest request,@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {


		//			Member Mdto = mypageService.selectData(sessionInfo.getUserId());
		//			String userId = sessionInfo.getUserId();

		Post dto = new Post();

		String userId = sessionInfo.getUserId();
		//dto.setUserId(userId);

		System.out.println(userId);

		int currentPage = 1;

		if(pageNum!=null) {

			currentPage = Integer.parseInt(pageNum);

		}


		int numPerPage = 12;

		start = (currentPage - 1) * numPerPage + 1;
		end = currentPage * numPerPage;

		List<Post> lists = postService.mygetLists(start, end,userId);

		String param = "";


		ModelAndView mav = new ModelAndView();

		int dataCount = postService.mygetDataCount(userId);

		int totalPage = postUtil.getPageCount(numPerPage, dataCount);

		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		String listUrl = "/myList";

		if(!param.equals("")) {
			listUrl = listUrl + "?" + param;
		}

		if (!param.equals("")) {
			listUrl = listUrl + "?" + param;
		}

		String pageIndexList = postUtil.pageIndexList(currentPage, totalPage, listUrl);

		String mydetailUrl = "/detail?pageNum=" + totalPage;

		if (!param.equals("")) {
			mydetailUrl = mydetailUrl + "&" + param;
		}


		mav.setViewName("/myList"); 
		mav.addObject("lists", lists);
		mav.addObject("dataCount", dataCount);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("mydetailUrl", mydetailUrl);


		return mav;

	}

	
//	//참여 목록에 추가
//	@PostMapping(value = "/insertOrder.action")
//	public ModelAndView insertOrder(Order Odto,
//			@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo,Post dto,Member member) throws Exception {
//
//		ModelAndView mav = new ModelAndView();
//		
//		int postId = dto.getPostId();
//
//		int id = postService.maxId();
//
//	    member = mypageService.selectData(sessionInfo.getUserId());
//	    dto = postService.getReadData(postId);
//	    String status = postService.getReadStatus(postId);
//
//	    //참여하기
//	    Odto.setMember(member); //userId
//	    Odto.setPost(dto); //postId
//	    Odto.setOrderPrice(dto.getProductsPrice());	//orderPrice
//		Odto.setId((long) id + 1); //orderId 
//	    
//		postService.insertOrder1(Odto);
//		postService.updateOrder(postId);
//
//	
//		mav.setViewName("redirect:/detail?postId=" + postId);
//
//		return mav;
//
//	}
//
//	
//	
//	
	//참여 목록에서 삭제
	@PostMapping(value = "/deleteOrder.action")
	public ModelAndView deleteOrder(Order Odto,Post dto,Member member,
			@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo) throws Exception {

		int postId = dto.getPostId();

		member = mypageService.selectData(sessionInfo.getUserId());
	    dto = postService.getReadData(postId);
	    String status = postService.getReadStatus(postId);

	    //참여하기
	    Odto.setMember(member); //userId
	    Odto.setPost(dto); //postId

	    postService.deleteOrder1(Odto);
	    postService.deleteOrder2(postId);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/detail?postId=" + postId);

		return mav;

	}



}
