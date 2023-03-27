# SSE
- 일명 Server Sent Event
- 서버측에서 연결을 유지하며, 단방향으로 보낼수 있음.
- websokcet 의 양방향과 비교됨
- 별도 protocol 을 사용하지 않고, http 에서 해결가능함
- 구현에 따라 Pub/Sub, Observer 모두 가능함

## 작동방식
### 클라이언트
#### 1. 브라우저에서 서버측에 SSE 연결요청함
- 브라우저 스펙에서 제공되는 `EventSource` 를 생성
- 연결과 동시에 서버와 브라우저간 연결이 유지됨
- 서버는 지속적으로 데이터를 보낼 수 있음.
```http
GET /connect HTTP/1.1
Accept: text/event-stream
Cache-Control: no-cache
```

### 서버
#### 1. http 요청을 받으면, SSE 처리가능하도록 헤더를 내려주고 연결을 유지함
```http
HTTP/1.1 200
Content-Type: text/event-stream;charset=UTF-8
Transfer-Encoding: chunked
```

#### 2. 연결유지상태에서 지속적인 응답을 내려줌
- 브라우저 > 개발도구 > 네트워크 > EventStream 을 통해 서버전송 데이터를 트레이싱할 수 있음.

## vs Websocket

|      |Socket|	Server-Sent-Event|
|------|------|------------------|
|브라우저|지원 대부분 브라우저에서 지원| 	대부분 모던 브라우저 지원(polyfills 가능)|
|통신 방향	|양방향	|일방향(서버 -> 클라이언트)|
|리얼타임|	Yes|	Yes|
|데이터 형태|Binary, UTF-8|UTF-8
|자동 재접속|No|Yes(3초마다 재시도)|
|최대 동시 접속 수|브라우저 연결 한도는 없지만 서버 셋업에 따라 |다름 |HTTP를 통해서 할 때는 브라우저당 6개 까지 가능 / HTTP2로는 100개가 기본
|프로토콜|websocket|HTTP|
|베터리 소모량|큼|작음|
|Firewall 친화적|Nope|Yes|

### 구현
- SSE 가 더 간단함
- 별도의 프로토콜을 제공하기 위한 작업이 더 적음
- but, 일반화하기에는 무리가 있는듯함.
- Websocket 으로 한번 틀잡아놓으면 더 안정적으로 빠르게 구현할 수도 있을듯함.

### 성능
- 대부분의 블로그에서는 배터리사용량과 기기의 리소스 사용량이 Websocket 이 더 많다고 나옴.
- 또한 SSE 가 더 가벼워 빠르다고 나옴.
- but, 구체적인 근거없이 설명한것이 대부분
- 벤치마킹한것을 보면 사실상 속도, 자원사용량이 크게 의미있다고 보이지 않음
- 결국, 상황에 따라 결정하는게 나음
- http 망을 이용하고 방화벽처리를 위해 별도처리가 싫은경우 SSE 가 나음
- 규격화되고 안정적인 서비스를 만드려면 Websocket 이 나옴

### 사례
- 둘다 실시간성 서비스에 적용함
- Websocket 은 주식거래, 채팅 등 실시간-양방향이 필요한 경우 주로 사용
- SSE 는 알림, 뉴스 피드 등 서버에서 일방적으로 보내는 경우 많이 사용함

### 참조
- https://tecoble.techcourse.co.kr/post/2022-10-11-server-sent-events/
- https://developer.mozilla.org/en-US/docs/Web/API/EventSource
- https://www.timeplus.com/post/websocket-vs-sse