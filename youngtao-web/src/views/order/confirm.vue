<template>
  <div class="container mb-5" v-loading.fullscreen="loading" element-loading-text="订单创建中，请稍后">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="2" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <!-- 添加收货人 -->
    <div class="pb-3">
      <div style="font-weight: 600" class="mb-2">选择收货地址</div>
      <el-row :gutter="20">
        <el-col :md="6" v-for="addr in userAddress" :key="addr.id">
          <div class="addr-card" :class="{'active': addr.isDefault}" @click="changeAddr(addr.id, $event)">
            <div class="addr-header">
              {{ addr.consignee }}
            </div>
            <div class="addr-content">
              <div>{{ addr.address }}</div>
              <div>{{ addr.detail }} {{ addr.postcode }}</div>
              <div>{{ addr.telephone }}</div>
            </div>
          </div>
        </el-col>
        <el-col :md="6">
          <el-button icon="el-icon-plus" class="add-btn" circle
            @click="goAddAddr">
          </el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 商品列表 -->    
    <div class="table-responsive-lg">
      <div style="font-weight: 600" class="mb-2">确认商品信息</div>
      <div class="pb-1">
        <table class="table">
          <thead>
            <th>商品</th>
            <th>商品信息</th>
            <th>单价(元)</th>
            <th>数量</th>
            <th>优惠</th>
            <th>小计(元)</th>
          </thead>
          <div style="height: 10px"></div>
          <!-- 数据 -->
          <tbody v-for="merchant in orderList" :key="merchant.merchatId">
            <tr class="bg-light">
              <td  colspan="6">
                <i class="el-icon-shopping-bag-1"></i> {{merchant.shopName}}
              </td>
            </tr>
            <tr v-for="item in merchant.skuList" :key="item.skuId">
              <td> 
                <img :src="item.image" width="30px" alt="图片"> 
                {{ item.spu }}
              </td>
              <td>
                <div v-for="key in Object.keys(item.sku)" :key="key">
                  <span class="table-sku">{{ key }}: {{ item.sku[key] }} </span>
                </div>
              </td>
              <td> <span> {{ item.price }} </span> </td>
              <td> <span> x {{ item.count }} </span> </td>
              <td> 无 </td>
              <td> <span> {{ item.price * item.count }} </span> </td>
            </tr>
            <tr>
              <td colspan="6">
                <span class="text-muted">备注：</span>
                <input v-model="merchant.remark" class="remark" placeholder="补充填写其他信息，如有快递不到也请留言">
              </td>
            </tr>
            <div style="height: 20px"></div>
          </tbody>
        </table>
      </div>
    </div>
    <!-- 确认购买 -->
    <div class="buy-paybar clearfix">
      <div class="float-left">
        <el-link style="line-height: 40px" @click="$router.go(-1)">
          <i class="el-icon-arrow-left pl-2"></i> 返回
        </el-link>
      </div>
      <div class="float-right">
        <span class="pr-3">
          共有 <span style="color: #ff5777;">{{ skuCount }}</span> 件商品，
          共计<span style="color: #ff5777; font-size: 20px">￥{{ payMoney }}</span>
        </span>
        <el-button type="primary" @click="createOrder">确认并付款</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'
import { createOrder, queryStatus } from '@/api/omc/order'
import { gscCreateOrder } from '@/api/gsc/order'
import { gscConfirmOrder } from '@/api/gsc/product'
import { confirmOrder } from '@/api/gmc/product'
import { getUserAddress } from '@/api/uac/address'

@Component
export default class Buy extends Vue {
  private loading = false
  private queueTimer: any
  private currentOrder: any = {} // 当前订单信息

  private userAddress: any = [] // 收货地址
  private checkedAddress: any   // 选中的收货地址

  private orderList: any = []
  private skuCount = 0
  private payMoney = 0

