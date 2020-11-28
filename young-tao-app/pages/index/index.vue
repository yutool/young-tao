<template>
	<view class="container">
		<!-- 导航栏 -->
		<u-navbar class="search-bar" :is-back="false" :border-bottom="false">
			<view class="items-center">
				<text class="iconfont icon-qiandao1"></text>
				<u-search placeholder="search" v-model="searchValue" :show-action="false"
					bg-color="none" search-icon-color="#dd5d7b" border-color="#dd5d7b" >
				</u-search>
			</view>
			<view slot="right" class="items-center">
				<!-- #ifndef MP -->
				<text class="iconfont icon-saoyisao"></text>
				<!-- #endif -->
				<text class="iconfont icon-huaban"></text>
			</view>
		</u-navbar>
		<!-- 标签 -->
		<u-sticky :enable="stickyEnable" :offset-top="stickyTop">
			<u-tabs-swiper activeColor="#dd5d7b" ref="tabs" :list="tabs" :current="0" :is-scroll="false" swiperWidth="750"></u-tabs-swiper>
		</u-sticky>
		<!-- 轮播图 -->
		<u-swiper :list="list" height="300" border-radius="0"></u-swiper>
		<!-- 快捷导航 -->
		<scroll-view scroll-x="true" class="quick-nav-wrap">
			<view class="quick-nav-row" v-for="i in 2">
				<view v-for="i in 6" class="quick-nav-item">
					<image class="quick-nav-item-img" src="http://m.360buyimg.com/mobilecms/s120x120_jfs/t1/142596/7/1864/4759/5efbf5a9E60c62b8a/49cdd24cb2bfecf5.png.webp"></image>
					<view class="quick-nav-item-text">图片</view>
				</view>
			</view>
		</scroll-view>
		
		<!-- 限时秒杀 -->
		<view class="seckill-wrap">
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
					<yt-simple-product-card v-for="i in 10" :key="i"></yt-simple-product-card>
				</view>
			</scroll-view>
		</view>
		
		<view class="yt-container">
			<!-- 抢购 -->
			<view class="snapup-wrap">
				<view class="snapup-box">
					<u-swiper :list="list" height="466" border-radius="12"></u-swiper>
				</view>
				<view class="snapup-box">
					<view class="snapup-right-card" style="margin-bottom: 18rpx;">
						<view class="snapup-right-title">
							<view class="title-text">直播</view>
							<u-tag text="直播中" type="warning" size="mini" mode="dark"></u-tag>
						</view>
						<view class="snapup-right-img">
							<image src="http://img14.360buyimg.com/mobilecms/s372x372_jfs/t1/119069/23/14795/154967/5f34aebdE98cc1eac/a22d93a5fe5ddd66.jpg!q70.dpg.webp"></image>
							<image src="http://img14.360buyimg.com/mobilecms/s372x372_jfs/t1/119069/23/14795/154967/5f34aebdE98cc1eac/a22d93a5fe5ddd66.jpg!q70.dpg.webp"></image>
						</view>
					</view>
					<view class="snapup-right-card">
						<view class="snapup-right-title">
							<view class="title-text">有好货</view>
							<u-tag text="全民口碑" type="warning" size="mini" mode="dark"></u-tag>
						</view>
						<view class="snapup-right-img">
							<image src="http://img14.360buyimg.com/mobilecms/s372x372_jfs/t1/119069/23/14795/154967/5f34aebdE98cc1eac/a22d93a5fe5ddd66.jpg!q70.dpg.webp"></image>
							<image src="http://img14.360buyimg.com/mobilecms/s372x372_jfs/t1/119069/23/14795/154967/5f34aebdE98cc1eac/a22d93a5fe5ddd66.jpg!q70.dpg.webp"></image>
						</view>
					</view>
				</view>
			</view>
			<!-- 推荐 -->
			<view class="recommended-wrap">
				<!-- 推荐菜单 -->
				<view class="recommended-menu">
					<view class="recommended-menu-item active">
						<view class="item-name">今日推荐</view>
						<view class="item-subname">爆款热卖</view>
					</view>
					<view class="recommended-menu-item">
						<view class="item-name">猜你喜欢</view>
						<view class="item-subname">为你推荐</view>
					</view>
				</view>
				<!-- 推荐商品 -->
				<u-waterfall v-model="goodsList">
					<template v-slot:left="{leftList}">
						<view v-for="(item, index) in leftList" :key="index">
							<yt-product-card margin="0 20rpx 20rpx 0"></yt-product-card>
						</view>
					</template>
					<template v-slot:right="{rightList}">
						<view v-for="(item, index) in rightList" :key="index">
							<yt-product-card margin="0 20rpx 20rpx 0"></yt-product-card>
						</view>
					</template>
				</u-waterfall>
				<!-- 加载更多 -->
				<view class="pt-3 pb-3">
					<u-loadmore  bg-color="rgb(240, 240, 240)" :status="loadStatus" @loadmore="addRandomData"></u-loadmore>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loadStatus: 'loadmore',
				searchValue: '',
				stickyEnable: true,
				stickyTop: 0,
				// #ifdef MP-WEIXIN
				stickyTop: 130,
				// #endif
				carouselBackground: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
				tabs: [
					{ name: '首页' },
					{ name: '女装' },
					{ name: '数码' },
					{ name: '电脑办公' },
					{ name: '个人清洁' },
					{ name: '男装' },
					{ name: '母婴童装' },
					{ name: '运动' },
					{ name: '美妆' }
				],
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
				],
				goodsList: []
			}
		},
		onLoad() {
			this.addRandomData();
		},
		onShow() {
			this.stickyEnable= true
		},
		onHide() {
			this.stickyEnable= false
		},
		methods: {
			addRandomData() {
				this.loadStatus = 'loading';
				// 模拟数据加载
				setTimeout(() => {
					for(let i = 0; i < 10; i++) {
						this.goodsList.push({})
					}
					this.loadStatus = 'loadmore';
				}, 1000)
			}
		}
	}
