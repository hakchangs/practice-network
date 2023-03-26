<template>
  <div class="counter"></div>
</template>

<script>
export default {
  data() {
    return {
      size: null,
      speed: null,
      messages: null,
      messageIndex: null,
    };
  },
  mounted() {
    this.reset();
  },
  methods: {
    connect() {
      const { onConnected, onCount, onClose } = this;
      this.init(onConnected, onCount, onClose);
      this.doAfterConnect();
    },
    doAfterConnect() {},
    count() {
      if (!this.doValidateConnection()) {
        return;
      }
      this.messageIndex = this.messages.push({ reply: "" }) - 1;
      this.requestCounting(this.size, this.speed);
    },
    doValidateConnection() {},
    reset() {
      this.doReset();
      this.size = 10;
      this.speed = 100;
      this.messages = [];
      this.messageIndex = 0;
    },
    doReset() {},
    onConnected() {
      this.messageIndex = 0;
    },
    onCount(receivedMessage) {
      let { reply: previousReply } = this.messages[this.messageIndex];
      let { reply: receivedReply, id } = receivedMessage;
      const message = { id, reply: previousReply + receivedReply };
      this.$set(this.messages, this.messageIndex, message);
    },
    onClose() {},
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
