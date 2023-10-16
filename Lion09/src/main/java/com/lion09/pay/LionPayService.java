package com.lion09.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LionPayService {
	
	public void insertLionPay(LionPayDTO dto) throws Exception;
	
	public void insertRecharge(ListDTO listDto) throws Exception;

	public void updateRechargeAmt(@Param("rechargeAmount")Integer rechargeAmount, String userId) throws Exception;
	
	public void updateBalData(LionPayDTO dto, String userId) throws Exception;
	
	public void updateAccData(LionPayDTO dto, String userId) throws Exception;
	
	public void updatePwdData(String payPwd, String userId) throws Exception;
	
	public void resetAccData(LionPayDTO dto, String userId) throws Exception;
	
	public LionPayDTO getReadData(String userId) throws Exception;
	
	public int getBalance(String userId) throws Exception;
	
	public void insertData(ListDTO listDto) throws Exception;
	
	public List<ListDTO> getListData(LionPayDTO dto) throws Exception;
	
	public List<ListDTO> getLists(@Param("start")Integer start,@Param("end")Integer end) throws Exception;
	
	public int getDataCount() throws Exception;
	
	List<String> getBankList();
}
