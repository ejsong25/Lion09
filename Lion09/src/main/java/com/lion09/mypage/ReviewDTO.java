package com.lion09.mypage;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ReviewDTO {
	
	@Id
	private int num;
	
	private String userId;
	
	private int postId;
	
}
