<template>
  <div class="m-content p-3 rounded-sm">
    <div class="password-box">
      <el-form :model="passwordForm" status-icon :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="passwordForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="passwordForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Security extends Vue {
  private passwordForm = {
    password: '',
    checkPass: '',
  }
  private rules = {
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
    ],
    checkPass: [
      { required: true, message: '请输入确认密码', trigger: 'blur' },
    ]
  }
}
</script>

<style scoped lang="scss">
.password-box {
  max-width: 600px;
  margin: 0 auto;
}
</style>
