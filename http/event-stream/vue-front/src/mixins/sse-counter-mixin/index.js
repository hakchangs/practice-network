let sse = null;
let sessionId = null;
let status = "ready";

export default {
  methods: {
    init(connectedAction, countAction, closeAction) {
      clearConnection();
      requestConnection();
      addListeners({ connectedAction, countAction, closeAction });
    },
    async requestCounting(size, speed) {
      await this.$axios.post(
        `/api/sse/counter/count?id=${sessionId}&size=${size}&speed=${speed}`
      );
      console.log("카운팅 요청완료...");
    },
    validateConnection() {
      if (!this.isConnected) {
        status = "not connected...";
        return false;
      }
      return true;
    },
    resetStatus() {
      status = "ready";
    },
    getStatus() {
      return status;
    },
    getSessionId() {
      return sessionId;
    },
  },
  computed: {
    isConnected() {
      return this.getStatus().startsWith("connected");
    },
  },
};

//connection
function clearConnection() {
  if (sse != null) {
    sse.close();
  }
}
function requestConnection() {
  status = "try to connect...";
  sse = new EventSource("/api/sse/counter/connect");
  console.log("연결 요청중...");
}

//listener
function addListeners({ connectedAction, countAction, closeAction }) {
  sse.addEventListener("connect", (event) => {
    console.log("[connect] 연결되었습니다.", event);
    onConnectedListener(event, connectedAction);
  });
  sse.addEventListener("count", (event) => {
    console.log("[count]", event);
    onCountListener(event, countAction);
  });
  sse.addEventListener("close", (event) => {
    console.log("[close] 연결해제가 요청되었습니다.", event);
    onCloseListener(event, closeAction);
  });
  console.log("리스너 등록완료...");
}
function onConnectedListener(event, connectedAction) {
  const { data: receivedData, lastEventId: id } = event;
  sessionId = id;
  status = receivedData;
  connectedAction();
}
function onCountListener(event, countAction) {
  const { data: receivedData } = event;
  const message = { id: sessionId, reply: receivedData };
  countAction(message);
}
function onCloseListener(event, closeAction = () => {}) {
  status = "closed";
  sse.close();
  closeAction();
}
