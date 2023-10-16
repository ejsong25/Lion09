package com.lion09.pay;

import java.util.Arrays;
import java.util.List;

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
	public void insertRecharge(ListDTO listDto) throws Exception {
		lionPayMapper.insertRecharge(listDto);
		
	}
	
	@Override
	public void updateRechargeAmt(Integer rechargeAmount, String userId) throws Exception {
		lionPayMapper.updateRechargeAmt(rechargeAmount, userId);
		
	}
	
	@Override
	public void updateBalData(LionPayDTO dto, String userId) throws Exception {
		lionPayMapper.updateBalData(dto, userId);
	}
	
	@Override
	public void updateAccData(LionPayDTO dto, String userId) throws Exception {
		lionPayMapper.updateAccData(dto, userId);
	}
	
	@Override
	public void updatePwdData(String payPwd, String userId) throws Exception {
		lionPayMapper.updatePwdData(payPwd, userId);
	}

	@Override
	public void resetAccData(LionPayDTO dto, String userId) throws Exception {
		lionPayMapper.resetAccData(dto, userId);
		
	}
	
	@Override
	public int getDataCount() throws Exception {
		return lionPayMapper.getDataCount();
	}
	
	@Override
	public LionPayDTO getReadData(String userId) throws Exception {
		return lionPayMapper.getReadData(userId);
		
	}
	
	@Override
	public int getBalance(String userId) throws Exception {
		return lionPayMapper.getBalance(userId);
	}
	
	@Override
	public void insertData(ListDTO listDto) throws Exception {
		lionPayMapper.insertData(listDto);
		
	}
	
	@Override
	public List<ListDTO> getListData(LionPayDTO dto) throws Exception {
		return lionPayMapper.getListData(dto);
	}

	@Override
	public List<ListDTO> getLists(Integer start, Integer end) throws Exception {
		return lionPayMapper.getLists(start, end);
	}
	
	@Override
    public List<String> getBankList() {
        List<String> bankList = Arrays.asList("국민은행", "기업은행", "농협은행", "신한은행", "우리은행", "하나은행", "새마을금고", "케이뱅크", "토스뱅크", "우체국");
        return bankList;
    }

}
