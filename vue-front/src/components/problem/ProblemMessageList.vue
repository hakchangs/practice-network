<template>
  <div class="problem-message-list">
    <problem-message
      v-for="(msg, index) in problemMessageList"
      :key="index"
      :id="msg.id"
      :name="msg.name"
      :description="msg.description"
    ></problem-message>
  </div>
</template>

<script>
import ProblemMessage from "./ProblemMessage.vue";
export default {
  components: { ProblemMessage },
  props: {
    problemMessageList: Array,
  },
};
</script>

<style></style>
