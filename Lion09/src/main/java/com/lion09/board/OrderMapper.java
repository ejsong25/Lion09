package com.lion09.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lion09.order.Order;

@Mapper
public interface OrderMapper {
	
	public void insertOrder1(Order Odto) throws Exception;	
			
	public void updateOrder(int postId) throws Exception;	

	public void deleteOrder1(Order Odto) throws Exception;
	
	public void deleteOrder2(int postId) throws Exception;
	
	public List<Order> OrderList(@Param("start") Integer start,@Param("end") Integer end,@Param("userId") String userId) throws Exception;
	
	public String findOrderState(Order Odto) throws Exception;
	
	public int maxId() throws Exception;
	
	public int findOrderCount(Order Odto) throws Exception;
	
	public List<Order> orderHistory(@Param("start") Integer start,@Param("end") Integer end, @Param("userId") String userId) throws Exception;
	
	public int orderMaxId() throws Exception;
	
	public int orderDataCount(String userId) throws Exception;
	
	public String findOrderData(String userId) throws Exception;
	
}
