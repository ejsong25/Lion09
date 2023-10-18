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
	public List<Post> getLists(int start,int end, String searchKey,String searchValue) throws Exception {
		return postMapper.getLists(start,end,searchKey,searchValue);
	}
	
	@Override
	public int getDataCount(String searchKey,String searchValue) throws Exception {
		return postMapper.getDataCount(searchKey,searchValue);
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
	public void updateData(Post dto) throws Exception {
		postMapper.updateData(dto);
		
	}

	@Override
	public void deleteData(int postId) throws Exception {
		postMapper.deleteData(postId);
		
	}

	@Override
	public void imgUpdate(Post dto) throws Exception {
		postMapper.imgUpdate(dto);
		
	}

	@Override
	public void imgDefault(Post dto) throws Exception {
		postMapper.imgDefault(dto);
		
	}

	@Override
	public int findPostlikeState(PostLikeDTO likedto) throws Exception {
		return postlikeMapper.findPostlikeState(likedto);
	}



	
	
	

}
