<template>
	<view class="container">
		<!-- 适配小程序和app端的搜索框和轮播图 -->
		<view>
			<!-- #ifndef MP -->
			<u-swiper :list="list"></u-swiper>
			<!-- #endif -->
			<!-- #ifdef MP -->
			<view class="p-2">
				<u-search bg-color="#fff" :show-action="false" height="70" margin="0 0 16rpx" v-model="searchValue"></u-search>
				<u-swiper :list="list"></u-swiper>
			</view>
			<!-- #endif -->
		</view>
		
		<!-- 快捷导航 -->
		<scroll-view scroll-x="true" class="quick-nav-box">
			<view class="quick-nav-row" v-for="i in 2">
				<view v-for="i in 6" class="quick-nav-item">
					<image class="quick-nav-item-img" src="http://m.360buyimg.com/mobilecms/s120x120_jfs/t1/142596/7/1864/4759/5efbf5a9E60c62b8a/49cdd24cb2bfecf5.png.webp"></image>
					<view class="quick-nav-item-text">图片</view>
				</view>
			</view>
		</scroll-view>
		
		<!-- 限时秒杀 -->
		<view class="seckill-box">
			<view class="seckill-title-box">
				<view class="seckill-title">
					<image class="seckill-title-img" src="../../static/images/secskill-img.jpg"></image>
					<view class="seckill-title-time">
						<text class="time-hour">10场</text>
						<text class="time-countdown">00:00:00</text>
					</view>
				</view>
				<view>更多 &gt;</view>
			</view>
			<scroll-view scroll-x="true">
				<view class="seckill-content">
					<yt-simple-goods-card v-for="i in 10" :key="i"></yt-simple-goods-card>
				</view>
			</scroll-view>
		</view>
		
		<!-- 推荐 -->
		<view class="recommended-box">
			<!-- 推荐菜单 -->
			<u-sticky @fixed="isSticky = true" @unfixed="isSticky = false">
				<view class="recommended-menu" :class="isSticky ? 'recommended-menu-sticky' : ''">
					<view class="recommended-menu-item active">
						<view class="item-name">今日推荐</view>
						<view class="item-subname" v-if="!isSticky">爆款热卖</view>
					</view>
					<view class="recommended-menu-item">
						<view class="item-name">猜你喜欢</view>
						<view class="item-subname" v-if="!isSticky">为你推荐</view>
					</view>
				</view>
			</u-sticky>
			<!-- 推荐商品 -->
			<yt-goods-list></yt-goods-list>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchValue: '',
				isSticky: false,
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

<style lang="scss" scoped>
.container {
	margin-bottom: var(--window-bottom);
}
// 快捷导航
.quick-nav-box {
	background-color: #FFF;
	margin-bottom: 16rpx;
	.quick-nav-row {
		display: flex;
	}
	.quick-nav-item {
		padding: 10rpx 30rpx;
		text-align: center;
	}
	.quick-nav-item-img {
		width: 90rpx;
		height: 96rpx;
	}
	.quick-nav-item-text {
		font-size: 28rpx;
		margin-top: 4rpx;
		color: #666;
	}
}

// 秒杀
.seckill-box {
	background-color: #FFF;
	padding: 20rpx;
	margin-bottom: 16rpx;
	.seckill-title-box {
		display: flex;
		justify-content: space-between;
		padding: 10rpx 0;
	}
	.seckill-title {
		display: flex;
		align-items: center;
	}
	.seckill-title-img {
		width: 140rpx;
		height: 30rpx;
		margin-right: 10rpx;
	}
	.seckill-title-time {
		border: 2rpx solid #dd5d7b;
		border-radius: 20rpx;
		line-height: 32rpx;
		.time-hour {
			position: relative;
			left: -2rpx;
			border-radius: 20rpx;
			padding: 2rpx 6rpx;
			background-color: #dd5d7b;
			color: #fff;
			
		}
		.time-countdown {
			margin: 0 10rpx;
		}
	}
	.seckill-content {
		display: flex;
	}
}

// 推荐商品
.recommended-box {
	.recommended-menu {
		display: flex;
		justify-content: space-around;
		background-color: rgb(241, 241, 241);
		padding: 10rpx 0;
	}
	.recommended-menu-sticky {
		background-color: #FFF !important;
	}
	.recommended-menu-item {
		color: #333;
		text-align: center;
		.item-name {
			font-size: 36rpx;
			margin-bottom: 6rpx;
		}
		.item-subname {
			font-size: 24rpx;
		}
		&.active {
			.item-name {
				color: #FF4142;
			}
			.item-subname {
				color: #FFF;
				background-color: #FF4142;
				border-radius: 20rpx;
			}
		}
	}
}
</style>
