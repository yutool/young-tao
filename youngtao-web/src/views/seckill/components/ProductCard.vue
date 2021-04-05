<template>
  <div class="product-card shadow-sm" @click="productDetail(product.spuId, product.skuId)">
    <div class="product-img">
      <img :src="product.image" alt="">
    </div>
    <div class="product-title">
      <el-tag type="danger" size="mini">活动中</el-tag>
      {{ product.title }}
    </div>
    <el-row>
      <el-col :span="15">
        <div class="product-price-box">
          <span class="product-price">￥{{ product.price }}</span>
          <span> <s>{{ product.oldPrice }}</s> </span>
        </div>
      </el-col>
      <el-col :span="9">
        <el-button size="small" type="primary" @click="productDetail(product.spuId, product.skuId)" class="float-right">
          立即抢购
        </el-button>
      </el-col>
    </el-row>
    <div class="mt-2">
      <el-progress :percentage="10" :stroke-width="5" :format="percentage => `售${percentage}%`"></el-progress>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator'

@Component
export default class ProductCard extends Vue {
  @Prop() private product: any
  @Prop() private time: any
  
  private productDetail(spuId: string, skuId: string) {
    this.$router.push(`/market/detail/${spuId}?skuId=${skuId}`)
  }
}
</script>

<style scoped lang="scss">
.product-card {
  cursor: pointer;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 20px;
  background-color: #fff;
  
  .product-img {
    text-align: center;
    height: 200px;
    img {
      height: 100%;
    }
  }
  .product-title {
    color: #666;
    font-size: 14px;
    padding: 10px 0;
  }
  .product-price {
    color: #f46;
    font-size: 18px;
    display: inline-block;
    margin-right: 10px;
    margin-bottom: 3px;
  }
}
</style>
