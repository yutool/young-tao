<template>
	<view class="container">
		<!-- 适配小程序和app端的搜索框和轮播图 -->
		<!-- #ifndef MP -->
		<u-swiper :list="list"></u-swiper>
		<!-- #endif -->
		<!-- #ifdef MP -->
		<view class="p-2">
			<u-search bg-color="#fff" :show-action="false" height="70" margin="20rpx 0" v-model="searchValue"></u-search>
			<u-swiper :list="list"></u-swiper>
		</view>
		<!-- #endif -->
		
		<view class="p-2">
			<!-- 九宫格 -->
			<swiper class="swiper" @change="change">
				<swiper-item>
					<u-grid :col="5" :border="false" @click="click" hover-class="hover-class">
						<u-grid-item v-for="(item, index) in 10" :index="index" :key="index" bg-color="none">
							<u-icon name="photo" :size="46"></u-icon>
							<view class="grid-text">图片</view>
						</u-grid-item>
					</u-grid>
				</swiper-item>
				<swiper-item>
					<u-grid :col="5" @click="click">
						<u-grid-item v-for="(item, index) in 9" :index="index + 9" :key="index" bg-color="none">
							<u-icon name="photo" :size="46"></u-icon>
							<view class="grid-text">图片</view>
						</u-grid-item>
					</u-grid>
				</swiper-item>
			</swiper>
			<!-- 限时秒杀 -->
			<yt-goods-list></yt-goods-list>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchValue: '',
				carouselBackground: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
				list: [{
						image: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
						title: '昨夜星辰昨夜风，画楼西畔桂堂东'
					}, {
						image: 'https://cdn.uviewui.com/uview/swiper/2.jpg',
						title: '身无彩凤双飞翼，心有灵犀一点通'
					}, {
						image: 'https://cdn.uviewui.com/uview/swiper/3.jpg',
						title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
					}
				]
			}
		},
		methods: {
			change(e) {
				this.current = e.detail.current;
			}
		}
	}
</script>

<style lang="scss">
	

	.grid-text {
		font-size: 28rpx;
		margin-top: 4rpx;
		// color: $u-type-info;
	}
	
	.indicator-dots {
		margin-top: 40rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.indicator-dots-item {
		// background-color: $u-tips-color;
		height: 6px;
		width: 6px;
		border-radius: 10px;
		margin: 0 3px;
	}
	
	.indicator-dots-active {
		// background-color: $u-type-primary;
	}
</style>
