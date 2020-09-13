<template>
	<view class="yt-goods">
		<view class="yt-goods-image">
			<image :src="item.image"></image>
		</view>
		<view class="yt-goods-title">
			<text class="yt-goods-tip">{{item.tip}}</text>
			<text class="yt-goods-text">{{item.title}}</text>
		</view>
		<view class="yt-goods-price">
			<text class="yt-goods-price-original">￥{{item.favourPrice}}</text>
			<text class="yt-goods-price-favour">￥{{item.originalPrice}}</text>
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
.yt-goods {
	background-color: #FFF;
	border-radius: 20rpx;
	margin: 10rpx;
	width: 325rpx;
	.yt-goods-image {
		text-align: center;
		image {
			border-top-right-radius: 20rpx;
			border-top-left-radius: 20rpx;
			height: 325rpx;
		}
	}
	.yt-goods-title {
		display: flex;
		.yt-goods-tip {
			flex: 1;
			background-color: #ff3333;
			color: #ffffff;
			padding: 0 10rpx;
			border-radius: 5rpx;
		}
		.yt-goods-text {
			word-break: break-all;
			display: -webkit-box;
			overflow: hidden;
			line-height: 1.5;
			text-overflow: ellipsis;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 2;
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
