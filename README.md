### spring boot2 + websocket 을 이용한 msg queue 구현 
websocket을 이용한 (sockjs, stomp 를 이용하지 않은) 메시지 큐 구현


### how to run
```
> ./gradlew bootrun
```

### 클래스 
#### UserMsg
websocket client <> websocketHandler 사이의 dto
#### InternalMsg
MessageService <> websocketHandler 내부 레이어 사이의 dto
#### DefaultController
메시지 채널을 만들기 위한 http api 전용 컨트롤러
#### WebsocketConfig.MessageHandler
웹소켓의 이벤트 처리를 담당하는 핸들러. 이 예제에선 restController와 비슷한 역할이라 보면 될거같음.
#### WebsocketConfig
웹소켓의 핸들러 설정 및 라우팅 처리 
#### IdGenerator
메시지 채널의 고유한 아이디 생성을 담당
#### Channel
메시지 채널, 한개당 채널 한개, 전체 채널은 MessageService가 in-memory로 관리
#### MessageService
메시지 채널 생성, join 처리를 위한 서비스


### test
1. 서버 실행후 메시지 채널 생성을 만들기 위해 아래 api를 실행. 
```shell
POST http://localhost:8080/create
>> 17f6367fe830002
```
2. 웹소켓 클라이언트를 이용하여 접속한다.
```shell
ws://localhost:8080/ws/msg
Connecting to ws://localhost:8080/ws/msg
Connected to ws://localhost:8080/ws/msg
```
3. 연결이 성공하면 리턴받은 채널 아이디를 이용, json string을 전송한다. 같은 내용이 echo로 전달된다.
```json
{
  "channelId": "17f6367fe830002",
  "sender": "first",
  "message": "hi"
}
```
5. 아직 받을수있는 다른 클라이언트가 없으므로, 2에서 했던것처럼 다른 커넥션을 새로 생성한다.
6. 연결이 성공하면 마찬가지로 json string을 생성하여 전송한다. 
```json
{
  "channelId": "17f6367fe830002",
  "sender": "second",
  "message": "hi2"
}
```
7. 첫번째 클라이언트와 두번째 클라이언트 모두 같은 echo가 전달되었는지 확인한다.
8. 격리된 채널임을 확인하기 위해서 1부터 다시 시도하여 메시지가 서로 얽히지 않는것을 확인한다. 
