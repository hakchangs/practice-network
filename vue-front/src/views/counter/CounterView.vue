<template>
  <div class="counter-view">
    <nav>
      <router-link to="/counter/sse">SSE</router-link> |
      <router-link to="/counter/ws">WebSocket</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {};
</script>

<style>
.counter-view {
}
</style>
