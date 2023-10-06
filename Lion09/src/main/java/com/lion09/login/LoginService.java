package com.lion09.login;

import org.springframework.stereotype.Service;

import com.lion09.member.Member;
import com.lion09.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	private final MemberRepository memberRepository;

	public Member login(String userId, String userPwd) {
		Member member = memberRepository.findOne(userId);
		
		if(!member.getUserPwd().equals(userPwd) || member == null) {
			return null;
		}
		return member;
	}
}
