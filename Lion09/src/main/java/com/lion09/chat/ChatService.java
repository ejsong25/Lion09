package com.lion09.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class ChatService {

	private final ChatRoomMapper chatRoomMapper;
    private final MsgChatService msgChatService;
    
    // 채팅방 삭제에 따른 채팅방의 사진 삭제를 위한 fileService 선언
    private final FileService fileService;

    // 전체 채팅방 조회
    public List<ChatRoomDTO> findAllRoom(String userId) throws Exception{
        // 채팅방 생성 순서를 최근순으로 반환
        List<ChatRoomDTO> chatRooms = chatRoomMapper.findAllRoom(userId);
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    public ChatRoomDTO findRoomByPostId(int postId) throws Exception{
        return chatRoomMapper.findRoomByPostId(postId);
    }
    
    // roomID 기준으로 채팅방 찾기
    public ChatRoomDTO findRoomById(String roomId) throws Exception{
    	return chatRoomMapper.findRoomById(roomId);
    }

    // roomName 로 채팅방 만들기
    public void createChatRoom(ChatRoomDTO dto) throws Exception{
    	int maxNum = chatRoomMapper.maxNum(dto.getUserId())+1;
    	dto.setNum(maxNum);
//    	System.out.println(dto.getNum());
        // 채팅방 타입에 따라서 사용되는 Service 구분
        if(dto.getChatType() == ChatType.MSG){
            chatRoomMapper.createChatRoom(dto); //저장
        }
    }

    // 채팅방 인원+1
    public void plusUserCnt(int postId) throws Exception{
        //log.info("cnt {}",chatRoomMapper.plusUserCnt(roomId));
    	chatRoomMapper.plusUserCnt(postId);
    }

    // 채팅방 인원-1
    public void minusUserCnt(int postId) throws Exception{
    	chatRoomMapper.minusUserCnt(postId);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(int postId) throws Exception{
    	return chatRoomMapper.chkRoomUserCnt(postId);

    }
    
    // 채팅방 삭제
    public void delChatRoom(int postId) throws Exception{
    	try {
            // 채팅방 타입에 따라서 단순히 채팅방만 삭제할지 업로드된 파일도 삭제할지 결정

    		chatRoomMapper.delChatRoom(postId);
            if (chatRoomMapper.findRoomByPostId(postId).getChatType() == ChatType.MSG) { // MSG 채팅방은 사진도 추가 삭제
                // 채팅방 안에 있는 파일 삭제
                fileService.deleteFileDir(postId);
            }
            log.info("삭제 완료 roomId : {}", postId);

        } catch (Exception e) { // 만약에 예외 발생시 확인하기 위해서 try catch
            System.out.println(e.getMessage());
        }
    }
}