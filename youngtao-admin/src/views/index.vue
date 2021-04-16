<template>
  <div class="container">
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Market extends Vue {
  private mounted() {
    this.$router.push('/product/list')
  }
}
</script>

<style scoped lang="scss">
</style>
