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
			<view class="type-box b-b">
				<view class="icon iconfont icon-qiandai"></view>
				<view class="content">
					<view class="title">微信支付</view>
					<view class="hint">推荐使用微信支付</view>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType === 1' @click="payType = 1"></radio>
				</label>
			</view>
			<view class="type-box b-b">
				<view class="icon iconfont icon-qiandai"></view>
				<view class="content">
					<view class="title">支付宝支付</view>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType === 2' @click="payType = 2"></radio>
				</label>
			</view>
			<view class="type-box">
				<view class="icon iconfont icon-qiandai"></view>
				<view class="content">
					<view class="title">樱桃钱包</view>
					<view class="hint">可用余额 ¥198.5</view>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType === 3' @click="payType = 3"></radio>
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

@Component
export default class CreateOrder extends Vue {
	private payType = 1;
	private orderPayRecord = {};
	
	onLoad(option) {
		const payId = option.payId
		if (!payId) return;
		getByPaymentId(payId).then(res => {
			if (res.data == null) return;
			this.orderPayRecord = res.data;
		})
	}
	
	private handleClick() {
		if (this.payType == 1) {				// 微信支付
			appPay('wxpay', {})
		} else if (this.payType == 2) {	// 支付宝
			appPay('alipay', 'alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016091700532697&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%2235712635712635%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&sign=KIogNAr5uIEo6u7hGv7jg9TeV6KF23VKTuFSRzeHpOd2uam4tiJ7%2FzSuFV0XI303XhmrrxEGGk62Kkjsbigtdrk7rRGGXpQMUVoiyj9De7NY3UQeRzJhUUrqDduv7oywdgPQdJWiFgpq0%2Btau0ROtRGSJD7M306t04s%2BjA%2F0BOE4jAtotjhpnhmMfMXK3TiUP4U4GITi%2FH56s3zwYorIFVlWcPxFzAjqKeGbF9jQ6CsrpecGds5krCV46fJc34dh9BurzsG8VmvBE5%2FnuvPhM5Pixykb30lII3SDy4xgnAphRZP%2FXOnB2DZwNXnHi%2BX1iO4i3SkxxNEaoXD%2FPlw9qQ%3D%3D&sign_type=RSA2×tamp=2021-01-24+16%3A35%3A29&version=1.0');
		} else if (this.payType == 3) {	// 樱桃支付
			
		}
	}
	
	private appPay(provider, orderInfo) {
		uni.requestPayment({
			provider: provider,
			orderInfo: orderInfo,
			success: res => {
				console.log('支付成功', res)
			},
			fail: err => {
				console.log('支付失败', err)
				const message = err.errMsg || '';
				if (message.indexOf('[payment支付宝:62001]') !== -1) {
					uni.showModal({
						content: '您已取消支付。如有需要，您可在我的订单里重新付款。30分钟内有效。',
						showCancel: false
					});
				} else {
					uni.showModal({
						content: '支付失败,原因为: ' + message,
						showCancel: false
					});
				}
			},
			complete: () => {
				// this.submitting = false;
			}
		});
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
