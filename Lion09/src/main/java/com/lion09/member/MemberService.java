package com.lion09.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lion09.pay.LionPayDTO;
import com.lion09.pay.LionPayService;
import com.lion09.pay.ListDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	@Autowired
	@Qualifier("lionPayServiceImpl")
	private LionPayService lionPayService;
	
	//회원가입
	@Transactional
	public String join(Member member) throws Exception {
		memberRepository.save(member);
		
		LionPayDTO dto = new LionPayDTO();
		dto.setMember(member); // Member와 연결
		dto.setUserId(member.getUserId()); // userId 설정

		lionPayService.insertLionPay(dto);
		
		int maxNum = lionPayService.maxNum(member.getUserId());
		lionPayService.insertRecharge(maxNum + 1, member.getUserId());

		return member.getUserId();
	}
	
	
	public Member getUser(String userId) throws Exception {
		return memberRepository.findOne(userId);
		
	}
	
	public Member getUserByNickName(String nickName) throws Exception {
		return memberRepository.findByNickName(nickName);
	}
}
