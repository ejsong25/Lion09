package com.lion09.pay;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
	
	@Column(nullable = true)
	private String userId;
	
	@Id
	private Integer num;
	
	@Column(nullable = true)
	private Integer rechargeAmount;
	
	@Column(nullable = true)
	private Integer categoryId;
	
	@Column(nullable = true)
	private Timestamp date;
}
