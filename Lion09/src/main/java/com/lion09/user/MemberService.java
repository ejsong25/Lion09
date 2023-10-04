package com.lion09.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	//회원가입
	@Transactional
	public Member join(MemberForm form) {
		Member member = new Member();
		member.setUserId(form.getUserId());
		member.setUserPwd(form.getUserPwd1());
		member.setUserName(form.getUserName());
		member.setEmail(form.getEmail());
		member.setNickName(form.getNickName());
		
		memberRepository.save(member);
		
		return member;
	}
	
}
