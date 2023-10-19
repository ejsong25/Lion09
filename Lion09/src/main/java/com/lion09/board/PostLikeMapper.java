package com.lion09.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostLikeMapper {

	public void insertPostlike(PostLikeDTO likedto) throws Exception;
	
	public PostLikeDTO getPostlike() throws Exception;
	
	public void deletePostlike(PostLikeDTO likedto) throws Exception;
	
	public int findPostlikeState(PostLikeDTO likedto) throws Exception;
	
	public void updateLike(int postId) throws Exception;

	public void deleteLike(int postId) throws Exception;
	
	public List<Post> likeList(@Param("start") Integer start,@Param("end") Integer end,@Param("userId") String userId) throws Exception;
	
	public int getLikeCount(String userId) throws Exception;
	
}
