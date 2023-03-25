<template>
  <div class="counter-view">
    <counter-with-sse> </counter-with-sse>
  </div>
</template>

<script>
import CounterWithSse from "@/components/CounterWithSse.vue";

export default {
  components: { CounterWithSse },
};
</script>

<style>
.counter-view {
}
</style>
