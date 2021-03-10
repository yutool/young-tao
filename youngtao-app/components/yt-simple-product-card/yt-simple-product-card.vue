<template>
	<view class="yt-simple-goods-card">
		<image class="yt-simple-goods-img" :src="data.image"></image>
		<view class="yt-simple-goods-title">
			{{data.title || 'title'}}
		</view>
		<view class="yt-simple-goods-price">
			￥{{data.price}}
		</view>
	</view>
</template>

<script>
	export default {
		props: ['data']
	}
</script>

<style lang="scss" scoped>
.yt-simple-goods-card {
	width: 160rpx;
	margin: 10rpx;
	.yt-simple-goods-img {
		width: 160rpx;
		height: 160rpx;
	}
	.yt-simple-goods-title {
		font-size: 26rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	.yt-simple-goods-price {
		font-size: 26rpx;
		color: #fa436a;
	}
}
</style>
