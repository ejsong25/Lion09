package com.lion09.member;

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
	public String join(Member member) {
		memberRepository.save(member);
		
		return member.getUserId();
	}
	
	
	public Member getUser(String userId) throws Exception {
		Member findMember = memberRepository.findOne(userId);
		
		if(findMember != null) {
			return findMember;
		}else {
			throw new Exception("User Not Found");
		}
		
	}
	
}
