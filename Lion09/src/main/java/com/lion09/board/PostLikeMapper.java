package com.lion09.board;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {

	public void insertPostlike(PostLikeDTO likedto) throws Exception;
	
	public PostLikeDTO getPostlike() throws Exception;
	
	public void deletePostlike(PostLikeDTO likedto) throws Exception;
	
	public int findPostlikeState() throws Exception;
	
}
