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
			<view class="price">￥38.88</view>
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
			<button class="pay-btn">确认支付 ￥100.00</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				payType: 1
			}
		},
		methods: {
			
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
