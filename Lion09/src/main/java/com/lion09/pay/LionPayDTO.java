package com.lion09.pay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lion09.member.Member;

import lombok.Data;

@Table(name = "lionPay")
@Entity
@Data
public class LionPayDTO {
	
	@Column(nullable = true)
	private String accountNum;
	
	@Column(nullable = true)
	private String accountName;
	
	@Column(nullable = true)
	private String payPwd;
	
	@Column(nullable = true)
	private Integer balance;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private Member member;
	
	@Id
	private String userId;
	
	@Column(nullable = true)
	private Integer rechargeAmount;
	
}
