package com.lion09.board;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lion09.order.Order;

public interface PostService {
	
	public void insertData(Post dto) throws Exception;

	public int maxPostId() throws Exception;
	
	public int getDataCount(String searchKey,String searchValue) throws Exception;
	
	public int mygetDataCount(String userId) throws Exception;
	
	public List<Post> getLists(int start,int end, String searchKey,String searchValue) throws Exception;

	public List<Post> mygetLists(int start,int end,String userId) throws Exception;
 
	public void updateHitCount(int postId) throws Exception;

	public Post getReadData(int postId) throws Exception;
	
	public void updateData(Post dto) throws Exception;
	
	public void deleteData(int postId) throws Exception;
	
	public void imgUpdate(Post dto) throws Exception;
	
	public void imgDefault(Post dto) throws Exception;
	
	public String getReadOrder1(@Param("userId") String userId, @Param("postId") int postId) throws Exception;
	
	public int getReadOrder2(@Param("userId") String userId, @Param("postId") int postId) throws Exception;
	
	public Order getOrderList(@Param("userId") String userId, @Param("postId") int postId) throws Exception;

	
	//좋아요 기능
	public void insertPostlike(PostLikeDTO likedto) throws Exception;
	
	public PostLikeDTO getPostlike() throws Exception;
	
	public void deletePostlike(PostLikeDTO likedto) throws Exception;
	
	public int findPostlikeState(PostLikeDTO likedto) throws Exception;
	
	public void updateLike(int postId) throws Exception;

	public void deleteLike(int postId) throws Exception;
	
	//관심목록
	public List<Post> likeList(int start,int end,String userId) throws Exception;
	
	public int getLikeCount(String userId) throws Exception;
	
	//마감임박
	public List<Post> deadlineProduct() throws Exception;
	
	//인기글
	public List<Post> hitProduct() throws Exception;
	
	//참여하기
	public void insertOrder1(Order Odto) throws Exception;	
	
	public void updateOrder(int postId) throws Exception;

	public void deleteOrder1(Order Odto) throws Exception;
	
	public void cancelOrder(int postId,String userId) throws Exception;

	
	public void deleteOrder2(int postId) throws Exception;
	
	public List<Order> OrderList(@Param("start") Integer start,@Param("end") Integer end,@Param("userId") String userId) throws Exception;
	
	public String findOrderState(Order Odto) throws Exception;
	
	public String getReadStatus(int postId) throws Exception;
	
	public int maxId() throws Exception;
	
	public int findOrderCount(Order Odto) throws Exception;
	
	public List<Order> orderHistory(@Param("userId") String userId,int start,int end) throws Exception;
	
	public int orderMaxId() throws Exception;
	
	public int orderDataCount(String userId) throws Exception;
	
	public String findOrderData(String userId) throws Exception;
	
	public void updateOrders(Post dto) throws Exception;
	
	public void deleteOrders(int postId) throws Exception;
	
	public String getReadType(String userId, int postId) throws Exception;

	public void updateReview(@Param("userId") String userId, @Param("postId") int postId) throws Exception;
	
	//정원마감
	public void updateStatus(Post dto) throws Exception;
	
	public void updateOderStatus(@Param("postId") int postId) throws Exception;

	
	
}