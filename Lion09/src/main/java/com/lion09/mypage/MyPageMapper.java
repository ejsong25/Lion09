package com.lion09.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.lion09.member.Member;

@Mapper
public interface MyPageMapper {

	public Member selectData(String userId) throws Exception;
	
	public void updateData(Member dto) throws Exception;

	public int selectNickName(String nickName) throws Exception;

	public void updateAddress(Member dto) throws Exception;
	
	public void imgUpdate(Member dto) throws Exception;

	public void imgDefault(String userId) throws Exception;
	
	public List<String> findLocationsNearby(Member dto) throws Exception;
	
	public void updateRange(Member dto) throws Exception;

	public void updatePwd(Member dto) throws Exception;

	public void updateEnergy(String userId) throws Exception;
	
	public void updateEnergy2(String userId) throws Exception;
	
}