package com.lion09.user;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

	@Id
	private String userId;
	private String userName;
	private String nickName;
	private String email;
	private String userPwd;
	private String address;
	private String grade;
	private int userLike;
	private String profileImg;
}
