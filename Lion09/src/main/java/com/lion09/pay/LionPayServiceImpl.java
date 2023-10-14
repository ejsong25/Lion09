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
	public void resetAccData(String userId) throws Exception {
		
		lionPayMapper.resetAccData(userId);
		
	}
	
	@Override
	public LionPayDTO getReadData(String userId) throws Exception {
		
		return lionPayMapper.getReadData(userId);
		
	}
	
	@Override
    public List<String> getBankList() {
		
        List<String> bankList = Arrays.asList("국민은행", "기업은행", "농협은행", "신한은행", "우리은행", "하나은행", "새마을금고", "케이뱅크", "토스뱅크", "우체국");
        return bankList;
    }

}
