package com.lion09.board;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.lion09.member.Member;

import lombok.Data;

@Entity
@Data
public class PostLikeDTO {

	private int postId;

	private String userId;
	
	@Id
	private LocalDateTime likeDate;
	
	private int likeState;
	
}




