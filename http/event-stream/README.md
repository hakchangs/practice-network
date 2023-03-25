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
- `Content-Type: text/event-stream`
- `Transfer-Encoding: Chunked`
```http
HTTP/1.1 200
Content-Type: text/event-stream;charset=UTF-8
Transfer-Encoding: chunked
```

#### 2. 연결유지상태에서 지속적인 응답을 내려줌
- 브라우저 > 개발도구 > 네트워크 > EventStream 을 통해 서버전송 데이터를 트레이싱할 수 있음.

### 참조
- https://tecoble.techcourse.co.kr/post/2022-10-11-server-sent-events/
- https://developer.mozilla.org/en-US/docs/Web/API/EventSource