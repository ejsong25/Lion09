package com.lion09.mypage;

import org.springframework.web.multipart.MultipartFile;

public interface MyPageService {

	public MyPageDTO selectData() throws Exception;
	
	public void updateData(MyPageDTO dto) throws Exception;

	public void imgUpdate(MyPageDTO dto) throws Exception;
	
}
