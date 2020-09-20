<template>
	<view class="yt-goods-card">
		<view class="yt-goods-image">
			<image class="image" :src="item.image"></image>
		</view>
		<view class="yt-goods-content">
			<view class="yt-goods-title">
				<text class="yt-goods-title-tip">{{item.tip}}</text>
				<text class="yt-goods-title-text">{{item.title}}</text>
			</view>
			<view class="yt-goods-price">
				<text class="yt-goods-price-original">￥{{item.favourPrice}}</text>
				<text class="yt-goods-price-favour">￥{{item.originalPrice}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				item:	{
						image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
						title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
						originalPrice: 9999,
						favourPrice: 8888,
						tip: '自营'
					}
			};
		}
	}
</script>

<style lang="scss" scoped>
.yt-goods-card {
	background-color: #FFF;
	border-radius: 20rpx;
	margin: 0 auto 20rpx;
	width: 325rpx;
	.yt-goods-image {
		text-align: center;
		.image {
			border-top-right-radius: 20rpx;
			border-top-left-radius: 20rpx;
			height: 325rpx;
		}
	}
	.yt-goods-content {
		padding: 10rpx 20rpx;
	}
	.yt-goods-title {
		display: -webkit-box;
		overflow: hidden;
		text-overflow: ellipsis;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		.yt-goods-title-tip {
			font-size: 26rpx;
			color: #ffffff;
			background-color: #ff3333;
			padding: 2rpx 10rpx;
			margin-right: 10rpx;
			border-radius: 10rpx;
		}
		.yt-goods-title-text {
		}
	}
	.yt-goods-price {
		margin-top: 10rpx;
		font-size: 28rpx;
		line-height: 1.5;
		position: relative;
		.yt-goods-price-original {
			color: #e80080;
		}
		.yt-goods-price-favour {
			color: #888888;
			text-decoration: line-through;
			margin-left: 10rpx;
		}
	}
}
</style>
