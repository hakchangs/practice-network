import jsonSerializer from "@/support/json-serializer";

let ws = null;

export default {
  data() {
    return { status: "ready" };
  },
  methods: {
    init(connectedAction, countAction, closeAction) {
      this.$_webSocketCounterMixin_clear();
      this.$_webSocketCounterMixin_connect();
      this.$_webSocketCounterMixin_addListeners({
        connectedAction,
        countAction,
        closeAction,
      });
    },
    requestCounting(size, speed) {
      ws.send(jsonSerializer.serialize({ size, speed }));
      console.log("카운팅 요청완료...");
    },
    validateConnection() {
      if (!this.$_webSocketCounterMixin_isConnected) {
        this.status = "not connected...";
        return false;
      }
      return true;
    },
    resetStatus() {
      this.status = "ready";
    },
    $_webSocketCounterMixin_connect() {
      this.status = "try to connect...";
      ws = new WebSocket("ws://localhost:8002/ws/counter");
      console.log("연결 요청중...");
    },
    $_webSocketCounterMixin_clear() {
      if (ws != null) {
        ws.close();
      }
    },
    $_webSocketCounterMixin_addListeners({
      connectedAction,
      countAction,
      closeAction,
    }) {
      ws.onopen = () => {
        console.log("[onopen] 연결되었습니다.");
        this.status = "connected!";
        onConnectedListener(connectedAction);
      };
      ws.onmessage = (event) => {
        console.log(`[onmessage] ${event}`);
        onCountListener(event, countAction);
      };
      ws.onclose = (event) => {
        console.log(`[onclose] 연결이 종료되었습니다.`);
        this.status = "closed";
        onCloseListener(event, closeAction);
      };
      ws.onerror = (error) => {
        console.log("[onerror] 에러가 발생했습니다", error);
        onErrorListener(error);
      };
      console.log("리스너 등록완료...");
    },
  },
  computed: {
    $_webSocketCounterMixin_isConnected() {
      return this.status.startsWith("connected");
    },
  },
};

//listeners
function onConnectedListener(connectedAction) {
  connectedAction();
}
function onCountListener(event, countAction) {
  const receivedData = jsonSerializer.deserialize(event.data);
  const message = { id: receivedData.id, reply: receivedData.message };
  countAction(message);
}
function onCloseListener(event, closeAction = () => {}) {
  if (event.wasClean) {
    const { code, reason } = event;
    console.log(`[onclose] 정상종료. code=${code}, reason=${reason}`);
  } else {
    console.log(`[onclose] 비정상 종료`);
  }
  closeAction();
}
function onErrorListener(error, errorAction = () => {}) {
  errorAction();
}
