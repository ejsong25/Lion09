'use strict';

// document.write("<script src='jquery-3.6.1.js'></script>")
document.write("<script\n" +
    "  src=\"https://code.jquery.com/jquery-3.6.1.min.js\"\n" +
    "  integrity=\"sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=\"\n" +
    "  crossorigin=\"anonymous\"></script>")


var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var profileImgName = null;

// roomId 파라미터 가져오기
const url = new URL(location.href).searchParams;
const postId = url.get('postId');

function connect(event) {
    username = document.querySelector('#name').value.trim();
    profileImgName = document.querySelector('#profileImgName').value;

    // usernamePage 에 hidden 속성 추가해서 가리고
    // chatPage 를 등장시킴
    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    // 연결하고자하는 Socket 의 endPoint
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);


    event.preventDefault();
}

function onConnected() {
    // sub 할 url => /sub/chat/room/postId 로 구독한다
    stompClient.subscribe('/sub/chat/room/' + postId, onMessageReceived);

    // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
    // /pub/chat/enterUser 로 메시지를 보냄
    stompClient.send("/pub/chat/enterUser",
        {},
        JSON.stringify({
            "postId": postId,
            nickName: username
//            type: 'ENTER'
        })
    )
    connectingElement.classList.add('hidden');
}


// ajax 로 유저리스트를 받으며 클라이언트가 입장/퇴장 했다는 문구가 나왔을 때마다 실행된다.
function getUserList() {
    const $list = $("#list");

    $.ajax({
        type: "GET",
        url: "/chat/userlist",
        data: {
            "postId": postId
        },
        success: function (data) {
            var users = "";
            for (let i = 0; i < data.length; i++) {
                //console.log("data[i] : "+data[i]);
                users += "<li class='dropdown-item'>" + data[i] + "</li>"
            }
            $list.html(users);
        }
    })
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

// 메시지 전송때는 JSON 형식을 메시지를 전달한다.
function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        var chatMessage = {
            "postId": postId,
            nickName: username,
            message: messageInput.value,
            type: 'TALK'
        };

        stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

// 메시지를 받을 때도 마찬가지로 JSON 타입으로 받으며,
// 넘어온 JSON 형식의 메시지를 parse 해서 사용한다.
function onMessageReceived(payload) {
    var chat = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (chat.type === 'ENTER') {  // chatType 이 enter 라면 아래 내용
        messageElement.classList.add('event-message');
        chat.content = chat.nickName + chat.message;
//        getUserList();

    } else if (chat.type === 'LEAVE') { // chatType 가 leave 라면 아래 내용
        messageElement.classList.add('event-message');
        chat.content = chat.nickName + chat.message;
        getUserList();

    } else { // chatType 이 talk 라면 아래 내용
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarElement2 = document.createElement('img');
        avatarElement2.src = '/img/mypage/' + chat.profileImgName;
        
        avatarElement2.style.width = '45px'; 
		avatarElement2.style.height = '45px';
		
        avatarElement.appendChild(avatarElement2);
        
        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(chat.nickName);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }
	getUserList();
    var contentElement = document.createElement('p');

    // 만약 s3DataUrl 의 값이 null 이 아니라면 => chat 내용이 파일 업로드와 관련된 내용이라면
    // img 를 채팅에 보여주는 작업
    if(chat.s3DataUrl != null){
        var imgElement = document.createElement('img');
        imgElement.setAttribute("src", chat.s3DataUrl);
        imgElement.setAttribute("width", "300");
        imgElement.setAttribute("height", "300");
        
        var downBtnElement = document.createElement('button');
        downBtnElement.setAttribute("class", "btn fa fa-download");
        downBtnElement.setAttribute("id", "downBtn");
        downBtnElement.setAttribute("name", chat.fileName);
        downBtnElement.setAttribute("onclick", `downloadFile('${chat.fileName}', '${chat.fileDir}')`);

        contentElement.appendChild(imgElement);
        contentElement.appendChild(downBtnElement);

	    messageElement.appendChild(contentElement);
	
	    messageArea.appendChild(messageElement);
	    messageArea.scrollTop = messageArea.scrollHeight;
	    
    }else if(chat.message != null){
        // 만약 s3DataUrl 의 값이 null 이라면
        // 이전에 넘어온 채팅 내용 보여주기
       var messageText = document.createTextNode(chat.message);
       contentElement.appendChild(messageText);
   	  
   	   messageElement.appendChild(contentElement);

       messageArea.appendChild(messageElement);
       messageArea.scrollTop = messageArea.scrollHeight;
    }
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)

/// 파일 업로드 부분 ////
function uploadFile(){

    var file = $("#file")[0].files[0];
    var formData = new FormData();
    formData.append("file",file);
    formData.append("postId", postId);

    // 확장자 추출
    var fileDot = file.name.lastIndexOf(".");
    // 확장자 검사
    var fileType = file.name.substring(fileDot + 1, file.name.length);
    // console.log("type : " + fileType);
    if (!(fileType == "png" || fileType == "jpg" || fileType == "jpeg" || fileType == "gif"))
    {
        alert("파일 전송은 png, jpg, gif, jpeg 만 가능합니다.");
        return;
    }

    // ajax 로 multipart/form-data 를 넘겨줄 때는
    //         processData: false,
    //         contentType: false
    // 처럼 설정해주어야 한다.

    // 동작 순서
    // post 로 rest 요청한다.
    // 1. 먼저 upload 로 파일 업로드를 요청한다.
    // 2. upload 가 성공적으로 완료되면 data 에 upload 객체를 받고,
    // 이를 이용해 chatMessage 를 작성한다.
    $.ajax({
        type : 'POST',
        url : '/s3/upload',
        data : formData,
        processData: false,
        contentType: false
        
    }).done(function (data){

        var chatMessage = {
            "postId": postId,
            nickName: username,
            message: username+" 님의 파일 업로드",
            type: 'TALK',
            s3DataUrl : data.s3DataUrl, // Dataurl
            "fileName": file.name, // 원본 파일 이름
            "fileDir": data.fileDir // 업로드 된 위치
        };

        // 해당 내용을 발신한다.
        stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
    }).fail(function (error){
        alert(error);
    })
}

// 파일 다운로드 부분 //
// 버튼을 누르면 downloadFile 메서드가 실행됨
// 다운로드 url 은 /s3/download+원본파일이름
function downloadFile(name, dir){
    let url = "/s3/download/"+name;

    // get 으로 rest 요청한다.
    $.ajax({
        url: "/s3/download/"+name, // 요청 url 은 download/{name}
        data: {
            "fileDir" : dir // 파일의 경로를 파라미터로 넣는다.
        },
        dataType: 'binary', // 파일 다운로드를 위해서는 binary 타입으로 받아야한다.
        xhrFields: {
            'responseType': 'blob' // 여기도 마찬가지
        },
        success: function(data) {

            var link = document.createElement('a');
            link.href = URL.createObjectURL(data);
            link.download = name;
            link.click();
        }
    });
}