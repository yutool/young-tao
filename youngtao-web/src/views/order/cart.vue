<template>
  <div class="container mb-5" v-loading.fullscreen="loading">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="1" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <!-- 购物车列表-->
    <div v-if="cartList.length">
      <!-- 购物车列表 -->
      <div class="table-responsive-lg">
        <table class="table table-borderless">
          <thead class="bg-light">
            <tr>
              <th scope="col">
                <el-checkbox v-model="checkedAll" @change="handlerAll">全选</el-checkbox>
              </th>
              <th scope="col">商品</th>
              <th scope="col">商品信息</th>
              <th scope="col">单价（元）</th>
              <th scope="col">数量</th>
              <th scope="col">小计（元）</th>
              <th scope="col">操作</th>
            </tr>
          </thead>
          <br />
          <tbody v-for="merchant in cartList" :key="merchant.merchantId">
            <!-- 店名 -->
            <tr class="border mt-3 bg-light">
              <th scope="row">
                <el-checkbox v-model="checkedMerchant[merchant.merchantId]" @change="handlerMerchant"></el-checkbox>
              </th>
              <td colspan="6">
                <i class="el-icon-shopping-bag-1"></i> {{merchant.shopName}}
              </td>
            </tr>
            <!-- 商品 -->
            <tr class="border" v-for="cart in merchant.skuList" :key="cart.skuId">
              <th scope="row">
                <el-checkbox v-model="checkedCart[cart.skuId]" @change="handlerCart"></el-checkbox>
              </th>
              <td @click="$router.push(`/product/detail/${cart.spuId}`)" class="pointer">
                <img :src="cart.image" width="60px" alt="">
                {{ cart.spu }}
              </td>
              <td>
                <div v-for="key in Object.keys(cart.sku)" :key="key">
                  <span class="table-sku">{{ key }}: {{ cart.sku[key] }} </span>
                </div>
              </td>
              <td>
                <div> ￥{{ cart.price }} </div>
                <div> <s class="text-muted">￥{{ cart.oldPrice }}</s>  </div>
              </td>
              <td>
                <el-input-number
                  :min="1" :max="999" :precision="0"
                  size="mini" 
                  v-model="cart.num" 
                  @change="changeNum(cart)">
                </el-input-number>
              </td>
              <td>
                <span class="table-price"> ￥{{ cart.num * cart.price }} </span>
              </td>
              <td>
                <el-link type="danger" @click="deleteCart(cart.id)">删除</el-link> <br/>
                <el-link type="primary">移入收藏夹</el-link>
              </td>
            </tr>
            <div style="height: 10px"></div>
          </tbody>
        </table>
      </div>
      <!-- 状态栏 -->
      <div class="cart-paybar clearfix">
        <div class="float-left pt-1 ">
          <el-checkbox v-model="checkedAll" @change="handlerAll">全选</el-checkbox>
          <span class="action-btn" @click="batchDelete">删除选中</span>
          <span class="action-btn">清空售馨商品</span>
          <span class="action-btn">移入收藏夹</span>
        </div>
        <div class="float-right">
          <span> 总共 <span style="color: #ff5777;">{{ checkedNum }} </span> 件商品，</span> 
          <span> 共计:<span style="color: #ff5777; font-size: 20px">￥{{ checkedMoney }}</span> 元 </span> 
          <el-button class="ml-2" type="danger" @click="buy" size="small">立即购买</el-button>
        </div>
      </div>
    </div>
    <!-- 空购物车 -->
    <div v-else class="text-center pt-5">
      购物车空空如也~~~
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter, State } from 'vuex-class'
import { getUserCart, updateNum, deleteCart, batchDelete } from '@/api/omc/cart'
import { createOrder } from '@/api/omc/order'

@Component
export default class Cart extends Vue {
  @State((state: any) => state.app.loading) private loading: any
  
  private cartList: any = []
  private checkedAll = false
  private checkedMerchant: any = {}
  private checkedCart: any = {}

