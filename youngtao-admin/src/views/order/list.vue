<template>
  <div class="mb-5">
    <!-- 订单列表 -->
    <div class="table-responsive-lg">
      <table class="table order-table">
        <thead class="table-head">
          <tr>
            <th scope="col">
              <el-select v-model="selectedStatus" size="mini" @change="handleChange">
                <el-option v-for="item in options" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </th>
            <th scope="col">订单详情</th>
            <th scope="col">单价</th>
            <th scope="col">数量</th>
            <th scope="col">实付款</th>
            <th scope="col">状态</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <!-- 订单数据 -->
        <template  v-if="orderList.length">
          <tbody v-for="order in orderList" :key="order.orderId" >
            <div style="height: 10px"></div>
            <tr class="bg-light">
              <td colspan="7">
                <span class="mr-3">{{ order.createTime }}</span>
                <span>订单编号：{{ order.orderId }}</span>
              </td>
            </tr>
            <tr v-for="(item, index) in order.orderItem" :key="item.skuId">
              <td colspan="2" class="pointer" @click="$router.push(`/market/detail/${item.spuId}`)">
                <el-col :span="9">
                  <img :src="item.image" alt="" width="70">
                </el-col>
                <el-col :span="15">
                  <div class="item-sku" v-for="(val, key) in item.sku" :key="key">
                    {{ key }}：{{ val }}
                  </div>
                </el-col>
              </td>
              <td> 
                <div>￥{{ item.price }}</div>
                <div class="text-black-50"> <s>￥{{ item.oldPrice }}</s> </div>
              </td>
              <td>
                {{ item.num }}
              </td>
              <td v-if="index == 0" :rowspan="item.length">
                <span class="order-price">￥{{ order.payMoney }}</span>
              </td>
              <td v-if="index == 0" :rowspan="item.length">
                <div class="pb-1 text-primary">
                  <span v-if="order.status==0">已完成</span>
                  <span v-if="order.status==2">待付款</span>
                  <span v-if="order.status==3">待发货</span>
                  <span v-if="order.status==4">待收货</span>
                  <span v-if="order.status==5">待评价</span>
                  <span v-if="order.status==12">已退款</span>
                  <span v-if="order.status==100">交易关闭</span>
                </div>
                <div class="pb-1"><a href="#">订单详情</a></div>
                <div><a href="#">查看物流</a></div>
              </td>
              <td v-if="index == 0" :rowspan="item.length">
                <el-button v-if="order.status==2" type="danger" size="mini">提醒付款</el-button>
                <el-button v-if="order.status==3" type="primary" size="mini">去发货</el-button>
                <el-button v-if="order.status==4" type="primary" size="mini">提醒物流</el-button>
              </td>
            </tr>
          </tbody>
        </template>
        <!-- 无订单显示 -->
        <div v-else class="text-center pt-5">
          暂无商品
        </div>
      </table>
      <!-- 分页 -->
      <el-pagination
        class="text-center"
        @size-change="handleSizeChange"
        @current-change="getMerchantOrder"
        :current-page="pageInfo.pageNum"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getMerchantOrder } from '@/api/omc/order'

@Component
export default class OrderList extends Vue {
  private options = [
    { value: '', label: '全部订单' },
    { value: '0', label: '已完成' },
    { value: '2', label: '待付款' },
    { value: '3', label: '待发货' },
    { value: '12', label: '已退款' },
    { value: '100', label: '关闭' },
  ]
  private pageInfo: any = { size: 10 }  // 分页信息
  private selectedStatus = ''
  private orderList: any = []
  
  // 获取用户的订单
  private getMerchantOrder(page: number, size = this.pageInfo.pageSize) {
    getMerchantOrder({page, size, status: this.selectedStatus}).then((res: any) => {
      this.$log.info('获取用户订单', res)
      this.pageInfo = res.data
      this.orderList = res.data.list
    })
  }
  private mounted() {
    this.getMerchantOrder(0, this.pageInfo.size)
  }
  
  // 切换显示条数
  private handleSizeChange(size: number) {
    this.getMerchantOrder(this.pageInfo.pageNum, size)
  }
  // 切换状态
  private handleChange() {
    this.getMerchantOrder(0, 10)
  }
}
</script>

<style scoped lang="scss">
.table-responsive-lg {
  th {
    min-width: 90px;
  }
}
.order-table {
  font-size: 14px;
  color: #666;
  .table-head th {
    border-top: none !important;
  }
  a {
    color: #666;
    text-decoration: none;
  }
  a:hover { color: #ff5777; }
  .order-price {
    color: #ff5777;
    font-size: 16px;
  }
  .item-sku {
    font-size: 12px;
    color: #999;
  }
}
</style>