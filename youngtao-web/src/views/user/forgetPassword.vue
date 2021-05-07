<template>
  <div class="container">
    <el-page-header @back="$router.push('/')" content="忘记密码" class="pt-3 pb-3">
    </el-page-header>
    <div class="password-box">
      <el-form :model="passwordForm" ref="passwordForm" status-icon :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="邮箱号" prop="email">
          <el-input type="email" v-model="passwordForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="verifyCode">
          <el-input v-model="passwordForm.verifyCode" placeholder="请获取验证码">
            <el-button id="verifyBtn" slot="append" style="width: 115px;" @click="getVerifyCode">
              {{verifyHint}}
            </el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="passwordForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="passwordForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Ref, Vue } from 'vue-property-decorator'
import { resetPassword } from '@/api/uac/userInfo'
import { sendForgetPasswordCode } from '@/api/uac/email'

@Component
export default class ForgetPassword extends Vue {
  private passwordForm = {
    email: '',
    verifyCode: '',
    password: '',
    checkPass: '',
  }
  @Ref('passwordForm') private refPasswordForm: any
  private verifyHint = '获取验证码'
  private rules = {
    email: [
      { required: true, message: '请输入邮箱', trigger: 'blur' },
      { type: 'email', message: '邮箱格式有误', trigger: 'blur' },
    ],
    verifyCode: [
      { required: true, message: '请输入验证码', trigger: 'change' },
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
    ],
    checkPass: [
      { required: true, message: '请再次输入密码', trigger: 'blur' },
      { validator: (rule: any, value: any, callback: any) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.passwordForm.password) {
            console.log(this.passwordForm)
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        }, trigger: 'blur' },
    ],
  }

  private submitForm() {
    this.refPasswordForm.validate((valid: any) => {
      if (!valid) {
        return 
      }
      resetPassword(this.passwordForm).then((res: any) => {
        this.$message({type: 'success', message: '重置密码成功'})
        this.resetForm()
        this.$router.push('/')
      })
    })
  }

  private getVerifyCode() { // 获取验证码
    if (!this.passwordForm.email) {
      this.$message({type: 'info', message: '请输入邮箱'})
      return
    }
    sendForgetPasswordCode({toAddr: this.passwordForm.email}).then((res: any) => {
      this.$message({type: 'success', message: '发送成功，请注意查收'});
      $('#verifyBtn').prop('disabled', true);
      let second = 10;
      const interval = setInterval(() => {
        second--;
        this.verifyHint = second + 's重新获取';
        if (second <= 0) {
          window.clearInterval(interval);
          this.verifyHint = '获取验证码';
          $('#verifyBtn').prop('disabled', false);
        }
      }, 1000);
    });
  }

  private resetForm() {
    this.refPasswordForm.resetFields()
  }
}
</script>

<style scoped lang="scss">
.password-box {
  max-width: 600px;
  margin: 0 auto;
}
</style>
