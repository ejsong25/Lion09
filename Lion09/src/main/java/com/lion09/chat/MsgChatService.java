package com.lion09.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RequiredArgsConstructor
@Service
public class MsgChatService {
	
	private final ChatRoomMapper chatRoomMapper;
	private final ChatMsgMapper chatMsgMapper;

    // 채팅방 삭제에 따른 채팅방의 사진 삭제를 위한 fileService 선언
    private final FileService fileService;

    public ChatRoomDTO createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt) {
        // roomName 와 roomPwd 로 chatRoom 빌드 후 return
    	ChatRoomDTO room = ChatRoomDTO.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .roomPwd(roomPwd) // 채팅방 패스워드
                .secretChk(secretChk) // 채팅방 잠금 여부
                .userCount(0) // 채팅방 참여 인원수
                .maxUserCnt(maxUserCnt) // 최대 인원수 제한
                .build();

        // msg 타입이면 ChatType.MSG
        room.setChatType(ChatType.MSG);

        return room;
    }


    // 채팅방 유저 리스트에 유저 추가
    public String addMsg(String roomId, String userId, String nickName, MessageType msgType,
    		String message) throws Exception{
    	ChatRoomDTO room = chatRoomMapper.findRoomById(roomId);

    	int maxNum = chatMsgMapper.maxNum();
    	chatMsgMapper.insertUser(maxNum+1,roomId,userId,nickName,msgType,message);
    	
        return userId;
    }

    // 채팅방 전체 userlist 조회
    public ArrayList<String> getUserList(String roomId) throws Exception{
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDTO room = chatRoomMapper.findRoomById(roomId);

        return chatMsgMapper.getUserList(roomId);
    }
    
    public String getUser(String roomId,String userId) throws Exception{

        ChatRoomDTO room = chatRoomMapper.findRoomById(roomId);

        return chatMsgMapper.getUser(roomId,userId);
        
    }

    // 채팅방 특정 유저 삭제
    public void delUser(String roomId, String userId) throws Exception{
    	ChatRoomDTO room = chatRoomMapper.findRoomById(roomId);
    	chatMsgMapper.remove(roomId, userId);
    }
    
    //메세지
    public List<ChatDTO> getMsg(String roomId) throws Exception{
    	return chatMsgMapper.getMsg(roomId);
    }
}