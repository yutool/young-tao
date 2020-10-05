<template>
	<view class="container">
		<u-navbar class="live-navbar" :is-back="false" :border-bottom="false" height="50">
			<view class="items-center">
				<text class="iconfont icon-saoyisao"></text>
				<u-search class="search-bar" placeholder="search" bg-color="#f1f1f1" :show-action="false">
				</u-search>
			</view>
			<view slot="right" class="mr-4">
				<text class="iconfont icon-huaban"></text>
			</view>
		</u-navbar>
		
		<u-sticky class="module-bottom">
			<u-tabs :list="tabs" :current="1"></u-tabs>
		</u-sticky>
		<!-- 内容 -->
		<view class="yt-container">
			<u-waterfall v-model="liveList">
				<template v-slot:left="{leftList}">
					<view v-for="(item, index) in leftList" :key="index">
						<yt-live-card margin="0 20rpx 20rpx 0"></yt-live-card>
					</view>
				</template>
				<template v-slot:right="{rightList}">
					<view v-for="(item, index) in rightList" :key="index">
						<yt-live-card margin="0 20rpx 20rpx 0"></yt-live-card>
					</view>
				</template>
			</u-waterfall>
			<!-- 加载更多 -->
			<view class="pt-3 pb-3">
				<u-loadmore  bg-color="rgb(240, 240, 240)" :status="loadStatus" @loadmore="addRandomData"></u-loadmore>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loadStatus: 'loadmore',
				tabs: [
					{ name: '关注' },
					{ name: '推荐' },
					{ name: '化妆饰品' },
					{ name: '食品生鲜' },
					{ name: '服饰穿搭' },
					{ name: '家具日用' },
					{ name: '新店有礼' },
				],
				liveList: []
			}
		},
		onLoad() {
			this.addRandomData();
		},
		methods: {
			addRandomData() {
				this.loadStatus = 'loading';
				// 模拟数据加载
				setTimeout(() => {
					for(let i = 0; i < 10; i++) {
						this.liveList.push({})
					}
					this.loadStatus = 'loadmore';
				}, 1000)
			},
		}
	}
</script>

<style lang="scss">
.container {
	margin-bottom: var(--window-bottom);
}
// 导航栏
.live-navbar {
	.search-bar {
		width: 530rpx;
		/* #ifdef MP */
		width: 340rpx;
		/* #endif */
	}
	.icon-saoyisao {
		font-size: 46rpx;
		margin-left: 20rpx;
		margin-right: 40rpx;
	}
	.icon-huaban {
		font-size: 50rpx;
	}
}
</style>
