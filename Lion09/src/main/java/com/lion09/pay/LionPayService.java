package com.lion09.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LionPayService {
	
	public void insertLionPay(LionPayDTO dto) throws Exception;
	
	public void insertRecharge(String userId) throws Exception;

	public void updateRechargeAmt(LionPayDTO dto) throws Exception;
	
	public void updateBalData(LionPayDTO dto) throws Exception;
	
	public void updateAccData(LionPayDTO dto) throws Exception;
	
	public void updatePwdData(LionPayDTO dto) throws Exception;
	
	public void resetAccData(LionPayDTO dto) throws Exception;
	
	public LionPayDTO getReadData(String userId) throws Exception;
	
	public void insertData(ListDTO listDto, String userId) throws Exception;
	
	public List<ListDTO> getListData(String userId) throws Exception;
	
	public List<ListDTO> getLists(@Param("start")Integer start,@Param("end")Integer end, String userId) throws Exception;
	
	public Integer getDataCount() throws Exception;
	
	public Integer maxNum(String userId) throws Exception;
	
	List<String> getBankList();
}
