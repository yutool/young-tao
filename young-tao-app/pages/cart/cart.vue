<template>
	<view class="container">
		<view class="yt-container">
			<!-- 标题 -->
			<view class="cart-head">
				<view class="yt-flex-between">
					<view class="cart-head-title">
						<view class="cart-title-text">购物车</view>
						<view class="cart-title-notice">这是 NoticeBar 通告栏</view>
					</view>
					<view class="cart-title-manage">管理</view>
				</view>
				<view class="cart-head-content">
					<view class="cart-content-title">共7件宝贝</view>
					<view class="cart-content-address">收货地址：xxxcdsfsdfsdfdsfdsfsdxxx</view>
				</view>
			</view>
			
			<!-- 列表 -->
			<view>
				<yt-cart-card></yt-cart-card>
				<yt-cart-card></yt-cart-card>
				<yt-cart-card></yt-cart-card>
			</view>
		</view>
		
		
		<!-- 支付栏 -->
		<view class="cart-pay">
			<yt-cart-pay></yt-cart-pay>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [
					'寒雨连江夜入吴',
					'平明送客楚山孤',
					'洛阳亲友如相问',
					'一片冰心在玉壶'
				]
			}
		},
		methods: {
		}
	}
</script>

<style lang="scss" scoped>
.container {
	margin-bottom: calc(var(--window-bottom) + 100rpx);
}
.cart-head {
	margin-bottom: 20rpx;
	.cart-head-title {
		display: flex;
		align-items: center;
		margin-bottom: 10rpx;
	}
	.cart-title-text {
		font-size: 40rpx;
		font-weight: bolder;
		margin-right: 10rpx;
	}
	.cart-title-notice {
		background-color: #FCA9A9;
		font-size: 16rpx;
		border-radius: 6rpx;
	}
	.cart-title-manage {
	}
	.cart-head-content {
		display: flex;
		align-items: center;
		color: #666;
	}
	.cart-content-title {
		margin-right: 20rpx;
	}
	.cart-content-address {
		width: 360rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
}
.cart-pay {
	position: fixed;
	bottom: var(--window-bottom);
}
</style>
