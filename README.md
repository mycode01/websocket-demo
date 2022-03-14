### spring boot2 + websocket 을 이용한 msg queue 구현 
websocket(sockjs, STOMP)를 이용한 메시지 큐 구현  
sockJs, STOMP를 이용해야하기 때문에 화면부가 예제에 추가되어있음.  
또, 여러 인스턴스 환경에서 동작을 테스트하기 위해 외부 큐를 이용하였음. 

### requirements
rabbitMQ(+STOMP plugin)  
mysql

### how to run
```
> ./gradlew bootrun
```

### 클래스 
#### UserMsg
websocket client <> MessageHandler 사이의 dto
#### ChannelController, DefaultController
메시지 채널을 만들고 보여주기 위한 컨트롤러, 서블릿 엔진을 사용하기 위한 컨트롤러 
#### MessageHandler
웹소켓의 이벤트 처리를 담당하는 핸들러.
#### WebsocketConfig
웹소켓의 핸드쉐이크 및 엔드포인트, exchange 설정(queue relay)
#### RabbitMqConfig
웹소켓 외부 브로커로 사용될 queue의 설정 
#### IdGenerator
메시지 채널의 고유한 아이디 생성을 담당
#### Channel, Message
메시지 채널 및 메시지 로그 
#### ChannelService
채널 생성 및 조회를 위한 서비스 
#### LogService
메시지 로그처리를 위한 서비스 

### test
1. 각 환경에 맞게 application.properties 및 RabbitMqConfig 수정.
2. 멀티 인스턴스에서의 동작을 확인하기 위해 두개의 인스턴스를 실행시킨다.
3. 로그에서 각 인스턴스의 http 포트를 확인 후 브라우저에서 아래 경로로 접근한다.
```localhost:port번호/index```
4. 메시지 전달을 위한 채널을 생성한 후 각 브라우저에서 해당 채널로 이동한다.
5. 각 브라우저에서 메시지 전송을 확인, 각 인스턴스에서 로그를 확인한다. 
6. db를 확인하여 각 메시지가 로깅되고 있음을 확인한다.
7. 격리된 채널임을 확인하기 위해서 4부터 다시 시도하여 채널간 메시지가 서로 얽히지 않는것을 확인한다. 
