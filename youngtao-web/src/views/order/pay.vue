<template>
  <div class="container">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="3" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <!-- 订单信息 -->
    <div class="mb-3">
      <div class="mb-1">订单提交成功，请您及时付款，以便尽快为您发货~</div>
      <small>
        请您在半小时之内完成支付，超时订单会自动取消，订单金额: <span class="money">￥{{ payRecord.money }}</span> 
      </small>
    </div>
    <!-- 二维码 -->
    <div class="pay-card">
      <div class="pay-header">
        <ul>
          <li @click="alipay" :class="{'active' : payType == 1}">支付宝支付</li>
          <li @click="wxpay" :class="{'active' : payType == 2}">微信支付</li>
        </ul>
      </div>
      <div class="pay-content">
        <div v-if="payType == 0" class="pt-5 pb-5">
          请选择支付方式
        </div>
        <div v-if="payType == 1" class="pt-5 pb-5">
          正在为您跳转，请稍后...
        </div>
        <el-row v-if="payType == 2">
          <el-col :span="9">
            <vue-qrious v-if="wxCodeUrl" :value="wxCodeUrl" :padding="10" :size="200" />
            <div v-else class="mt-5">加载中...</div>
          </el-col>
          <el-col :span="15">
            <div>
              <div class="pay-hint">
                <span>请使用微信扫描二维码</span> <br/>
                <span>已完成支付</span>
              </div>
              <div class="pay-helper">
                <span>详细 <a href="#">使用帮助</a></span> <br/>
                <span>如果支付遇到问题，可 <a href="#">联系客服</a></span>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 一些提示信息 -->
    <div class="hint">
      <h6>重要说明</h6>
      <ul>
        <li>聚美购物商城支付平台目前支持<a href="https://opendocs.alipay.com/open/200/105311">支付宝沙箱环境</a>。</li>
        <li>它支付渠道正在调试中，敬请期待。</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getPayOrder } from '@/api/opc/payRecord'
import { weixinPay } from '@/api/opc/wxpay'
import { aliWebPay } from '@/api/opc/alipay'
import VueQrious from 'vue-qrious'

@Component({
  components: {
    VueQrious
  }
})
export default class Pay extends Vue {
  private payType = 0; // 1支付宝，2微信
  private payRecord: any = {}

  private payTimer: any
  private wxCodeUrl = null

  private mounted() {
    this.getPayOrder()
  }

  // 获取订单
  private getPayOrder() {
    getPayOrder(this.$route.params.id).then((res: any) => {
      if (!res.data) {
        this.$router.go(-1)
      }
      this.$log.info('pay查询订单', res)
      this.payRecord = res.data
    })
  }

  // 支付宝支付
  private alipay() {
    this.payType = 1
    const data = {
      paymentId: this.payRecord.paymentId,
      subject: '聚美订单',
      body: `聚美订单 ${this.payRecord.paymentId}`
    }
    aliWebPay(data).then((res: any) => {
      const div = document.createElement('div')
      div.innerHTML = res.data
      document.body.appendChild(div)
      document.forms[0].submit()
    })
  }
  
  // 申请微信支付
  private wxpay() {
    this.$message({type: 'info', message: '暂不支持微信支付'})
    // this.payType = 2
    // weixinPay(this.order).then((res: any) => {
    //   this.$log.info('申请支付', res)
    //   this.wxCodeUrl = res.data.code_url
    // })
  }

  // 检查订单状态
  private checkPayStatus() {  
    this.payTimer = setInterval(() => {
      getPayOrder(this.$route.params.id).then((res: any) => {
        if (res.code !== 0) {
          window.clearInterval(this.payTimer)
        } else if (res.data.status === 4) {
          // 4表示付款成功-待发货        
          this.$log.info('订单状态：', '支付成功')
          window.clearInterval(this.payTimer)
          this.$router.push({name: 'pay_success', params: { order: res.data }})
        } else if (res.data.status === 3 || res.data.status === 0) {
          // 3-支付失败 0-订单关闭
          this.$log.info('订单状态：', '支付失败')
          window.clearInterval(this.payTimer)
          this.$router.push({name: 'pay_fail', params: { order: res.data }})
        }
      })
    }, 2000)
  }
  
  private beforeDestroy() {
    if (this.payTimer) {
      window.clearInterval(this.payTimer)
    }
  }
}
</script>

<style scoped lang="scss">
.active {
  background-color: #f9f9f9;
}
.money {
  font-size: 16px;
  color: #FF4466;;
}
.pay-card {
  border: 2px solid #f6f6f6;
  margin-bottom: 1rem;
  .pay-header {
    ul {
      margin: 0;
      padding: 0;
      li {
        list-style: none;
        display: inline-block;
        padding: 10px 10px;
        border: 1px solid #f6f6f6;
        cursor: pointer;
      }
    }
  }
  .pay-content {
    text-align: center;
    padding: 20px;
    background: #f9f9f9;
    .pay-hint {
      color: #999;
      font: 700 20px/1.4 Microsoft YaHei;
      padding: 30px 0;
    }
    .pay-helper {
      font-size: 14px;
      color: #666;
    }
  }
}
</style>
