<template>
  <div v-loading.fullscreen="loading" class="product-wrap">
    <router-view />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { State } from 'vuex-class'

@Component
export default class Promo extends Vue {
  @State((state: any) => state.app.loading) private loading: any
}
</script>

<style scoped lang="scss">
.product-wrap {
  background-color: #f6f6f6;
  min-height: 100vh;
}
</style>
