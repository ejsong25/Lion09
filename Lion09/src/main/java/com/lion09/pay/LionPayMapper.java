package com.lion09.pay;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lion09.pay.LionPayDTO;

@Mapper
public interface LionPayMapper {
	
	public void insertLionPay(LionPayDTO dto) throws Exception;
	
	public void insertRecharge(@Param("num") Integer num, @Param("userId") String userId) throws Exception;
	
	public void updateRechargeAmt(LionPayDTO dto) throws Exception;
	
	public void updateBalData(LionPayDTO dto) throws Exception;
	
	public void updateAccData(LionPayDTO dto) throws Exception;
	
	public void updatePwdData(LionPayDTO dto) throws Exception;
	
	public void resetAccData(LionPayDTO dto) throws Exception;
	
	public LionPayDTO getReadData(@Param("userId") String userId) throws Exception;
	
	public void insertData(@Param("dto") ListDTO listDto,@Param("userId") String userId) throws Exception;
	
	public List<ListDTO> getListData(@Param("userId") String userId) throws Exception;
	
	public List<ListDTO> getLists(@Param("start")Integer start,@Param("end")Integer end, @Param("userId") String userId) throws Exception;
	
	public Integer getDataCount(@Param("userId") String userId) throws Exception;
	
	public Integer maxNum(@Param("userId") String userId) throws Exception;
	
	List<String> getBankList();
	
}
