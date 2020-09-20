<template>
	<view class="yt-simple-goods-card">
		<image class="yt-simple-goods-img" src="http://img14.360buyimg.com/mobilecms/s372x372_jfs/t1/119069/23/14795/154967/5f34aebdE98cc1eac/a22d93a5fe5ddd66.jpg!q70.dpg.webp"></image>
		<view class="yt-simple-goods-title">
			标题abcdefgaaafadfadsf
		</view>
		<view class="yt-simple-goods-price">
			￥200
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		}
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
