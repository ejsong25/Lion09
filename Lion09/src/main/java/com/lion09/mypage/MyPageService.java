package com.lion09.mypage;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface MyPageService {

	public MyPageDTO selectData() throws Exception;
	
	public void updateData(MyPageDTO dto) throws Exception;

	public void imgUpdate(MyPageDTO dto) throws Exception;

	public void imgDefault(MyPageDTO dto) throws Exception;
	
	public List<MyPageDTO> findLocationsNearby(MyPageDTO dto) throws Exception;

	public void updateRange(MyPageDTO dto) throws Exception;
	
}
