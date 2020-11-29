<template>
	<view class="yt-live-card" :style="{margin}">
		<!-- 直播 -->
		<view class="live-box">
			<image class="live-image" src="https://m.360buyimg.com/livecms/s480x480_jfs/t1/145266/6/9960/334741/5f77cf7cE7f2aaa38/7aa9f79423257a80.jpg!cc_2x2!q70.dpg" mode="aspectFill"></image>
			<!-- 覆盖层 -->
			<view class="cover-box">
				<view class="cover-top">
					<view class="top-title">
						<text class="title-hint">直播中</text>
						<text class="title-number">1000人观看</text>
					</view>
				</view>
				<view class="cover-bottom">
					<image class="anchor-avatar" src="https://m.360buyimg.com/ceco/s80x80_jfs/t1/136597/29/9108/157644/5f51e4bfEc5bdb805/d037f967e9b213db.jpg!q70.dpg"></image>
					<text class="anchor-name">主播名</text>
				</view>
			</view>
		</view>
		<!-- 商品展示 -->
		<view class="content-wrap">
			<view class="live-title">直播标题</view>
			<view class="goods-box">
				<view class="goods-content">
					<view class="goods-title">商品标题</view>
					<view class="goods-price">￥100.00</view>
				</view>
				<image src="https://m.360buyimg.com/mobilecms/s160x160_jfs/t1/120369/26/3618/346059/5ed23046E5a5e4d75/062b0c503309f0ea.jpg!q70.dpg"></image>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['margin'],
		data() {
			return {
				
			};
		}
	}
</script>

<style lang="scss" scoped>
.yt-live-card {
	width: $card-widht;
	background-color: #FFF;
	border-radius: $border-radius;
	.live-box {
		position: relative;
		.live-image {
			border-radius: $border-radius $border-radius 0 0;
		}
	}
	.cover-box {
		display: flex;
		flex-direction: column;
		position: absolute;
		top: 0;
		width: 100%;
		height: 100%;
		color: #FFF;
		.cover-top {
			flex: 1;
		}
		.top-title {
			font-size: 20rpx;
			margin-top: 20rpx;
			margin-left: 10rpx;
		}
		.title-hint {
			background-color: $primary-color;
			padding: 6rpx 20rpx;
			border-radius: 6rpx 0 0 6rpx;
		}
		.title-number {
			background-color: rgba(0, 0, 0, .3);
			padding: 6rpx 20rpx;
			border-radius: 0 6rpx 6rpx 0;
		}
		.cover-bottom {
			display: flex;
			align-items: center;
			margin-bottom: 20rpx;
		}
		.anchor-avatar {
			width: 40rpx;
			height: 40rpx;
			border-radius: 50%;
			margin: 0 10rpx;
		}
		.anchor-name {
			font-size: 20rpx;
		}
	}
	.content-wrap {
		font-size: 20rpx;
		padding: 0 10rpx 10rpx;
		.live-title {
			color: #000;
			margin-bottom: 5rpx;
		}
		.goods-box {
			display: flex;
			image {
				width: 60rpx;
				height: 60rpx;
			}
		}
		.goods-content {
			flex: 1;
		}
		.goods-title {
			color: #999;
			margin-bottom: 5rpx;
		}
	}
	
}
</style>
