package com.lion09.member;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class MemberUpdateForm {
	@NotEmpty
	private String email;
	@NotEmpty
	private String userPwd;
	@NotEmpty
	private String userPwd1;
}
