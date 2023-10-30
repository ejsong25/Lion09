package com.lion09.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lion09.order.Order;


@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostMapper postMapper; //의존성 주입
	
	//좋아요 메퍼
	@Autowired
	private PostLikeMapper postlikeMapper;
	
	//참여하기 메퍼
	@Autowired
	private OrderMapper orderMapper;
	
	
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
	
	@Override
	public void updateLike(int postId) throws Exception {
		postlikeMapper.updateLike(postId);
	}

	@Override
	public void deleteLike(int postId) throws Exception {
		postlikeMapper.deleteLike(postId);
	}

	@Override
	public List<Post> mygetLists(int start, int end,String userId) throws Exception {
		return postMapper.mygetLists(start,end,userId);
	}

	@Override
	public int mygetDataCount(String userId) throws Exception {
		return postMapper.mygetDataCount(userId);
	}

	@Override
	public List<Post> likeList(int start,int end,String userId) throws Exception {
		return postlikeMapper.likeList(start,end,userId);
	}

	@Override
	public int getLikeCount(String userId) throws Exception {
		return postlikeMapper.getLikeCount(userId);
	}

	@Override
	public List<Post> deadlineProduct() throws Exception {
		return postMapper.deadlineProduct();
	}

	@Override
	public List<Post> hitProduct() throws Exception {
		return postMapper.hitProduct();
	}

	@Override
	public void insertOrder1(Order Odto) throws Exception {
		orderMapper.insertOrder1(Odto);
		
	}

	@Override
	public void updateOrder(int postId) throws Exception {
		orderMapper.updateOrder(postId);
		
	}

	@Override
	public List<Order> OrderList(Integer start, Integer end, String userId) throws Exception {
		return orderMapper.OrderList(start, end, userId);
	}

	@Override
	public String findOrderState(Order Odto) throws Exception {
		return orderMapper.findOrderState(Odto);
	}

	@Override
	public String getReadStatus(int postId) throws Exception {
		return postMapper.getReadStatus(postId);
	}

	@Override
	public int maxId() throws Exception {
		return orderMapper.maxId();
	}

	@Override
	public void deleteOrder1(Order Odto) throws Exception {
		orderMapper.deleteOrder1(Odto);
		
	}

	@Override
	public void deleteOrder2(int postId) throws Exception {
		orderMapper.deleteOrder2(postId);
		
	}

	@Override
	public int findOrderCount(Order Odto) throws Exception {
		return orderMapper.findOrderCount(Odto);
	}

	@Override
	public List<Order> orderHistory(String userId,int start,int end) throws Exception {
		return orderMapper.orderHistory(start, end, userId);
	}

	@Override
	public int orderMaxId() throws Exception {
		return orderMapper.orderMaxId();
	}

	@Override
	public int orderDataCount(String userId) throws Exception {
		return orderMapper.orderDataCount(userId);
	}

	@Override
	public String findOrderData(String userId) throws Exception {
		return orderMapper.findOrderData(userId);
	}

	@Override
	public void updateOrders(Post dto) throws Exception {
		orderMapper.updateOrders(dto);
	}

	@Override
	public void deleteOrders(int postId) throws Exception {
		orderMapper.deleteOrders(postId);
	}

	@Override
	public String getReadType(String userId, int postId) throws Exception {
		return orderMapper.getReadType(userId,postId);
	}

	@Override
	public void updateReview(String userId, int postId) throws Exception {
		orderMapper.updateReview(userId, postId);
	}
	
	@Override
	public int cannotRemovePosts(String userId) throws Exception {
		return postMapper.cannotRemovePosts(userId);
	}

	@Override
	public void deleteAllPosts(String userId) throws Exception {
		postMapper.deleteAllPosts(userId);
	}

	@Override
	public void cancelOrder(int postId, String userId) throws Exception {
		orderMapper.cancelOrder(userId, postId);
		
	}

	@Override 
	public String getReadOrder1(String userId, int postId) throws Exception {
		return orderMapper.getReadOrder1(userId, postId);
	}

	@Override
	public int getReadOrder2(String userId, int postId) throws Exception {
		return orderMapper.getReadOrder2(userId, postId);
	}

	@Override
	public Order getOrderList(String userId, int postId) throws Exception {
		return orderMapper.getOrderList(userId, postId);
	}

	@Override
	public void updateStatus(Post dto) throws Exception {
		postMapper.updateStatus(dto);
				
	}

	@Override
	public void updateOderStatus(int postId) throws Exception {
		orderMapper.updateOderStatus(postId);
		
	}

	@Override
	public void updateOderStatus1(int postId) throws Exception {
		orderMapper.updateOderStatus1(postId);
		
	}



}