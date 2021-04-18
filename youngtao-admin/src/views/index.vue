<template>
  <div class="yt-container">
    <Navbar />
    <div class="sidebar-box">
      <Sidebar />
    </div>
    <div class="content-box">
      <router-view/>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Navbar from '@/components/Navbar.vue';
import Sidebar from '@/components/Sidebar.vue';

@Component({
  components: {
    Navbar, Sidebar
  }
})
export default class Market extends Vue {
}
</script>

<style scoped lang="scss">
.yt-container {
  margin-top: 50px;
}
.sidebar-box {
  width: 300px;
  position: fixed;
  min-width: 100vh;
}
.content-box {
  margin-left: 300px;
  min-width: 900px;
}
</style>
