<template>
  <div class="problem-message">
    <div class="problem-message__header">
      <!-- <p class="problem-message--text">{{ id }}</p> -->
      <p class="problem-message--text">{{ name }}</p>
    </div>
    <div class="problem-message__body">
      <p class="problem-message--text">{{ description }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    id: Number,
    name: String,
    description: String,
  },
};
</script>

<style>
.problem-message {
  display: flex;
  flex-direction: column;
  background: #f8fcff;
  text-align: left;
  margin: 1rem;
  border-radius: 0.5rem;
  border: 1px solid #d8d8d8;
}
.problem-message > * {
  padding: 0.5rem;
}
.problem-message__header {
  background: #ecefec;
}
.problem-message__body {
}
.problem-message__header > p,
.problem-message__body > p {
  margin: 0;
}
</style>
