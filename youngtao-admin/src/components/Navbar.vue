<template>
  <nav class="navbar bg-white shadow-sm pt-1 pb-1 fixed-top">
    <!-- logo -->
    <router-link to="/" class="navbar-brand ml-3">
      <img src="@/assets/logo.png" width="90px" />
    </router-link>
    <!-- 头像 -->
    <div class="ml-auto mr-5" style="height: 36px;">
      <el-dropdown class="pl-3" @command="handleCommand">
        <el-avatar class="user-avatar pointer" :size="36" @click.native="$router.push('/account')">
          <img v-if="JSON.stringify(merchantInfo) !== '{}'" :src="merchantInfo.avatar"/>
          <span v-else>游客</span>
        </el-avatar>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <div v-if="JSON.stringify(merchantInfo) !== '{}'" class="text-center">
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
          </div>
          <div v-else>
            <el-dropdown-item @click.native="$router.push('/login')">
              登录/注册
            </el-dropdown-item>
          </div>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </nav>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Action, State } from 'vuex-class';

@Component
export default class Navbar extends Vue {
  @State((state: any) => state.account.merchant) private merchantInfo: any;
  private handleCommand() {
    //
  }
  private logout() {
    this.$store.dispatch('account/logout')
  }
}
</script>

<style scoped lang="scss">

</style>
