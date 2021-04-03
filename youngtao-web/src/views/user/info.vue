<template>
  <div class="m-content p-3 rounded-sm">
    <el-form class="info-box" ref="userInfo" :rules="rules" :model="userInfo" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="userInfo.username"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userInfo.email" disabled></el-input>
      </el-form-item>
      <el-form-item label="手机">
        <el-input v-model="userInfo.telephone" disabled></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="userInfo.gender" class="pt-2">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker type="date" placeholder="选择日期" v-model="userInfo.birthday" ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveInfo">保存信息</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import { Component, Ref, Vue } from 'vue-property-decorator'
import { State } from 'vuex-class'
import { Form } from 'element-ui'
import { updateUserInfo } from '@/api/uac/userInfo'

@Component
export default class UserInfo extends Vue {
  @State((state: any) => state.account.user) private userInfo: any
  @Ref('userInfo') private refUserInfo!: Form

  private rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
    ],
    gender: [
      { required: true, message: '请选择性别', trigger: 'blur' },
    ],
    birthday: [
      { required: true, message: '请选择日期', trigger: 'blur' },
    ],
  }

  private saveInfo() {
    this.refUserInfo.validate((valid) => {
      if (valid) {
        updateUserInfo(this.userInfo).then((res: any) => {
          this.$message({type: 'success', message: '更新成功'})
        })
      } else {
        return false;
      }
    });
  }
}
</script>

<style scoped lang="scss">
.info-box {
  max-width: 650px;
  margin: 0 auto;
}
</style>
