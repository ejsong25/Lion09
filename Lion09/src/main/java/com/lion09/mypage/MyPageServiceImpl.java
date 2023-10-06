package com.lion09.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private MyPageMapper mypageMapper;

	@Override
	public MyPageDTO selectData() throws Exception {
		return mypageMapper.selectData();
	}

	@Override
	public void updateData(MyPageDTO dto) throws Exception {
		mypageMapper.updateData(dto);
	}

	
	
}
