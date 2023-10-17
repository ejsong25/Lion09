package com.lion09.pay;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lion09.member.Member;

import lombok.Data;

@Table(name = "recharge")
@Entity
@Data
public class ListDTO {
	
	@Column(nullable = true)
	private String accountName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private Member member;
	
	@Id
	private String userId;
	
	@Column(nullable = true)
	private Integer rechargeAmount;
	
	@Column(nullable = true)
	private Integer categoryId;
	
	@Column(nullable = true)
	private Timestamp date;
}
