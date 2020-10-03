<template>
	<view class="yt-cart-card">
		<!-- 店名 -->
		<view class="cart-shop">
			<u-checkbox v-model="value"></u-checkbox>
			<text class="shop-name">店名 &gt;</text>
			<text class="shop-ticket">领券</text>
		</view>
		<!-- 商品列表 -->
		<view class="cart-goods-list">
			<view v-for="i in 2" :key="i" class="goods-item">
				<view class="item-checkbox">
					<u-checkbox v-model="value"></u-checkbox>
				</view>
				<!-- 商品图片 -->
				<view class="item-img">
					<image src="http://img10.360buyimg.com/mobilecms/s372x372_jfs/t1/112035/15/17621/129863/5f5ba84eE098a6257/ed106a5c3d02855d.jpg!q70.dpg.webp"></image>
				</view>
				<!-- 商品详情 -->
				<view class="item-detail">
					<view class="detail-name">名称name</view>
					<view>
						<text class="detail-spec" @click="show = true;">
							<text class="mr-3">规格</text>
							<text>&gt;</text>
						</text>
					</view>
					<view class="detail-price">
						<view class="price-text">￥10</view>
						<u-number-box v-model="number" input-width="70" input-height="40"></u-number-box>
					</view>
				</view>
				<!-- 弹出层 -->
				<u-popup mode="bottom" v-model="show" :closeable="true">
					<view class="content">
						<yt-goods-pupop-content></yt-goods-pupop-content>
						<view class="confrim-btn">
							<u-button @click="show = false;">确定</u-button>
						</view>
					</view>
				</u-popup>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				number: 0,
				value: false,
				show: false
			}
		}
	}
</script>

<style lang="scss" scoped>
.yt-cart-card {
	border-radius: $border-radius;
	margin-bottom: $module-bottom;
	padding: 10rpx 20rpx;
	background-color: #FFF;
	.cart-shop {
		display: flex;
		align-items: center;
		padding: 20rpx 0;
		.shop-name {
			flex: 1;
		}
	}
	.cart-goods-list {
		.goods-item {
			display: flex;
			padding: 20rpx 0;
		}
		.item-checkbox {
			align-self: center;
		}
		.item-img {
			margin-right: 20rpx;
			image {
				width: 160rpx;
				height: 160rpx;
				border-radius: 10rpx;
			}
		}
		.item-detail {
			flex: 1;
			display: flex;
			flex-direction: column;
			justify-content: space-around;
		}
		.detail-spec {
			background-color: #eee;
			padding: 6rpx 20rpx;
			border-radius: 10rpx;
		}
		.detail-price {
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		.price-text {
			font-size: 36rpx;
			color: $primary-color;
		}
	}
}
</style>
