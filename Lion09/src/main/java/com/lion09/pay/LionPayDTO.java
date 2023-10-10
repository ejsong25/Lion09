package com.lion09.pay;

import lombok.Data;

@Data
public class LionPayDTO {
	
	private String accountNum;
	private String accountName;
	private String payPwd;
	private int balance;
	private String userId;
	private int rechargeAmount;
}
