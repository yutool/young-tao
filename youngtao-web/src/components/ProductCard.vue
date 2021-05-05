<template>
  <router-link :to="`/product/detail/${spu.spuId}`" class="product-card p-3">
    <div class="product-image">
      <img :src="spu.images[0]" alt="">
    </div>
    <div>
      <div class="product-title-spu">
        <div class="spu-title text-over">{{ spu.title }}</div> 
      </div>
      <div class="product-info">
        <span class="product-price">￥{{ spu.price }}</span>
        <span class="float-right">已售：{{ spu.saleNum }}</span>
      </div>
    </div>
  </router-link>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class ProductCard extends Vue {
  @Prop() private spu: any
}
</script>

<style scoped lang="scss">
.product-card {
  display: block;
  border: 1px solid #e5e5e5;
  color: #000;
  text-decoration: none;
  margin-bottom: 1rem;
  .spu-title {
    color: #666;
    font-size: 14px;
  }
  .product-info {
    position: relative;
    border-top: 1px dashed #e5e5e5;
    margin-top: 12px;
    padding-top: 12px;
    color: #666;
    .product-price {
      color: #FF4466;
    }
  }
  .product-image {
    height: 200px;
    overflow: hidden;
    img {
      width: 100%;
      height: auto;
    }
  }
}

</style>
