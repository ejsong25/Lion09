package com.lion09.qaboard;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lion09.member.Member;

import lombok.Data;

@Data
@Entity
@Table(name = "qaboard")
public class QuestionDTO {

	@Id
	private int num;
	
	private String subject;
	private String content;
	private LocalDateTime created;
	private int hitCount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member member;
	
	
}
