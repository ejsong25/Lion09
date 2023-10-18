package com.lion09.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.lion09.member.Member;

public interface MyPageService {

	public Member selectData(String userId) throws Exception;
	
	public void updateData(Member dto) throws Exception;

	public void updateAddress(Member dto) throws Exception;

	public void imgUpdate(Member dto) throws Exception;

	public void imgDefault(String userId) throws Exception;
	
	public List<Member> findLocationsNearby(Member dto) throws Exception;

	public void updateRange(Member dto) throws Exception;

	public void updatePwd(Member dto) throws Exception;
	
}
