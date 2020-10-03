<template>
	<view class="yt-cart-pay">
		<view class="select-all">
			<u-checkbox v-model="value">全选</u-checkbox>
		</view>
		<view class="goods-total">
			<view>合计：</view>
			<view class="total-price">￥0</view>
			<button class="pay-btn" size="mini">结算</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: false
			}
		}
	}
</script>

<style lang="scss" scoped>
.yt-cart-pay {
	display: flex;
	align-items: center;
	padding: 20rpx;
	width: 750rpx;
	background-color: #FFF;
	.select-all {
		flex: 1;
	}
	.goods-total {
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}
	.total-price {
		font-size: 40rpx;
		color: $primary-color;
		margin-right: 20rpx;
	}
	.pay-btn {
		border-radius: 50rpx;
		background-color: $primary-color;
		color: #EEE;
	}
}

</style>
