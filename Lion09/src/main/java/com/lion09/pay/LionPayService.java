package com.lion09.pay;

import java.util.List;

public interface LionPayService {

	public void updateBalData(LionPayDTO dto) throws Exception;
	
	public void updateAccData(LionPayDTO dto) throws Exception;
	
	public void updatePwdData(String payPwd) throws Exception;
	
	public void resetAccData(LionPayDTO dto) throws Exception;
	
	public LionPayDTO getReadData() throws Exception;
	
	List<String> getBankList();
	
}
