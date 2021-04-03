<template>
  <div class="m-content p-3 rounded-sm">
    <div class="password-box">
      <el-form :model="passwordForm" ref="passwordForm" status-icon :rules="rules" label-width="100px" class="demo-ruleForm">
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
import { Component, Ref, Vue } from 'vue-property-decorator';
import { updatePassword } from '@/api/uac/userInfo'

@Component
export default class Security extends Vue {
  private passwordForm = {
    password: '',
    checkPass: '',
  }
  @Ref('passwordForm') private refPasswordForm: any
  
  private validatePass2 = (rule: any, value: any, callback: any) => {
    if (value === '') {
      callback(new Error('请再次输入密码'));
    } else if (value !== this.passwordForm.password) {
      callback(new Error('两次输入密码不一致!'));
    } else {
      callback();
    }
  }

  private rules = {
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
    ],
    checkPass: [
      { required: true, message: '请再次输入密码', trigger: 'blur' },
      { validator: this.validatePass2, trigger: 'blur' },
    ],
  }

  private submitForm() {
    this.refPasswordForm.validate((valid: any) => {
      if (!valid) {
        return 
      }
      updatePassword(this.passwordForm).then((res: any) => {
        this.$message({type: 'success', message: '更新成功'})
        this.resetForm()
      })
    })
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
