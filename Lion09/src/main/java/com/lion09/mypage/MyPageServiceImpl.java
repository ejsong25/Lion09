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
	public void updateData(@Param("dto")Member dto, @Param("userId")String userId) throws Exception {
		mypageMapper.updateData(dto, userId);
	}

	@Override
	public void imgUpdate(@Param("p_name") String profileImgName, @Param("userId") String userId) throws Exception {
		mypageMapper.imgUpdate(profileImgName, userId);
	}

	@Override
	public void imgDefault(String userId) throws Exception {
		mypageMapper.imgDefault(userId);
	}

	@Override
	public List<Member> findLocationsNearby(String userId) throws Exception {
		return mypageMapper.findLocationsNearby(userId);
	}

	@Override
	public void updateRange(int myRange, String userId) throws Exception {
		mypageMapper.updateRange(myRange, userId);
	}

	
	
}
