<template>
	<view class="container">
		<u-navbar title="收银台" :border-bottom="false">
			<!-- #ifndef MP -->
				<view class="content" slot="right">
					<view class="mr-3">订单中心</view>
				</view>
			<!-- #endif -->
		</u-navbar>
		<!-- 付款金额 -->
		<view class="price-wrap">
			<view class="price">￥{{orderPayRecord.money}}</view>
			<view class="time">支付剩余时间：00:29:10</view>
		</view>
		<!-- 支付类型 -->
		<view class="pay-type-wrap">
			<view class="type-box b-b" @click="payType = 1">
				<view class="icon iconfont icon-qiandai"></view>
				<view class="content">
					<view class="title">支付宝支付</view>
					<view class="hint">推荐使用支付宝支付</view>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType === 1'></radio>
				</label>
			</view>
			<view class="type-box b-b" @click="payType = 2">
				<view class="icon iconfont icon-qiandai"></view>
				<view class="content">
					<view class="title">微信支付</view>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType === 2'></radio>
				</label>
			</view>
			<view class="type-box" @click="payType = 3">
				<view class="icon iconfont icon-qiandai"></view>
				<view class="content">
					<view class="title">樱桃钱包</view>
					<view class="hint">可用余额 ¥198.5</view>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType === 3'></radio>
				</label>
			</view>
		</view>
		<!-- 确认支付 -->
		<view class="paybar-wrap">
			<button class="pay-btn" @click="handleClick">确认支付 ￥{{orderPayRecord.money}}</button>
		</view>
	</view>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { getByPaymentId } from '@/api/opc/orderPayRecord.js';
import { wxAppPay } from '@/api/opc/wxpay.js';
import { aliAppPay, aliPayCheck } from '@/api/opc/alipay.js';

@Component
export default class CreateOrder extends Vue {
	private payType = 1;
	private orderPayRecord = {};
	private payId;
	
	onLoad(option) {
		this.payId = option.payId
		if (!this.payId) return;
		getByPaymentId(this.payId).then(res => {
			if (res.data == null) return;
			this.orderPayRecord = res.data;
		})
	}
	
	private handleClick() {
		if (this.payType == 1) {				// 支付宝
			const alipay = uni.requireNativePlugin('JY-ALIPAY')
			aliAppPay({'paymentId': this.payId, 'body': '樱桃订单：xxx', 'subject': 'subject'}).then(res => {
				alipay.show({ // 发起支付
					if_sanbox: true, 
					auto_create_order_info: false,
					order_info: res.data
				}, result => {
					if (result.resultStatus == '9000') {
						const obj = JSON.parse(result.result)
						const { out_trade_no } = obj.alipay_trade_app_pay_response
						aliPayCheck({paymentId: out_trade_no}).then(res => {
							if (res.data) {
								uni.showModal({ content: '支付成功', showCancel: false})
							}
						})
					} else if (result.resultStatus == '5002555') {
						uni.showModal({ content: '支付sdk出错', showCancel: false });
					} else {
						uni.showModal({ content: result.memo, showCancel: false });
					}
				});
			})
		} else if (this.payType == 2) {	// 微信支付
			wxAppPay({'paymentId': this.payId, 'body': '樱桃订单：xxx'}).then(res => {
				uni.requestPayment({
					provider: wxpay,
					orderInfo: res.data,
					success: res => {
						uni.showModal({ content: '支付成功', showCancel: false})
					},
					fail: err => {
						uni.showModal({ content: `支付失败: ${err.errMsg || ''}`, showCancel: false });
					},
					complete: () => {
						// this.submitting = false;
					}
				});
			})
		} else if (this.payType == 3) {	// 樱桃支付
		}
	}
}
</script>

<style lang="scss" scoped>
$paybar-height: 115rpx;
.container {
	margin-bottom: calc(130rpx);
}
.b-b {
	border-bottom: 1px solid #f6f6f6;
}
.price-wrap {
	margin-bottom: $module-margin;
	background-color: #FFF;
	text-align: center;
	padding: 60rpx;
	.price {
		color: $primary-color;
		font-size: 50rpx;
		margin-bottom: 20rpx;
	}
}
.pay-type-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	.type-box {
		display: flex;
		align-items: center;
		padding: 30rpx;
		.iconfont {
			padding-right: 20rpx;
		}
		.content {
			flex: 1;
		}
	}
}
.paybar-wrap {
	position: fixed;
	bottom: 0;
	background-color: #fff;
	width: 750rpx;
	height: $paybar-height;
	padding: 0 40rpx;
	display: flex;
	align-items: center;
	.pay-btn {
		width: 100%;
		background-color: $primary-color;
		color: #FFF;
		border-radius: 60rpx;
		padding: 0 60rpx;
		font-size: 30rpx;
	}
}
</style>
