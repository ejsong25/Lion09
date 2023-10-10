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
	public void updateBalData(LionPayDTO dto) throws Exception {
		
		lionPayMapper.updateBalData(dto);
	}
	
	@Override
	public void updateAccData(LionPayDTO dto) throws Exception {
		
		lionPayMapper.updateAccData(dto);
	}
	
	@Override
	public void updatePwdData(String payPwd) throws Exception {
		
		lionPayMapper.updatePwdData(payPwd);
	}

	@Override
	public void resetAccData(LionPayDTO dto) throws Exception {
		
		lionPayMapper.resetAccData(dto);
		
	}
	
	@Override
	public LionPayDTO getReadData() throws Exception {
		
		return lionPayMapper.getReadData();
		
	}
	
	@Override
    public List<String> getBankList() {
		
        List<String> bankList = Arrays.asList("국민은행", "기업은행", "농협은행", "신한은행", "우리은행", "하나은행", "새마을금고", "케이뱅크", "토스뱅크", "우체국");
        return bankList;
    }

}