  private checkedNum = 0;   // 选中个数
  private checkedMoney = 0  // 总金额
  
  // 获取购物车
  private getUserCart() {
    getUserCart().then((res: any) => {
      this.cartList = res.data
      this.$log.info('查询购物车', this.cartList)
    })
  }
  private mounted() {
    this.getUserCart()
  }

  // 立即购买
  private buy() {
    if (this.checkedNum === 0) {
      this.$message({type: 'info', message: '暂无该商品，换个吧'})
      return;
    }
    // 处理商品
    const skuList = []
    for (const merchant of this.cartList) {
      for (const sku of merchant.skuList) {
        if (this.checkedCart[sku.skuId]) {
          skuList.push({skuId: sku.skuId, count: sku.num})
        }
      }
    }
    // 存入cookie
    const key = this.$utils.setOrderItem({type: this.$orderType.NORMAL, skuList, isCart: true});
    this.$router.push(`/order/confirm/${key}`)
  }

  // 修改商品数量
  private changeNum(cart: any) {
    updateNum({id: cart.id, num: cart.num}).then((res: any) => {
      this.$log.info('更新购物车数量', res)
    })
  }

  // 删除购物车商品
  private deleteCart(id: number) {
    deleteCart({id}).then((res: any) => {
      this.getUserCart()
    })
  }

  // 删除选择商品
  private batchDelete() {
    const cartIds: any = []
    for (const merchant of this.cartList) {
      for (const item of merchant.skuList) {
        if (this.checkedCart[item.skuId]) {
          cartIds.push(item.id)
        }
      }
    }
    if (cartIds.length !== 0) {
      batchDelete({ids: cartIds}).then((res: any) => {
        this.getUserCart()
      })
    }
  }

  // 处理复选框点击事件
  private handlerMerchant() {
    let flag = true
    for (const merchant of this.cartList) {
      // 如果被选中，则选中下面所有商品
      if (this.checkedMerchant[merchant.merchantId]) {
        for (const sku of merchant.skuList) {
          this.checkedCart[sku.skuId] = true
        }
      } else {
        for (const sku of merchant.skuList) {
          this.checkedCart[sku.skuId] = false
        }
        flag = false
      }
    }
    this.checkedAll = flag
    this.calculate()
  }
  private handlerCart() {
    let allFlag = true
    for (const merchant of this.cartList) {
      let flag = true
      for (const sku of merchant.skuList) {
        // 如果有一个没有被选中，则取消
        if (!this.checkedCart[sku.skuId]) {
          allFlag = flag = false;
          break;
        }
      }
      this.checkedMerchant[merchant.merchantId] = flag
    }
    this.checkedAll = allFlag
    this.calculate()
  }
  private handlerAll() {
    for (const merchant of this.cartList) {
      this.checkedMerchant[merchant.merchantId] = this.checkedAll
    }
    this.handlerMerchant()
  }
  // 计算选中件数
  private calculate() {
    this.checkedNum = 0;
    this.checkedMoney = 0;
    for (const merchant of this.cartList) {
      for (const sku of merchant.skuList) {
        if (this.checkedCart[sku.skuId]) {
          this.checkedNum++;
          this.checkedMoney += sku.price * sku.num;
        }
      }
    }
  }
}
</script>

<style scoped lang="scss">
.table-responsive-lg {
  th:not(:first-child) {
    min-width: 150px;
  }
  .table-sku {
    line-height: 20px;
    color: #666;
    font-size: 13px;
  }
  .table-price {
    color: #ff5777;
    font-size: 20px;
  }
}
.cart-paybar {
  box-shadow: 0 .125rem .25rem rgba(0,0,0,.1);
  padding: 5px;
  .paybar-list {
    display: inline;
    margin: 0;
    padding: 10px;
    li {
      list-style: none;
      display: inline;
      padding-right: 5px;
    }
  }
}
.action-btn {
  font-size: 14px;
  margin-left: 10px;
  cursor: pointer;
}
.action-btn:hover {
  color: #ff5777;
}
</style>
