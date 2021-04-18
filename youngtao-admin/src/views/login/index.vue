<template>
  <div class="yt-container">
    <Navbar />
    <div class="login-wrap">
      <div class="img-box">
        <img src="@/assets/login.png" alt="">
      </div>
      <div class="form-box">
        <img class="logo-img" src="@/assets/logo.png" alt="">
        <div class="form-title">
          登录樱桃
        </div>
        <div class="form-item">
          <input v-model="loginForm.account" class="w-100" type="text" placeholder="请输入邮箱号">
        </div>
        <div class="form-item">
          <input v-model="loginForm.password" class="w-100" type="password" placeholder="请输入密码">
        </div>
        <button type="button" class="btn btn-secondary btn-block mb-2" @click="login">登录</button>
        <div class="form-hint">
          <router-link to="/register">没有账号？立即注册</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Navbar from '@/components/Navbar.vue';

@Component({
  components: {
    Navbar
  }
})
export default class Login extends Vue {
  private loginForm = {
    account: 'merchant@qq.com',
    password: '123456',
    type: 2
  }

  private login() {
    this.$store.dispatch('account/login', this.loginForm).then((res: any) => {
      this.$message({type: 'success', message: '登录成功'})
      this.$router.push('/')
    })
  }
}
</script>

<style scoped lang="scss">
$img-width: 560px;

.yt-container {
  background-color: #f6f6f6;
  min-height: 100vh;
  padding: 30px 50px;
  margin-top: 50px;
}
.login-wrap {
  width: $img-width + $img-width;
  margin: 0 auto;
  display: flex;
}
.img-box {
  width: $img-width;
  img {
    width: 100%;
  }
}
.form-box {
  width: $img-width;
  text-align: center;
  background-color: #fff;
  padding: 90px;
  .logo-img {
    width: 200px;
    margin-bottom: 50px;
  }
  .form-title {
    font-size: 20px;
    font-weight: bolder;
    margin-bottom: 50px;
  }
  .form-item {
    border-bottom: 1px solid #666;
    margin-bottom: 50px;
    input {
      border: 0;
      outline: none;
    }
  }
  .form-hint {
    font-size: 14px;
    text-align: right;
  }
}
</style>
