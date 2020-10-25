<template>
	<view class="yt-card" :style="[padding, {borderRadius: radius}, width]">
		<view class="inner-card" :style="[{margin: margin == null ? null : '0 ' + margin + 'rpx'}]">
			<slot></slot>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['padding', 'radius', 'width', 'margin']
	}
</script>

<style lang="scss" scoped>
.yt-card {
	background-color: #FFFFFF;
	border-radius: 16rpx;
	.inner-card {
		margin: 0 20rpx;
	}
}
</style>
