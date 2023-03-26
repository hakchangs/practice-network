<template>
  <div class="counter">
    <p class="counter__description">hi, i'm counter</p>
    <p class="counter__status">{{ getStatus() }}</p>
    <form class="counter__query" @submit.prevent="count">
      <input type="text" v-model="size" placeholder="fill size..." />cnt
      <input type="text" v-model="speed" placeholder="fill speed..." />ms
      <input
        type="submit"
        value="시작"
        class="counter__query--button"
        @click.prevent="count"
      />
      <input
        type="button"
        value="초기화"
        class="counter__query--button"
        @click.prevent="reset"
      />
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
import BaseCounter from "@/components/counter/BaseCounter.vue";
import SseCounterMixin from "@/mixins/sse-counter-mixin";
export default {
  extends: BaseCounter,
  mixins: [SseCounterMixin],
  methods: {
    count() {
      this.connect();
    },
    doReset() {
      this.resetStatus();
    },
    onConnected() {
      const id = this.getSessionId();
      this.messageIndex = this.messages.push({ id, reply: "" }) - 1;
      this.requestCounting(this.size, this.speed);
    },
    onCount(receivedMessage) {
      const { reply: previousReply, id } = this.messages[this.messageIndex];
      const { reply: receivedReply } = receivedMessage;
      const message = { id, reply: previousReply + receivedReply };
      this.$set(this.messages, this.messageIndex, message);
    },
  },
};
</script>
