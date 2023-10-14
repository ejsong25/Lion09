package com.lion09.pay;

import java.util.List;

public interface LionPayService {

	public void updateBalData(LionPayDTO dto, String userId) throws Exception;
	
	public void updateAccData(LionPayDTO dto, String userId) throws Exception;
	
	public void updatePwdData(String payPwd, String userId) throws Exception;
	
	public void resetAccData(String userId) throws Exception;
	
	public LionPayDTO getReadData(String userId) throws Exception;
	
	List<String> getBankList();
}
