package com.lion09.mypage;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {

	public MyPageDTO selectData() throws Exception;
	
	public void updateData(MyPageDTO dto) throws Exception;
	
}
