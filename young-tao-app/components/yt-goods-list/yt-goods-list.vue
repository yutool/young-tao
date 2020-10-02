<template>
	<view class="yt-goods-list">
		<u-waterfall v-model="list">
			<template v-slot:left="{leftList}">
				<view v-for="(item, index) in leftList" :key="index">
					<yt-goods-card margin="0 20rpx 20rpx 0"></yt-goods-card>
				</view>
			</template>
			<template  v-slot:right="{rightList}">
				<view v-for="(item, index) in rightList" :key="index">
					<yt-goods-card margin="0 0 20rpx 0"></yt-goods-card>
				</view>
			</template>
		</u-waterfall>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [
					{
						image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
						title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
						originalPrice: 9999,
						favourPrice: 8888,
						tip: '自营'
					},
					{
						image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
						title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
						originalPrice: 9999,
						favourPrice: 8888,
						tip: '自营'
					},
					{
						image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
						title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
						originalPrice: 9999,
						favourPrice: 8888,
						tip: '自营'
					},
					{
						image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
						title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
						originalPrice: 9999,
						favourPrice: 8888,
						tip: '自营'
					},
					{
						image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
						title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
						originalPrice: 9999,
						favourPrice: 8888,
						tip: '自营'
					},
					{
						image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
						title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
						originalPrice: 9999,
						favourPrice: 8888,
						tip: '自营'
					}
				]
			}
		},
		methods: {
		}
	}
</script>

<style lang="scss" scoped>
.yt-goods-list {
}
</style>