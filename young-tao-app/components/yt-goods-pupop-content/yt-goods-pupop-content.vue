<template>
	<view>
		<scroll-view scroll-y="true" class="yt-goods-pupop-scroll">
			<view>
				<view v-for="index in 10" :key="index">
					第{{index}}个Item
				</view>
			</view>
		</scroll-view>
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
.yt-goods-pupop-scroll {
	height: 1000rpx;
}
</style>
