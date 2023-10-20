package com.lion09.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lion09.SessionConst;
import com.lion09.SessionInfo;
import com.lion09.board.Post;
import com.lion09.board.PostService;
import com.lion09.member.MemberService;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatService chatService;
    private final MsgChatService msgChatService;
    private final MemberService memberService;
    private final SimpMessageSendingOperations template;
    @Autowired
    @Qualifier("postServiceImpl")
    private PostService postService;
    
    @GetMapping("/chat")
    public String goChatRoom(Model model, Principal principal) throws Exception {
        model.addAttribute("list", chatService.findAllRoom());

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
            log.debug("Username: {}", username);
        }

        log.debug("SHOW ALL ChatList {}", chatService.findAllRoom());
        return "roomlist";
    }

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public String createRoom(@SessionAttribute(SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo,
    		@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr) throws NumberFormatException, Exception {

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
    	ChatRoomDTO dto = msgChatService.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt));
        dto.setChatType(ChatType.MSG);
        dto.setUserId(sessionInfo.getUserId());
        chatService.createChatRoom(dto);


        log.info("CREATE Chat Room [{}]", dto);

        rttr.addFlashAttribute("roomName", dto);
        return "redirect:/chat";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, HttpServletRequest request, Principal principal) throws Exception {
    	
    	int postId = Integer.parseInt(request.getParameter("postId"));
    	log.info("postId {}", postId);

        if (principal != null) {
            // Principal 객체에서 사용자 이름 가져오기
            String username = principal.getName();
            model.addAttribute("username", username);
        }

        ChatRoomDTO room = chatService.findRoomByPostId(postId);
        if(room == null) {
        	Post post =  postService.getReadData(postId);
        	//방 만들기
        	//1. chat room,
        	ChatRoomDTO dto = new ChatRoomDTO();
        	dto.setUserId(post.getUserId());//채팅방 주인 = 게시글 작성자
        	dto.setRoomName(post.getTitle());
        	dto.setChatType(ChatType.MSG);
        	dto.setMaxUserCnt(post.getRecruitment());
        	dto.setPostId(postId);
        	chatService.createChatRoom(dto);
        	
        	room = chatService.findRoomByPostId(postId);
      }
        List<ChatDTO> msg = msgChatService.getMsg(postId);
        model.addAttribute("room", room);
        model.addAttribute("msgs", msg);
        
        if (room.getChatType() == ChatType.MSG) {
            return "chatroom";
        }

        // 반환값이 없는 경우 기본 반환값 설정
        return "defaultViewName"; // 변경이 필요한 경우에 기본 뷰 이름으로 수정
    }


    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd) throws Exception{

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatService.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId) throws Exception{

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
    	chatService.delChatRoom(roomId);

        return "redirect:/chat";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId) throws Exception{

        return chatService.chkRoomUserCnt(roomId);
    }
    
 // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @GetMapping("/chat/leave")
    public String webSocketDisconnectListener(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo, HttpServletRequest request ,StompHeaderAccessor headerAccessor) throws Exception {
//        log.info("DisConnEvent {}", event);

//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
//        String userId = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String userId = sessionInfo.getUserId();
//        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String roomId = request.getParameter("roomId");

        log.info("headAccessor {}", headerAccessor);

        // 채팅방 유저 -1
        chatService.minusUserCnt(roomId);

        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String nickName = memberService.getUser(userId).getNickName();
        msgChatService.delUser(roomId, userId);

        if (nickName != null) {
            log.info("User Disconnected : " + nickName);

            // builder 어노테이션 활용
            ChatDTO chat = ChatDTO.builder()
                    .type(MessageType.LEAVE)
                    .nickName(nickName)
                    .message(nickName + " 님이 퇴장하였습니다.")
                    .build();

            template.convertAndSend("/sub/chat/room/" + roomId, chat);
        }
        return "redirect:/chat";
    }
}