<template>
  <div class="counter">
    <p class="counter__description">hi, i'm counter</p>
    <p class="counter__status">{{ status }}</p>
    <form class="counter__query" @submit="startCount">
      <input type="text" v-model="size" placeholder="fill size..." />cnt
      <input type="text" v-model="speed" placeholder="fill speed..." />ms
      <button class="counter__query--button" @click="startCount">시작</button>
    </form>
    <ul class="counter__messages">
      <li
        class="counter__message"
        v-for="(message, index) in messages"
        :key="index"
      >
        <div class="counter__message--id">{{ message.id }}</div>
        <div class="counter__message--reply">{{ message.reply }}</div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      sse: null,
      sseId: null,
      status: "ready",
      size: 10,
      speed: 100,
      messages: [],
      messageIndex: null,
    };
  },
  methods: {
    async onSubmit(event) {
      event.preventDefault();
      this.startCount();
    },
    async startCount() {
      await this.requestConnect(this.requestCounting);
    },
    async requestConnect(onOpen) {
      if (this.sse != null) {
        this.sse.close();
      }
      this.sse = await new EventSource("/api/counter/connect");
      this.status = "preparing...";
      this.sse.addEventListener("connect", (event) => {
        console.log("connected...", event);
        const { data: receivedData, lastEventId: id } = event;
        this.sseId = id;
        this.status = receivedData;
        this.messageIndex = this.messages.push({ id, reply: "" }) - 1;
        onOpen();
      });
      this.sse.addEventListener("count", (event) => {
        const { data: receivedData } = event;
        const index = this.messageIndex;
        const { id, reply } = this.messages[index];
        const message = { id, reply: reply + receivedData };
        this.$set(this.messages, index, message);
      });
      this.sse.addEventListener("close", (event) => {
        const { data: receivedData } = event;
        console.log("close...data=", receivedData);
        this.sse.close();
      });
    },
    async requestCounting() {
      const { sseId, size, speed } = this;
      this.$axios
        .post(`/api/counter/count?id=${sseId}&size=${size}&speed=${speed}`)
        .then((response) => {
          console.log("카운팅 요청완료...", response);
        });
    },
  },
};
</script>

<style>
.counter {
}
.counter__query {
  display: flex;
  justify-content: center;
  gap: 0.5rem;
}
.counter__messages {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 0;
  max-width: 30rem;
  margin: 3rem auto;
}
.counter__message {
  list-style: none;
  border: 1px solid #e3e3e3;
  border-radius: 0.5em;
  text-align: left;
  display: flex;
  flex-direction: column;
  font-size: 14px;
}
.counter__message > * {
  padding: 0.5em;
}
.counter__message--id {
  background: #f6f6f6;
  color: green;
}
.counter__message--reply {
  overflow-wrap: anywhere;
}
</style>
