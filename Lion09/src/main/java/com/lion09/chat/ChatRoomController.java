package com.lion09.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatService chatService;
    
    @GetMapping("/chat")
    public String goChatRoom(Model model, Principal principal) {
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
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr) {

        // log.info("chk {}", secretChk);

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDTO room;

        room = chatService.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);


        log.info("CREATE Chat Room [{}]", room);

        rttr.addFlashAttribute("roomName", room);
        return "redirect:/chat";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId, Principal principal) {
        log.info("roomId {}", roomId);

        if (principal != null) {
            // Principal 객체에서 사용자 이름 가져오기
            String username = principal.getName();
            model.addAttribute("username", username);

            // 여기서 사용자 정보를 가져오는 추가적인 로직을 구현하실 수 있습니다.
        }

        ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        model.addAttribute("room", room);

        if (ChatRoomDTO.ChatType.MSG.equals(room.getChatType())) {
            return "chatroom";
        }

        // 반환값이 없는 경우 기본 반환값 설정
        return "defaultViewName"; // 변경이 필요한 경우에 기본 뷰 이름으로 수정
    }


    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd){

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatService.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId){

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
    	chatService.delChatRoom(roomId);

        return "redirect:/chat";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return chatService.chkRoomUserCnt(roomId);
    }
}