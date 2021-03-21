<template>
	<view class="container">
		<u-navbar title="填写订单" :border-bottom="false"></u-navbar>
		<!-- 收货地址 -->
		<view class="address-wrap">
			<view class="address-box">
				<view class="address">
					<view>xx省xx市xx区xx街道</view>
					<view>xx小区xx栋xx号</view>
				</view>
				<view>&gt;</view>
			</view>
			<view class="contact-box">
				<text>姓名</text>
				<text>132xxxx2661</text>
			</view>
		</view>
		<!-- 商品信息 -->
		<view class="module-bottom">
			<yt-order-product v-for="merchant in merchantList" :data="merchant">
			</yt-order-product>
		</view>
		<!-- 发票 -->
		<view class="other-wrap">
			<view class="other-box">
				<view class="title">发票</view>
				<view>普票（商品明细-个人）</view>
			</view>
			<view class="other-box">
				<view class="title">支付方式</view>
				<view>在线支付</view>
			</view>
		</view>
		
		<!-- 支付栏 -->
		<view class="paybar-wrap">
			<view class="money">总计：￥{{totalMoney}}</view>
			<button class="pay-btn" @click="createOrder">确认支付</button>
		</view>
	</view>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { confirmOrder } from '@/api/gmc/product.js';
import { createOrder } from '@/api/omc/order.js';
import { queryStatus } from '@/api/omc/order.js';
import { gscConfirmOrder } from '@/api/gsc/product.js';
import { gscCreateOrder } from '@/api/gsc/order.js';

@Component
export default class CreateOrder extends Vue {
	private merchantList= [];
	private timer = null;
	
	onLoad(option) {
		if (this.confirmOrder.length == 0) {
			// ...
		} else if (this.confirmOrder.length == 1 && this.confirmOrder[0].type == this.$orderType.SECKILL) {
			// 秒杀订单
			gscConfirmOrder({skuId: this.confirmOrder[0].skuId}).then(res => {
				console.log(res)
				const merchant = res.data;
				merchant.remark = '';
				this.merchantList = [merchant];
			})
		} else {
			// 普通订单
			confirmOrder({skuList: this.confirmOrder}).then(res => {
				for (const merchant of res.data) {
					merchant.remark = ''
				}
				this.merchantList = res.data
				console.log(this.merchantList)
			})
		}
	}
	
	// 创建订单
	private createOrder() {
		const order = []
		for (const merchant of this.merchantList) {
			const orderItem = []
			for (const sku of merchant.skuList) {
				orderItem.push({ skuId: sku.skuId, num: sku.count })
			}
			order.push({ orderItem, remark: merchant.remark })
		}
		console.log({ data: order, shippingAddressId: '0' })
		
		if (this.confirmOrder.length == 1 && this.confirmOrder[0].type == this.$orderType.SECKILL) {
			// 秒杀订单
			gscCreateOrder({ skuId: order[0].orderItem[0].skuId, shippingAddressId: '0', remark: order[0].remark }).then(res => {
				this.checkOrderStatus(res.data)
			})
		} else {
			// 普通订单
			createOrder({ data: order, shippingAddressId: '0' }).then(res => {
				this.checkOrderStatus(res.data)
			})
		}
	}
	
	// 判断是否生成订单
	private checkOrderStatus(paymentId: string) {
		this.timer = setInterval(() => {
			queryStatus(paymentId).then(res => {
				if (res.data && res.data == this.$orderStatus.PAYMENT) {
					if (this.timer) clearInterval(this.timer)
					uni.navigateTo({
						url: `../pay/pay?payId=${paymentId}`
					})
				}
			})
		}, 1000) 
	}
	
	get totalMoney() {
		var sum = 0;
		for (const merchant of this.merchantList) {
			for (const item of merchant.skuList) {
				sum += item.price * item.count;
			}
		}
		return sum;
	}
	get confirmOrder() {
		return this.$store.state.global.confirmOrder;
	} 
}
</script>

<style lang="scss" scoped>
$paybar-height: 115rpx;
.container {
	margin-bottom: calc(135rpx);
}
.address-wrap {
	margin-bottom: $module-margin;
	padding: 20rpx 30rpx;
	background-color: #FFF;
	.address-box {
		display: flex;
		align-items: center;
		.address {
			flex: 1;
		}
	}
}
.other-wrap {
	margin-bottom: $module-margin;
	padding: 20rpx 30rpx;
	background-color: #fff;
	.other-box {
		margin-bottom: $module-margin;
		display: flex;
		.title {
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
	display: flex;
	align-items: center;
	padding: 0 30rpx;
	.money {
		flex: 1;
	}
	.pay-btn {
		background-color: $primary-color;
		color: #FFF;
		border-radius: 60rpx;
		padding: 0 60rpx;
		font-size: 30rpx;
	}
}
</style>
