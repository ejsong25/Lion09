package com.lion09.member;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	private final EntityManager em;
	
	//저장
	public String save(Member member) {
		em.persist(member);
		return member.getUserId();
	}
	
	//조회
	public Member findOne(String userId) {
		return em.find(Member.class, userId);
	}
	
}
