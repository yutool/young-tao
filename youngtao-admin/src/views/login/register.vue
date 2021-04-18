<template>
  <div class="yt-container">
    <Navbar />
    <div class="login-wrap">
      <div class="img-box">
        <img src="@/assets/login.png" alt="">
      </div>
      <div class="form-box">
        <div class="form-title">
          注册账号
        </div>
        <div class="form-item">
          <input v-model="registerForm.name" type="text" placeholder="店铺名称">
        </div>
        <div class="form-item">
          <input v-model="registerForm.email" type="text" placeholder="邮箱号">
        </div>
        <div class="form-item" style="display: flex">
          <input v-model="registerForm.verifyCode" type="text" style="flex: 1" placeholder="验证码">
          <div :class="[isActive  ? 'active' : 'disable']" @click="getVerifyCode">{{verifyHint}}</div>
        </div>
        <div class="form-item">
          <input v-model="registerForm.password" type="password" placeholder="密码">
        </div>
        <button type="button" class="btn btn-secondary btn-block mb-2" @click="register">注册</button>
        <div class="form-hint">
          注册即表示您同意 <a href="#">《服务协议》</a> 、  <a href="#">《免责声明》</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import Navbar from '@/components/Navbar.vue';
import { register } from '@/api/uac/merchant'
import { sendRegisterCode } from '@/api/uac/email'

@Component({
  components: {
    Navbar
  }
})
export default class Register extends Vue {
  private registerForm = {
    name: '',
    email: '',
    verifyCode: '',
    password: ''
  }

  private isActive  = false;
  private verifyHint = '获取验证码';

  private register() {
    register(this.registerForm).then((res: any) => {
      this.$message({type: 'success', message: '注册成功'})
      this.$router.push('/login')
    })
  }

  private getVerifyCode() { // 获取验证码
    if (!this.registerForm.email) {
      this.$message({type: 'info', message: '邮箱不能为空'})
      return
    }
    sendRegisterCode({toAddr: this.registerForm.email, type: 2}).then((res: any) => {
      this.$message({type: 'success', message: '发送成功，请注意查收'});
      $('#verifyBtn').prop('disabled', true);
      let second = 10;
      const interval = setInterval(() => {
        second--;
        this.verifyHint = second + 's重新获取';
        if (second <= 0) {
          window.clearInterval(interval);
          this.verifyHint = '重新获取验证码';
          $('#verifyBtn').prop('disabled', false);
        }
      }, 1000);
    });
  }

  @Watch('registerForm.email')
  private validate() {
    const exp = new RegExp('^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$')
    this.isActive  = exp.test(this.registerForm.email)
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
      width: 100%;
    }
  }
  .form-hint {
    font-size: 14px;
  }
}
.active {
  cursor: pointer;
}
.disable {
  cursor: not-allowed;
  color: #999;
}
</style>
