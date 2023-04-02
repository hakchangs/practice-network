<template>
  <div class="problem-monitor">
    <button @click="connect">연결</button>
    <button @click="addProblem">추가</button>
    <p>{{ status }}</p>
    <problem-message-list :problemMessageList="msgs"></problem-message-list>
  </div>
</template>

<script>
import ProblemMessageList from "./ProblemMessageList.vue";

export default {
  components: { ProblemMessageList },
  data() {
    return {
      msgs: [
        // { id: 1, name: "msg1", description: "상세" },
        // { id: 2, name: "msg1", description: "상세" },
      ],
      sse: null,
      status: "ready",
    };
  },
  methods: {
    disconnect() {
      if (this.sse) {
        this.sse.close();
      }
    },
    connect() {
      this.disconnect();

      const sse = new EventSource("/api/sse/monitor/connect");
      sse.addEventListener("connect", (event) => {
        this.status = "connected";
        console.log("[connect] 연결되었습니다.", event);
      });
      sse.addEventListener("problem", (event) => {
        this.status = "problem 발생!";
        console.log("[problem]", event);
        const { data: jsonData } = event;
        const data = JSON.parse(jsonData);
        this.msgs.push(data);
      });
      sse.addEventListener("close", (event) => {
        this.status = "close...";
        console.log("[close] 연결해제가 요청되었습니다.", event);
      });
      console.log("리스너 등록완료...");
      this.sse = sse;
    },
    addProblem() {
      this.$axios.post("/api/sse/problems", {
        name: "[테스트] 알림이 왔습니다.",
        description: "서버에 문제가 생겼습니다. 관리자에게 문의해주세요.",
      });
    },
  },
};
</script>

<style></style>
