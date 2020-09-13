<template>
	<view class="yt-cart-pay">
		<view class="cart-sel-all">
			<u-checkbox v-model="value">全选</u-checkbox>
		</view>
		<viwe class="goods-total">
			<view>合计：</view>
			<view>￥0</view>
			<button class="pay-btn" size="mini">结算</button>
		</viwe>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: false
			};
		}
	}
</script>

<style lang="scss" scoped>
.yt-cart-pay {
	display: flex;
	padding: 10rpx;
	width: 750rpx;
	background-color: #FFF;
	.cart-sel-all {
		flex: 1;
	}
	.goods-total {
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}
	.pay-btn {
		border-radius: 50rpx;
		background-color: #F43F3B;
		color: #EEE;
		margin: 0 20rpx;
	}
}

</style>
