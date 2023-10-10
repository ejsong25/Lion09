package com.lion09.pay;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lion09.pay.LionPayDTO;

@Mapper
public interface LionPayMapper {
	
	public void updateBalData(LionPayDTO dto) throws Exception;
	
	public void updateAccData(LionPayDTO dto) throws Exception;
	
	public void updatePwdData(String payPwd) throws Exception;
	
	public void resetAccData(LionPayDTO dto) throws Exception;
	
	public LionPayDTO getReadData() throws Exception;
	
}
