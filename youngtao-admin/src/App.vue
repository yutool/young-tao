<template>
  <div id="app">
    <Navbar />
    <div class="sidebar-box">
      <Sidebar />
    </div>
    <div class="content-box">
      <router-view/>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar'
import Sidebar from '@/components/Sidebar'
export default {
  components: {
    Navbar, Sidebar
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
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
