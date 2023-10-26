package com.lion09.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lion09.member.Member;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private MyPageMapper mypageMapper;

	@Override
	public Member selectData(String userId) throws Exception {
		return mypageMapper.selectData(userId);
	}

	@Override
	public void updateData(Member dto) throws Exception {
		mypageMapper.updateData(dto);
	}
	
	@Override
	public int selectNickName(String nickName) throws Exception {
		return mypageMapper.selectNickName(nickName);
	}

	@Override
	public void updateAddress(Member dto) throws Exception {
		mypageMapper.updateAddress(dto);
	}

	@Override
	public void imgUpdate(Member dto) throws Exception {
		mypageMapper.imgUpdate(dto);
	}

	@Override
	public void imgDefault(String userId) throws Exception {
		mypageMapper.imgDefault(userId);
	}

	@Override
	public List<String> findLocationsNearby(Member dto) throws Exception {
		return mypageMapper.findLocationsNearby(dto);
	}

	@Override
	public void updateRange(Member dto) throws Exception {
		mypageMapper.updateRange(dto);
	}
	
	@Override
	public void updatePwd(Member dto) throws Exception {
		mypageMapper.updatePwd(dto);
	}

	@Override
	public void updateEnergy(String userId) throws Exception {
		mypageMapper.updateEnergy(userId);
	}

	@Override
	public void updateEnergy2(String userId) throws Exception {
		mypageMapper.updateEnergy2(userId);
	}

}
