<template>
  <div class="m-theme" v-loading.fullscreen="loading">
    <div class="container">
      <!-- 用户导航栏 -->
      <div class="navbar-wrap m-content">
        <!-- 简介 -->
        <div class="info-wrap">
          <div class="user-info clearfix">
            <div class="float-left pl-5">
              <el-avatar :size="70" :src="merchant.avatar"> </el-avatar>
            </div>
            <div class="float-left pl-4 pt-1">
              <div class="username"> {{merchant.shopName}} </div>
              <div> {{merchant.email}} </div>
            </div>
          </div>
        </div>
        <!-- 导航栏 -->
        <el-tabs type="border-card" class="navbar-box m-tab-content" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="个人资料" name="/account/info"></el-tab-pane>
          <el-tab-pane label="安全设置" name="/account/security"></el-tab-pane>
        </el-tabs>
      </div>
      <!-- 内容-->
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { State } from 'vuex-class'

@Component
export default class Account extends Vue {
  @State((state: any) => state.account.merchant) private merchant: any
  @State((state: any) => state.app.loading) private loading: any

  private activeName = '/account/info';

  private handleClick(tabs: any) { // 路由跳转
    if (this.$route.path === this.activeName) {
      return
    }
    this.$router.push({path: this.activeName})
  }

  private mounted() {
    this.activeName = this.$route.path
  }
}
</script>

<style scoped lang="scss">
.navbar-wrap {
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
  .info-wrap {
    padding-top: 100px;
    background: url("../../assets/h-user.jpg") no-repeat;
    background-size: 100% auto;
    .user-info {
      background: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, .3));
      .username {
        color: #fff;
        font-size: 1.5rem;
      }
    }
  }
}
.navbar-box {
  border: none !important;
  box-shadow: none !important;
}
</style>
