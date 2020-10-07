<template>
	<view class="yt-goodsbar">
		<view class="left-box">
			<view class="item">
				<u-icon name="home" :size="40" :color="$u.color['contentColor']"></u-icon>
				<view class="text">店铺</view>
			</view>
			<view class="item">
				<u-icon name="server-fill" :size="40" :color="$u.color['contentColor']"></u-icon>
				<view class="text">客服</view>
			</view>
			<view class="item car">
				<u-badge class="car-num" :count="9" type="error" :offset="[-3, -6]"></u-badge>
				<u-icon name="shopping-cart" :size="40" :color="$u.color['contentColor']"></u-icon>
				<view class="text">购物车</view>
			</view>
		</view>
		<view class="right-box">
			<view class="cart btn">加入购物车</view>
			<view class="buy btn">立即购买</view>
		</view>
	</view>
</template>

<script>
export default {
	
};
</script>

<style lang="scss" scoped>
.yt-goodsbar {
	display: flex;
	border: solid 2rpx #f2f2f2;
	background-color: #ffffff;
	padding: 20rpx 30rpx;
	width: 750rpx;
	.left-box {
		flex: 1;
		display: flex;
		justify-content: space-between;
		margin-right: 30rpx;
		font-size: 20rpx;
		.item {
			text-align: center;
		}
		.car {
			position: relative;
			.car-num {
				position: absolute;
				top: -10rpx;
				right: -10rpx;
			}
		}
	}
	.right-box {
		display: flex;
		font-size: 30rpx;
		align-items: center;
		.btn {
			width: 210rpx;
			padding: 16rpx;
			text-align: center;
			border-radius: 36rpx;
			color: #ffffff;
		}
		.cart {
			background-color: #ed3f14;
			margin-right: 20rpx;
		}
		.buy {
			background-color: #ff7900;
		}
	}
}
</style>
