package com.lion09.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatRoomMapper {

	public List<ChatRoomDTO> findAllRoom() throws Exception;
	
	public ChatRoomDTO findRoomById(@Param("roomId") String roomId) throws Exception;
	
	public void createChatRoom(ChatRoomDTO dto) throws Exception;
	
	public boolean confirmPwd(@Param("roomId") String roomId, String roomPwd) throws Exception;
	
	public void plusUserCnt(@Param("roomId") String roomId) throws Exception;
	
	public void minusUserCnt(@Param("roomId") String roomId) throws Exception;
	
	public boolean chkRoomUserCnt(@Param("roomId") String roomId) throws Exception;
	
	public void delChatRoom(@Param("roomId") String roomId) throws Exception;
	
	public Integer maxNum(@Param("userId") String userId) throws Exception;
	
}
