package com.lion09.chat;

import lombok.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lion09.board.Post;

// Stomp 를 통해 pub/sub 를 사용하면 구독자 관리가 알아서 된다!!
// 따라서 따로 세션 관리를 하는 코드를 작성할 필도 없고,
// 메시지를 다른 세션의 클라이언트에게 발송하는 것도 구현 필요가 없다!
@Table(name = "chatRoom")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    
	@Id
	private Integer num;
	
	@Column(nullable = true)
	private String userId;
	
	@Column(nullable = true)
    private String roomId; // 채팅방 아이디
    
    private int postId;
    
    @Column(nullable = true)
    private String roomName; // 채팅방 이름
    
    @Column(nullable = true)
    private int userCount; // 채팅방 인원수
    
    @Column(nullable = true)
    private int maxUserCnt; // 채팅방 최대 인원 제한

    @Column(nullable = true)
    private String roomPwd; // 채팅방 삭제시 필요한 pwd
    
    @Column(nullable = true)
    private Boolean secretChk; // 채팅방 잠금 여부
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ChatType chatType; //  채팅 타입 여부
}