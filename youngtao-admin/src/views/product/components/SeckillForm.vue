<template>
  <el-form :model="addForm" :rules="rules" ref="addForm" label-width="80px" class="demo-ruleForm">
    <el-form-item label="商品名称">
      <el-input :value="data.spu.spu" disabled></el-input>
    </el-form-item>
    <el-form-item label="选中属性">
      <el-input :value="Object.values(data.sku.sku).join('，')" disabled></el-input>
    </el-form-item>
    <el-form-item label="原价 (￥)">
      <el-input :value="data.sku.price" disabled></el-input>
    </el-form-item>
    <el-form-item label="现价 (￥)" prop="price">
      <el-input v-model="addForm.price"></el-input>
    </el-form-item>
    <el-form-item label="开始时间" prop="date">
      <el-date-picker v-model="addForm.date" class="w-50" type="date" placeholder="活动时间2小时">
      </el-date-picker>
      <el-select v-model="addForm.hours" placeholder="请选择" class="w-50">
        <el-option v-for="item in [0,6,8,10,12,14,16,18,20,22]" :key="item" :label="item + ' 时'" :value="item">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="商品数量" prop="num">
      <el-input type="number" v-model="addForm.num" min="1"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">立即添加</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Emit, Prop, Ref, Vue } from 'vue-property-decorator';
import { addOrUpdateSku } from '@/api/gsc/sku'

@Component
export default class Form extends Vue {
  @Prop() private data!: any
  @Ref('addForm') private refAddForm: any;

  private addForm: any = {
    price: '',
    date: '',
    hours: '',
    num: ''
  }

  private rules = {
    price: [
      { required: true, message: '请输入价格', trigger: 'blur' },
    ],
    date: [
      { required: true, message: '请选择时间段', trigger: 'blur' },
    ],
    num: [
      { required: true, message: '请输入个数', trigger: 'blur' },
    ],
  }

  private submitForm() {
    this.refAddForm.validate((valid: any) => {
      if (!valid) {
        return;
      }
      this.addForm.skuId = this.data.sku.skuId
      addOrUpdateSku(this.addForm).then((res: any) => {
        this.$message({type: 'success', message: '添加成功'})
        this.refAddForm.resetFields();
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
