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
import com.lion09.member.Member;
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
    
    //네비바 클릭시 chatList
    @GetMapping("/chat")
    public String goChatRoom(@SessionAttribute(SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo,
    				Model model, Principal principal) throws Exception {
        model.addAttribute("list", chatService.findAllRoom(sessionInfo.getUserId()));

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
            log.debug("Username: {}", username);
        }

        log.debug("SHOW ALL ChatList {}", chatService.findAllRoom(sessionInfo.getUserId()));
        return "roomlist";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(@SessionAttribute(SessionConst.LOGIN_MEMBER) SessionInfo sessionInfo,
    		Model model, HttpServletRequest request, Principal principal) throws Exception {
    	
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
        	ChatRoomDTO dto = new ChatRoomDTO();
        	dto.setUserId(post.getUserId());//채팅방 주인 = 게시글 작성자
        	dto.setRoomName(post.getTitle());
        	dto.setChatType(ChatType.MSG);
        	dto.setMaxUserCnt(99);
        	dto.setPostId(postId);
        	chatService.createChatRoom(dto);
        	
        	room = chatService.findRoomByPostId(postId);
        	
//        	글 작성자 chatMessages에 넣어놓기
        	ChatDTO writerIntput = new ChatDTO();
        	writerIntput.setPostId(postId);
        	writerIntput.setUserId(post.getUserId());
        	writerIntput.setNickName(post.getNickName());
        	writerIntput.setType(MessageType.MASTER);
        	msgChatService.addMsg(writerIntput);
        }
        //사용자 입장시간 이후 대화기록
        List<ChatDTO> msg = msgChatService.getMsg(postId, sessionInfo.getUserId());
        //프로필 사진
        String profileImgName =
        		memberService.getUser(sessionInfo.getUserId()).getProfileImgName();
        
        model.addAttribute("room", room);
        model.addAttribute("msgs", msg);
        model.addAttribute("profileImgName", profileImgName);
        if (room.getChatType() == ChatType.MSG) {
            return "chatroom";
        }
        // 반환값이 없는 경우 기본 반환값 설정
        return "defaultViewName"; // 변경이 필요한 경우에 기본 뷰 이름으로 수정
    }
    
    public String delChatRoom(int postId) throws Exception{
    	chatService.delChatRoom(postId);
    	msgChatService.delMsg(postId);
        return "redirect:/chat";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{postId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable int postId) throws Exception{

        return chatService.chkRoomUserCnt(postId);
    }
    
 // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @GetMapping("/chat/leave")
    public String webSocketDisconnectListener(@SessionAttribute(SessionConst.LOGIN_MEMBER)SessionInfo sessionInfo, HttpServletRequest request ,StompHeaderAccessor headerAccessor) throws Exception {
//        log.info("DisConnEvent {}", event);
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
//        String userId = (String) headerAccessor.getSessionAttributes().get("userUUID");
//        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
//        log.info("headAccessor {}", headerAccessor);
    	
    	String userId = sessionInfo.getUserId();
        int postId = Integer.parseInt(request.getParameter("postId"));
        
        // 채팅방 유저 -1
        chatService.minusUserCnt(postId);
        
        //나간 회원 이름 알수 없음 설정
//        msgChatService.delUser(postId, userId);
        String nickName = memberService.getUser(userId).getNickName();

        if (nickName != null) {
            log.info("User Disconnected : " + nickName);

            // builder 어노테이션 활용
            ChatDTO chat = ChatDTO.builder()
                    .type(MessageType.LEAVE)
                    .postId(postId)
                    .nickName("알수없음")
                    .message(nickName + " 님이 퇴장하였습니다.")
                    .build();
            
            template.convertAndSend("/sub/chat/room/" + postId, chat);
            //퇴장 메세지 insert
            chat.setMessage(chat.getNickName() + " 님이 퇴장하였습니다.");
            chat.setUserId(userId);
            msgChatService.addMsg(chat);
            //닉네임 알수 없음으로 설정
            msgChatService.delUser(postId, userId);
        }
        //채팅방 삭제
        if(chatService.findRoomByPostId(postId).getUserCount() == 0) {
        	System.out.println(postId);
        	msgChatService.delMsg(postId);
        	chatService.delChatRoom(postId);
        }
        
        return "redirect:/chat";
    }
}