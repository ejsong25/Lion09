package com.lion09.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatRoomMapper {

	public List<ChatRoomDTO> findAllRoom(String userId) throws Exception;
	
	public ChatRoomDTO findRoomByPostId(@Param("postId") int postId) throws Exception;
	
	public ChatRoomDTO findRoomById(@Param("roomId") String roomId) throws Exception;
	
	public void createChatRoom(ChatRoomDTO dto) throws Exception;
	
	public boolean confirmPwd(@Param("roomId") String roomId, String roomPwd) throws Exception;
	
	public void plusUserCnt(@Param("postId") int postId) throws Exception;
	
	public void minusUserCnt(@Param("postId") int postId) throws Exception;
	
	public boolean chkRoomUserCnt(@Param("postId") int postId) throws Exception;
	
	public void delChatRoom(@Param("postId") int postId) throws Exception;
	
	public Integer maxNum(@Param("userId") String userId) throws Exception;
	
}
