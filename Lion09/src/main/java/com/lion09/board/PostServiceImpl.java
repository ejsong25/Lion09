package com.lion09.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService{

	
	@Autowired
	private PostMapper postMapper; //의존성 주입
	
	//좋아요 메퍼
	@Autowired
	private PostLikeMapper postlikeMapper;

	
	@Override
	public int maxPostId() throws Exception {
		return postMapper.maxPostId();
	}
	
	@Override
	public void insertData(Post dto) throws Exception {
		postMapper.insertData(dto);
		
	}
	
	@Override
	public List<Post> getLists(int start,int end) throws Exception {
		return postMapper.getLists(start,end);
	}
	
	@Override
	public int getDataCount() throws Exception {
		return postMapper.getDataCount();
	}
	

	public void updateHitCount(int postId) throws Exception {	
		postMapper.updateHitCount(postId);
	}

	@Override
	public Post getReadData(int postId) throws Exception {
		return postMapper.getReadData(postId);
	}

	@Override
	public void insertPostlike(PostLikeDTO likedto) throws Exception {
		postlikeMapper.insertPostlike(likedto);
	}

	@Override
	public PostLikeDTO getPostlike() throws Exception {
		return postlikeMapper.getPostlike();
	}

	@Override
	public void deletePostlike(PostLikeDTO likedto) throws Exception {
		postlikeMapper.deletePostlike(likedto);
	}

	@Override
	public int findPostlikeState() throws Exception {
		return postlikeMapper.findPostlikeState();
	}


	
	
	

}
