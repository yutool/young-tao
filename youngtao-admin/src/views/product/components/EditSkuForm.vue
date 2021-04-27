<template>
  <el-form :model="data" :rules="rules" ref="data" label-width="80px" class="demo-ruleForm">
    <el-form-item label="商品属性" >
      <el-input :value="Object.values(data.sku).join('，')" disabled></el-input>
    </el-form-item>
    <el-form-item label="原价 (￥)" prop="oldPrice">
      <el-input v-model="data.oldPrice"></el-input>
    </el-form-item>
    <el-form-item label="现价 (￥)" prop="price">
      <el-input v-model="data.price"></el-input>
    </el-form-item>
    <el-form-item label="商品库存" prop="num">
      <el-input type="number" min="1" v-model="data.num"></el-input>
    </el-form-item>
    <el-form-item label="预警库存" prop="alertNum">
      <el-input type="number" min="1" v-model="data.alertNum"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">立即保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Emit, Prop, Ref, Vue } from 'vue-property-decorator';
import { updateSkuInfo } from '@/api/gmc/sku'

@Component
export default class Form extends Vue {
  @Prop() private data!: any
  @Ref('data') private refEditForm: any;

  private rules = {
    oldPrice: [
      { required: true, message: '请输入价格', trigger: 'blur' },
    ],
    price: [
      { required: true, message: '请输入价格', trigger: 'blur' },
    ],
    num: [
      { required: true, message: '请输入库存', trigger: 'blur' },
    ],
    alertNum: [
      { required: true, message: '请输入预警库存', trigger: 'blur' },
    ],
  }

  private submitForm() {
    this.refEditForm.validate((valid: any) => {
      if (!valid) {
        return;
      }
      updateSkuInfo(this.data).then((res: any) => {
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