  // 确认订单
  private confirmOrder() {
    this.currentOrder = this.$utils.getOrderItem(this.$route.params.id);
    if (this.currentOrder.type === this.$orderType.NORMAL) {
      confirmOrder({skuList: this.currentOrder.skuList}).then((res: any) => {
        if (res.data == null) {
          this.$router.back()
        }
        // 设置备注属性
        this.orderList = res.data
        this.calculate()
      })
    } else {
      gscConfirmOrder({skuId: this.currentOrder.skuList[0].skuId}).then((res) => {
        const merchant = res.data
        this.orderList = [merchant]
        this.calculate()
      })
    }
  }
  // 计算
  private calculate() {
    for (const merchant of this.orderList) {
      merchant.remark = ''
    }
    // 计算价格
    for (const merchant of this.orderList) {
      for (const sku of merchant.skuList) {
        this.skuCount++;
        this.payMoney += sku.price * sku.count
      }
    }
  }
  // 获取用户收货地址
  private getUserAddress() {
    getUserAddress().then((res: any) => {
      this.$log.info('获取用户收货地址', res)
      this.userAddress = res.data
      // 设置默认地址
      for (const addr of this.userAddress) {
        if (addr.isDefault) {
          this.checkedAddress = addr.shippingAddrId
          break
        }
      }
    })
  }
  private mounted() {
    this.confirmOrder()
    this.getUserAddress()
  }

  // 创建订单，在这之前需要添加收货地址
  private createOrder() {
    if (!this.checkedAddress) {
      this.$message({ type: 'info', message: '请添加收货地址' })
      return
    }
    
    // 创建订单
    if (this.currentOrder.type === this.$orderType.NORMAL) {
      // 普通订单
      const orderList = []
      for (const merchant of this.orderList) {
        const orderItem = []
        for (const sku of merchant.skuList) {
          orderItem.push({ skuId: sku.skuId, count: sku.count })
        }
        orderList.push({ orderItem, remark: merchant.remark })
      }
      const data = { orderList, shippingAddressId: this.checkedAddress, isCart: this.currentOrder.isCart }
      createOrder(data).then((res: any) => {
        this.checkOrderStatus(res.data)
      })
    } else {
      // 秒杀订单
      const data = {
        skuId: this.orderList[0].skuList[0].skuId,
        remark: this.orderList[0].remark,
        shippingAddressId: this.checkedAddress,
        isCart: this.currentOrder.isCart
      }
      gscCreateOrder(data).then((res: any) => {
        this.checkOrderStatus(res.data)
      })
    }
  }
  // 判断是否生成了订单
  private checkOrderStatus(paymentId: string) {
    this.loading = true
    this.queueTimer = setInterval(() => {
      queryStatus(paymentId).then((res: any) => {
      if (res.data && res.data === this.$orderStatus.PAYMENT) {
        this.loading = false
        if (this.queueTimer) {
          clearInterval(this.queueTimer)
        }
        this.$router.push(`/order/pay/${paymentId}`)
      } else if (res.data && res.data === this.$orderStatus.FAILED) {
        this.$message({type: 'info', message: '订单创建失败'})
        this.loading = false
        if (this.queueTimer) {
          clearInterval(this.queueTimer)
        }
      }
    });
    }, 2000)
  }

  // 选择收货地址
  private changeAddr(id: string, e: any) {
    // 调整类
    const $addr = $(e.currentTarget)
    $('.addr-card').removeClass('active')
    $addr.addClass('active')
    // 设置收货地址
    this.checkedAddress = id
  }
  // 前往设置地址页
  private goAddAddr() {
    const routes = this.$router.resolve(`/user/address`)
    window.open(routes.href, '_blank')
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
  .remark {
    font-size: 14px;
    width: 360px;
    color: #666;
    border: none;
    outline: none;
  }
}
.addr-card {
  box-shadow: 0 .125rem .25rem rgba(0,0,0,.075);
  border-radius: 5px;
  padding: 10px;
  height: 160px;
  margin-bottom: 5px;
  cursor: pointer;
  .addr-header {
    padding-bottom: 5px;
    border-bottom: 1px solid #ccc;
    font-size: 1.2rem;
    font-weight: 500;
  }
  .addr-content {
    color: #666;
  }
}
.buy-paybar {
  border: 1px solid rgb(212, 212, 212);
}
.add-btn {
  margin: 50px 20px;
  font-size: 30px;
}
.active {
  background: rgb(255, 219, 225)
}
</style>
