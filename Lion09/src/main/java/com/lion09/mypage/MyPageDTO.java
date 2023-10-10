package com.lion09.mypage;

import lombok.Data;

@Data
public class MyPageDTO {

	private String userId;
	private String nickName;
	private int energy;
	private String address;
	private String latitude;
	private String longitude;
	
	//프로필 사진
	private String profileImgName;
	
}
