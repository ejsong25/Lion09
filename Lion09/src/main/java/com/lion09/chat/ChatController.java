package com.lion09.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;
import com.lion09.member.Member;
import com.lion09.member.MemberService;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    // 아래에서 사용되는 convertAndSend 를 사용하기 위해서 서언
    // convertAndSend 는 객체를 인자로 넘겨주면 자동으로 Message 객체로 변환 후 도착지로 전송한다.
    private final SimpMessageSendingOperations template;

    private final MsgChatService msgChatService;
    private final ChatService chatService;
    private final MemberService memberService;

    // MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
    // 이때 클라이언트에서는 /pub/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다.
    // 처리가 완료되면 /sub/chat/room/roomId 로 메시지가 전송된다.
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) throws Exception {
    	
    	Member findMember = memberService.getUserByNickName(chat.getNickName());
        chat.setUserId(findMember.getUserId());
        
        if(msgChatService.getUser(chat.getRoomId(),chat.getUserId()) == null) {
        	
        	// 채팅방 유저+1
        	chatService.plusUserCnt(chat.getRoomId());
        	chat.setMessage(chat.getNickName() + " 님이 입장하였습니다.");
        	chat.setType(MessageType.ENTER);
        	// 채팅방에 유저 추가 및 UserUUID 반환
        	String userUUID = msgChatService.addMsg(chat.getRoomId(),chat.getUserId(),
        			chat.getNickName(), MessageType.ENTER, chat.getMessage());
        }
        
        // 반환 결과를 socket session 에 userUUID 로 저장
        headerAccessor.getSessionAttributes().put("userUUID", chat.getUserId());
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat) throws Exception {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());
        Member findMember = memberService.getUserByNickName(chat.getNickName());
        
        msgChatService.addMsg(chat.getRoomId(), findMember.getUserId(), chat.getNickName(), MessageType.TALK, chat.getMessage());
        
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

    }

    //

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/chat/userlist")
    @ResponseBody
    public ArrayList<String> userList(String roomId) throws Exception {

        return msgChatService.getUserList(roomId);
    }

}