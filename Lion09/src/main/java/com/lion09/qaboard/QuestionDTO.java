package com.lion09.qaboard;

import lombok.Data;
import oracle.sql.DATE;

@Data
public class QuestionDTO {


	private int num;
	private String userId;
	private String pwd;
	private String subject;
	private String content;
	private String created;
	private int hitCount;
	
	
}
