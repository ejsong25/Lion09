package com.lion09.board;

import java.util.List;
import com.lion09.board.Post;

public interface PostService {
	
	public void insertData(Post dto) throws Exception;

	public int maxPostId() throws Exception;
	
	public int getDataCount() throws Exception;
	
	public List<Post> getLists(int start,int end) throws Exception;
	
	public void updateHitCount(int postId) throws Exception;

	public Post getReadData(int postId) throws Exception;
	
	
	//좋아요 기능
	public void insertPostlike(PostLikeDTO likedto) throws Exception;
	
	public PostLikeDTO getPostlike() throws Exception;
	
	public void deletePostlike(PostLikeDTO likedto) throws Exception;
	
	public int findPostlikeState() throws Exception;

}
