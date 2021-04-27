<template>
  <el-form :model="data" :rules="rules" ref="data" label-width="80px" class="demo-ruleForm">
    <el-form-item label="商品名称" prop="spu">
      <el-input v-model="data.spu"></el-input>
    </el-form-item>
    <el-form-item label="商品详情" prop="detail">
      <el-input v-model="data.detail"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">立即保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Emit, Prop, Ref, Vue } from 'vue-property-decorator';
import { updateSpuInfo } from '@/api/gmc/spu'

@Component
export default class Form extends Vue {
  @Prop() private data!: any
  @Ref('data') private refEditForm: any;

  private rules = {
    spu: [
      { required: true, message: '请输入价格', trigger: 'blur' },
    ],
    detail: [
      { required: true, message: '请选择时间段', trigger: 'blur' },
    ],
  }

  private submitForm() {
    this.refEditForm.validate((valid: any) => {
      if (!valid) {
        return;
      }
      updateSpuInfo(this.data).then((res: any) => {
        this.$message({type: 'success', message: '修改成功'})
        this.refEditForm.resetFields();
        this.confirm();
      })
    })
  }

  @Emit('confirm')
  private confirm() {
    // ...
  }
}
</script>

<style scoped lang="scss">

</style>
