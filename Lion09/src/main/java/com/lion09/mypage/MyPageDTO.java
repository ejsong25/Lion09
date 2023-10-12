package com.lion09.mypage;

import lombok.Data;

@Data
public class MyPageDTO {

	private String userId;
	private String nickName;
	private int energy;
	private String myAddress;
	private String myLatitude;
	private String myLongitude;
	
	//프로필 사진
	private String profileImgName;
	
	//주소 반경
	private int findLocationsNearby;
	private int myRange;
	
}
