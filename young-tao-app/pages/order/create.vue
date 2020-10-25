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
		<yt-create-order-goods v-for="i in 2" class="module-bottom"></yt-create-order-goods>
		
		<!-- 商品金额 -->
		<view class="money-wrap">
			<view class="money-box">
				<view class="lable">商品金额</view>
				<view class="value">￥100.00</view>
			</view>
			<view class="money-box">
				<view class="lable">退货无忧</view>
				<view class="value">￥0.00</view>
			</view>
			<view class="money-box">
				<view class="lable">运费（总量0.565kg)</view>
				<view class="value">￥0.00</view>
			</view>
			<view class="total-box">
				<view class="lable">
					<text>合计：</text>
					<text>￥100.00</text>
				</view>
			</view>
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
			<view class="money">￥100.00</view>
			<button class="pay-btn" @click="pay">确认支付</button>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			pay() {
				uni.navigateTo({
					url: '../pay/pay'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
$paybar-height: 115rpx;
.container {
	margin-bottom: calc(130rpx);
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
.money-wrap {
	margin-bottom: $module-margin;
	padding: 30rpx;
	background-color: #fff;
	.money-box {
		display: flex;
		.lable {
			flex: 1;
		}
		.value {
		}
	}
	.total-box {
		text-align: right;
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
