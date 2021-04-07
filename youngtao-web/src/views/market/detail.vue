<template>
  <div class="container pt-3">
    <el-row :gutter="20">
      <!-- 照片墙 -->
      <el-col :md="12">
        <div class="sku-images">
          <el-carousel height="450px">
            <el-carousel-item v-for="item in checkedSku.images" :key="item">
              <img :src="item" alt="">
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <!-- 选择商品 -->
      <el-col :md="12">
        <!-- 标题 -->
        <div class="sku-title">
          <el-tag v-if="checkedSku.type == 0" type="info" size="mini">缺货中</el-tag>
          <el-tag v-if="checkedSku.type == 1" size="mini">新品</el-tag>
          <el-tag v-if="checkedSku.type == 2" type="danger" size="mini">活动中</el-tag>
          {{ checkedSku.title || '赶紧让掌柜补货吧' }}
        </div>
        <!-- 价格 -->
        <div class="summary-price-wrap">
          <div class="sku-price">
            <span>
              优惠价：<span class="price">￥{{ checkedSku.price || 0 }}</span>
            </span>
            <s>原价：￥{{ checkedSku.oldPrice || 0 }} </s>
          </div>
          <div class="sku-sales">
            <small class="pr-3">累计评价: {{ spu.commentNum || 0 }}</small>
            <small>累计销售：{{ spu.saleNum || 0 }}</small>
          </div>
        </div>
        <!-- SKU -->
        <el-form ref="form" label-width="80px" label-position="left" class="sku-form">
          <el-form-item v-for="(value, name) in spu.skuTemplate" :key="name"  :label="name" >
            <el-radio-group v-model="checkedObj[name]" >
              <el-radio v-for="item in value" :key="item" :label="item" @change="calculate()">
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <!-- 商品数量 -->
        <div class="sku-amount">
          <span class="pr-3">
            <span class="sku-form-lable"> 数量：</span>
            <el-input-number :min="1" :max="checkedSku.num || 0" :precision="0" size="mini" v-model="selctedNum">
            </el-input-number>
          </span>
          <small class="text-muted">剩余库存：{{ checkedSku.num || 0 }}</small>
        </div>
        <!-- 操作按钮 -->
        <div class="sku-buy">
          <el-button type="success" @click="buy" plain>立即购买</el-button>
          <el-button type="primary" @click="addCart" plain :disabled="checkedSku.type == 2">加入购物车</el-button>
        </div>
        <div class="sku-serve">
          <span class="sku-form-lable">服务说明：</span>
          {{ spu.serve ? spu.serve : '暂无' }}
        </div>
        <div class="extra-pay">
          <span class="sku-form-lable">支付方式：</span>
          <div class="fl list list-nomaibei"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'
import { getProduct } from '@/api/gmc/product'
import { addCart } from '@/api/omc/cart'

@Component
export default class GoodsDetail extends Vue {
  private spu: any = {}
  private skuList: any = []
  private checkedObj: any = {}  // 选中的属性
  private selctedNum = 1;
  private checkedSku: any = {};

  private mounted() {
    this.getProduct()
  }

  // 初始化商品信息
  private getProduct() {
    getProduct(this.$route.params.id).then((res: any) => {
      this.$log.info('获取商品', res)
      this.spu = res.data
      this.skuList = res.data.skuList
      // 默认选中
      const skuId = this.$route.query.skuId;
      if (skuId) { for (const item of this.skuList) {
        if (item.skuId === skuId) {
          this.checkedSku = item
          break;
        }
      }}
      if (JSON.stringify(this.checkedSku) === '{}') {
        this.checkedSku = this.skuList[0]
      }
      this.checkedObj = this.$utils.copyOf(this.checkedSku.sku)
    })
  }
 
  // 加入购物车
  private addCart() {
    if (!this.checkedSku.skuId) {
      this.$message({type: 'info', message: '暂无该商品，换个吧'})
      return;
    }
    addCart({skuId: this.checkedSku.skuId, num: this.selctedNum}).then((res: any) => {
      this.$message({ type: 'success', message: '加入购物车成功' })
      this.$log.info('加购', res)
    })
  }
  // 购买
  private buy() {
    if (!this.checkedSku.skuId) {
      this.$message({type: 'info', message: '暂无该商品，换个吧'})
      return;
    }
    // 存入cookie
    const order = {
      type: this.checkedSku.type,
      skuList: [{ skuId: this.checkedSku.skuId, count: this.selctedNum }],
      isCart: false
    }
    const key = this.$utils.setOrderItem(order);
    this.$router.push(`/order/confirm/${key}`)
  }

  // 计算选中的sku
  private calculate() {
    for (const item of this.skuList) {
      if (JSON.stringify(item.sku) === JSON.stringify(this.checkedObj)) {
        this.checkedSku = item;
        return
      }
    }
    // 没有库存
    this.checkedSku = {
      images: this.spu.images,
      type: 0
    }
  }
}
</script>

<style scoped lang="scss">
.sku-images {
  text-align: center;
  padding: 10px 30px;
  img {
    width: 90%;
  }
}
.sku-title {
  font: 700 16px Arial,"microsoft yahei";
  color: #666;
  padding-top: 10px;
  line-height: 28px;
  margin-bottom: 5px;
}
.summary-price-wrap {
  padding: 10px;
  background: #f3f3f3;
  .sku-price {
    padding-bottom: 10px;
    .price {
      color: #e4393c;
      font-size: 20px;
      font-weight: 700;
      padding-right: 20px;
    }
  }
}
.sku-form {
  padding: 10px 0;
  .el-form-item {
    margin: 0;
  }
}
.sku-amount {
  padding-bottom: 10px;
}
.sku-buy {
  padding-bottom: 10px;
}
.sku-serve {
  padding-bottom: 10px;
}
.extra-pay  {
  .list-nomaibei {
    background-position: -37px -295px;
  }
  .list {
    background-image: url(https://s10.mogucdn.com/p2/170122/117603130_6a8jgjh899bh5jd6hcia3ehhj0113_221x704.png);
    background-position: -10px -290px;
    display: inline-block;
    width: 131px;
    height: 25px;
  }
}
.sku-form-lable {
  font-size: 14px;
  color: #606266;
}
</style>
  