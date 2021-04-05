<template>
  <div style="height: 36px;">
    <el-dropdown class="pl-3" @click.native="$router.push(`/user`)">
      <!-- 头像 -->
      <el-avatar class="pointer" :size="36">
        <img v-if="JSON.stringify(userInfo) !== '{}'" :src="userInfo.avatar"/>
        <span v-else>游客</span>
      </el-avatar>
      <!-- 下拉菜单 -->
      <el-dropdown-menu slot="dropdown" class="user-dropdown">
        <div v-if="JSON.stringify(userInfo) !== '{}'" class="text-center">
          <el-dropdown-item @click.native="$router.push('/user')">{{userInfo.username}}</el-dropdown-item>
          <el-dropdown-item @click.native="$router.push('/user/security')">安全设置</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
        </div>
        <div v-else>
          <el-dropdown-item @click.native="openLoginDialog">登录/注册</el-dropdown-item>
        </div>
      </el-dropdown-menu>
    </el-dropdown>
    <!-- 登录窗 -->
    <login-dialog />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Action, State } from 'vuex-class';
import LoginDialog from './components/LoginDialog.vue';

@Component({
  components: {
    LoginDialog,
  },
})
export default class Avatar extends Vue {
  @State((state: any) => state.account.user) private userInfo: any
  @Action('app/openLoginDialog') private openLoginDialog: any
  
  private logout() {
    this.$store.dispatch('account/logout');
  }
}
</script>

<style scoped lang="scss">

</style>
