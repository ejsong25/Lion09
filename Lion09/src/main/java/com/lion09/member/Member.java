package com.lion09.member;

import javax.persistence.Column;
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
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "nick_name")
	private String nickName;
	
	@Column(name = "user_pwd")
	private String userPwd;
	
	private String email;
	private String address;
	private String grade;
	private int userLike;
	private String profileImg;
}
