package com.lion09.pay;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LionPayServiceImpl implements LionPayService {

	@Autowired
	private LionPayMapper lionPayMapper;
	
	@Override
	public void insertLionPay(LionPayDTO dto) throws Exception {
		lionPayMapper.insertLionPay(dto);
	}
	
	@Override
	public void insertRecharge(Integer num, String userId) throws Exception {
		lionPayMapper.insertRecharge(num, userId);
	}
	
	@Override
	public void updateRechargeAmt(LionPayDTO dto) throws Exception {
		lionPayMapper.updateRechargeAmt(dto);
	}
	
	@Override
	public void updateBalData(LionPayDTO dto) throws Exception {
		lionPayMapper.updateBalData(dto);
	}
	
	@Override
	public void updateAccData(LionPayDTO dto) throws Exception {
		lionPayMapper.updateAccData(dto);
	}
	
	@Override
	public void updatePwdData(LionPayDTO dto) throws Exception {
		lionPayMapper.updatePwdData(dto);
	}

	@Override
	public void resetAccData(LionPayDTO dto) throws Exception {
		lionPayMapper.resetAccData(dto);
	}
	
	@Override
	public Integer getDataCount(String userId) throws Exception {
		return lionPayMapper.getDataCount(userId);
	}
	
	@Override
	public LionPayDTO getReadData(String userId) throws Exception {
		return lionPayMapper.getReadData(userId);
	}
	
	@Override
	public void insertData(ListDTO listDto,String userId) throws Exception {
		lionPayMapper.insertData(listDto,userId);
	}
	
	@Override
	public List<ListDTO> getListData(String userId) throws Exception {
		return lionPayMapper.getListData(userId);
	}

	@Override
	public List<ListDTO> getLists(Integer start, Integer end, String userId) throws Exception {
		return lionPayMapper.getLists(start, end, userId);
	}
	
	@Override
	public Integer maxNum(String userId) throws Exception {
		return lionPayMapper.maxNum(userId);
	}
	
	@Override
	public void insertUsage(ListDTO listDto, String userId) throws Exception {
		lionPayMapper.insertUsage(listDto,userId);
	}
	
	@Override
	public Integer getRefundData(String userId, int postId) throws Exception {
		return lionPayMapper.getRefundData(userId, postId);
	}
	
	@Override
    public List<String> getBankList() {
        List<String> bankList = Arrays.asList("국민은행", "기업은행", "농협은행", "신한은행", "우리은행", "하나은행", "새마을금고", "케이뱅크", "토스뱅크", "우체국");
        return bankList;
    }

	@Override
	public void deleteLionPay(String userId) throws Exception {
		lionPayMapper.deleteLionPay(userId);
	}

	@Override
	public void deleteLionPayLists(String userId) throws Exception {
		lionPayMapper.deleteLionPayLists(userId);
	}


}
