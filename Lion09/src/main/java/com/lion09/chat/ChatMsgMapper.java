package com.lion09.chat;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatMsgMapper {

	public void insertUser(ChatDTO dto) throws Exception;
	
	public Integer maxNum() throws Exception;
	
	public ArrayList<String> getUserList(int postId) throws Exception;
	
	public String getUser(@Param("postId") int postId,@Param("userId") String userId) throws Exception;
	
	public void remove(@Param("postId") int postId, @Param("userId") String userId) throws Exception;
	
	public List<ChatDTO> getMsg(@Param("postId")int post, @Param("userId")String userId) throws Exception;

	public void delMsg(int postId) throws Exception;
}
