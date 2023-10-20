package com.lion09.chat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatMsgMapper {

	public void insertUser(@Param("num") Integer num,@Param("roomId") String roomId, 
			@Param("userId") String userId, @Param("nickName") String nickName, @Param("msgType") MessageType msgType,
			@Param("message") String message) throws Exception;
	
	public Integer maxNum() throws Exception;
	
	public ArrayList<String> getUserList(@Param("roomId") String roomId) throws Exception;
	
	public String getUser(@Param("roomId") String roomId,@Param("userId") String userId) throws Exception;
	
	public void remove(@Param("roomId") String roomId, @Param("userId") String userId) throws Exception;
	
	public List<ChatDTO> getMsg(String roomId) throws Exception;
}
