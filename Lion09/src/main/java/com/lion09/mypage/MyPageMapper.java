package com.lion09.mypage;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface MyPageMapper {

	public MyPageDTO selectData() throws Exception;
	
	public void updateData(MyPageDTO dto) throws Exception;
	
	public void imgUpdate(MyPageDTO dto) throws Exception;
	
}
