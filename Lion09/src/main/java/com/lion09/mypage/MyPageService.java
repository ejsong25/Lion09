package com.lion09.mypage;

public interface MyPageService {

	public MyPageDTO selectData() throws Exception;
	
	public void updateData(MyPageDTO dto) throws Exception;
	
}
