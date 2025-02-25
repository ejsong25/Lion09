package com.lion09.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MemberForm {
	@NotEmpty(message = "사용자 아이디는 필수 항목입니다.")
	private String userId;
	
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String userPwd1;
	
	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String userPwd2;
	
	@NotEmpty(message = "이름은 필수 항목입니다.")
	private String userName;
	
	@Size(max=6, message = "닉네임은 최대 6자까지 가능합니다.")
	private String nickName;
	private String email;
}
