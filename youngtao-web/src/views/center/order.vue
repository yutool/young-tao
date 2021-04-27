<template>
  <div class="mb-5">
    <!-- 订单列表 -->
    <div v-if="orderList.length" class="table-responsive-lg">
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
            <th scope="col">操作</th>
            <th scope="col">实付款</th>
            <th scope="col">状态</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <!-- 订单数据 -->
        <tbody v-for="order in orderList" :key="order.orderId" >
          <div style="height: 10px"></div>
          <tr class="bg-light">
            <td colspan="8">
              <span class="mr-3">{{ order.createTime }}</span>
              <span>订单编号：{{ order.orderId }}</span>
              <span class="float-right">
                <el-popconfirm v-if="order.status==0 || order.status==12 || order.status==100" 
                    title="确认删除订单吗" @onConfirm="deleteOrder(order.orderId)">
                  <i class="el-icon-delete pointer" slot="reference"></i>
                </el-popconfirm>
              </span>
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
              <div class="pb-1 pointer" v-if="order.status==3" @click="orderRefund(order.orderId)">退款</div>
              <div class="pb-1 pointer">投诉卖家</div>
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
              <el-button v-if="order.status==2" type="danger" size="mini" @click="$router.push(`/order/pay/${order.paymentId}`)">去付款</el-button>
              <el-button v-if="order.status==3" type="primary" size="mini">提醒发货</el-button>
              <el-popconfirm v-if="order.status==4"  title="是否确认收货" @onConfirm="acceptOrder(order.orderId)" >
                  <el-button slot="reference" type="primary" size="mini">确认收货</el-button>
                </el-popconfirm>
              <el-button v-if="order.status==0 || order.status==12 || order.status==100" type="info" size="mini" @click="$router.push(`/market/detail/${item.spuId}`)">再来一单</el-button>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 分页 -->
      <el-pagination
        class="text-center"
        @size-change="handleSizeChange"
        @current-change="getUserOrder"
        :current-page="pageInfo.pageNum"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
    </div>
    
    <!-- 无订单显示 -->
    <div v-else class="text-center pt-5">
      你还没有购买过商品哦~~~
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getUserOrder, orderRefund, deleteOrder, acceptOrder } from '@/api/omc/order'

@Component
export default class List extends Vue {
  private options = [
    { value: '', label: '全部订单' },
    { value: '0', label: '已完成' },
    { value: '2', label: '代付款' },
    { value: '3', label: '代发货' },
    { value: '12', label: '已退款' },
    { value: '100', label: '关闭' },
  ]
  private pageInfo: any = { size: 10 }  // 分页信息
  private selectedStatus = ''
  private orderList: any = []
  
  // 获取用户的订单
  private getUserOrder(page: number, size = this.pageInfo.pageSize) {
    getUserOrder({page, size, status: this.selectedStatus}).then((res: any) => {
      this.$log.info('获取用户订单', res)
      this.pageInfo = res.data
      this.orderList = res.data.list
    })
  }
  private mounted() {
    this.getUserOrder(0, this.pageInfo.size)
  }
  
  // 切换显示条数
  private handleSizeChange(size: number) {
    this.getUserOrder(this.pageInfo.pageNum, size)
  }
  // 切换状态
  private handleChange() {
    this.getUserOrder(0, 10)
  }
  // 退款
  private orderRefund(orderId: string) {
    orderRefund({orderId, refundReason: '退款'}).then((res: any) => {
      this.$message({type: 'success', message: '退款成功'})
      this.getUserOrder(this.pageInfo.pageNum, this.pageInfo.size)
    })
  }
  // 删除订单
  private deleteOrder(id: string) {
    deleteOrder({id}).then((res: any) => {
      this.$log.info('删除订单：', res)
      this.getUserOrder(this.pageInfo.pageNum, this.pageInfo.size)
    })
  }
  // 确认收货
  private acceptOrder(id: string) {
    acceptOrder({id}).then((res: any) => {
      this.$message({type: 'success', message: '确认收货成功'})
      this.getUserOrder(this.pageInfo.pageNum, this.pageInfo.size)
    })
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