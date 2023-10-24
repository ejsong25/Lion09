package com.lion09.chat;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Table(name = "chatMessages")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    // 메시지  타입 : 입장, 채팅
    // 메시지 타입에 따라서 동작하는 구조가 달라진다.
    // 입장과 퇴장 ENTER 과 LEAVE 의 경우 입장/퇴장 이벤트 처리가 실행되고,
    // TALK 는 말 그대로 내용이 해당 채팅방을 SUB 하고 있는 모든 클라이언트에게 전달된다.

	@Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private MessageType type; // 메시지 타입
    
    @Id
    private Integer num;
    
    @Column(nullable = true)
    private int postId; // 방 번호 = 게시글 번호
    
    @Column(nullable = true)
    private String roomId; // 방 번호 = 게시글 번호
    
    @Column(nullable = true)
    private String userId;
    
    @Column(nullable = true)
    private String nickName; // 채팅을 보낸 사람
    
    @Column(nullable = true)
    private String message; // 메시지
    
    @Column(nullable = true)
    private Timestamp time; // 채팅 발송 시간

    /* 파일 업로드 관련 변수 */
    @Column(nullable = true)
    private String s3DataUrl; // 파일 업로드 url
    
    @Column(nullable = true)
    private String fileName; // 파일이름
    
    @Column(nullable = true)
    private String fileDir; // s3 파일 경로
    
  	private String profileImgName;
}