</script>

<style lang="scss" scoped>
::-webkit-scrollbar {
	width: 0;
	height: 0;
	background-color: transparent;
}
.container {
	margin-bottom: var(--window-bottom);
}
// 导航栏
.search-bar {
	.icon-qiandao1 {
		color: $primary-color;
		margin: 0 20rpx;
		font-size: 66rpx;
	}
	.icon-saoyisao {
		color: $primary-color;
		margin-left: 20rpx;
		font-size: 66rpx;
	}
	.icon-huaban {
		color: $primary-color;
		margin: 0 20rpx;
		font-size: 72rpx;
	}
}

// 快捷导航
.quick-nav-wrap {
	margin-bottom: $module-margin;
	background-color: #FFF;
	.quick-nav-row {
		display: flex;
	}
	.quick-nav-item {
		padding: 10rpx 30rpx;
		text-align: center;
	}
	.quick-nav-item-img {
		width: 90rpx;
		height: 90rpx;
	}
	.quick-nav-item-text {
		font-size: 28rpx;
		margin-top: 5rpx;
		color: #666;
	}
}

// 秒杀
.seckill-wrap {
	margin-bottom: $module-margin;
	background-color: #FFF;
	padding: 20rpx;
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
		border: 2rpx solid $primary-color;
		border-radius: 20rpx;
		line-height: 32rpx;
		.time-hour {
			background-color: $primary-color;
			position: relative;
			left: -2rpx;
			border-radius: 20rpx;
			padding: 2rpx 6rpx;
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

// 抢购
.snapup-wrap {
	margin-bottom: $module-margin;
	border-radius: $border-radius;
	display: flex;
	justify-content: space-between;
	.snapup-box {
		width: $card-widht;
		border-radius: 100rpx;
	}
	.snapup-right-card {
		border-radius: $border-radius;
		background-color: #FFF;
		padding: 12rpx;
	}
	.snapup-right-img {
		display: flex;
		justify-content: space-around;
		image {
			width: 150rpx;
			height: 150rpx;
		}
	}
	.snapup-right-title {
		display: flex;
		align-items: center;
		margin-bottom: 10rpx;
		.title-text {
			font-weight: bolder;
			font-size: 30rpx;
			margin-right: 10rpx;
		}
	}
}

// 推荐商品
.recommended-wrap {
	.recommended-menu {
		margin-bottom: $module-margin;
		display: flex;
		justify-content: space-around;
		background-color: rgb(241, 241, 241);
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
				color: $primary-color;
			}
			.item-subname {
				background-color: $primary-color;
				color: #FFF;
				border-radius: 20rpx;
			}
		}
	}
}
</style>
