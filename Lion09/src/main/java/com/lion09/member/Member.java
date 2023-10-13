package com.lion09.member;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
//도메인
public class Member {

	@Id
	private String userId;
	
	private String userName;
	private String nickName;
	private String userPwd;
	private String email;
	
	@Embedded
	private Address address;
	
	private String energy;
	private int userLike;
	
	
	private String profileImg;
}
