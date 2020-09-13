<template>
	<view class="yt-cart-card">
		<!-- 店名 -->
		<view class="yt-shop">
			<u-checkbox v-model="value"></u-checkbox>
			<text class="yt-shop-name">店名 &gt;</text>
			<text class="yt-shop-ticket">领券</text>
		</view>
		<!-- 商品列表 -->
		<view class="goods-list">
			<view v-for="i in 2" :key="i" class="goods-item">
				<u-checkbox v-model="value"></u-checkbox>
				<view>
					<image class="yt-goods-pic" src="http://img10.360buyimg.com/mobilecms/s372x372_jfs/t1/112035/15/17621/129863/5f5ba84eE098a6257/ed106a5c3d02855d.jpg!q70.dpg.webp"></image>
				</view>
				<view class="item-detail">
					<view>name</view>
					<view @click="open">规格L</view>
					<uni-popup ref="popup" type="bottom">底部弹出 Popup</uni-popup>
					<view class="goods-price-option">
						<view>￥10</view>
						<uni-number-box :min="0" :max="9"></uni-number-box>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: false
			}
		},
		methods: {
			open(){
				this.$refs.popup.open()
			}
		}
	}
</script>

<style lang="scss" scoped>

.yt-cart-card {
	background-color: #FFF;
	border-radius: 10rpx;
	padding: 0 20rpx;
	margin: 20rpx;
	.yt-shop {
		display: flex;
		padding: 20rpx 0;
		.yt-shop-name {
			flex: 1;
		}
		.yt-shop-ticket {
			
		}
	}
	.goods-list {
		.goods-item {
			display: flex;
			padding: 20rpx 0;
		}
		.goods-item-radio {
			align-self: center;
		}
		.item-detail {
			flex: 1;
		}
		.yt-goods-pic {
			width: 200rpx;
			height: 200rpx;
			border-radius: 10rpx;
		}
		.goods-price-option {
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
	}
}
</style>
