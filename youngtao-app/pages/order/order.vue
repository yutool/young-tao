<template>
	<view class="container">
		<!-- 标题 -->
		<u-navbar class="order-navbar" :border-bottom="false">
			<view class="search-box">
				<u-search class="search-bar" placeholder="search" :show-action="false"></u-search>
			</view>
			<view slot="right">
				<text class="iconfont icon-saoyisao"></text>
				<text class="iconfont icon-huaban"></text>
			</view>
		</u-navbar>
		<view class="wrap">
			<!-- 导航栏 -->
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="list" :current="current" @change="change" :is-scroll="false" swiperWidth="750">
				</u-tabs-swiper>
			</view>
			<!-- 内容 -->
			<swiper class="swiper-box" :current="swiperIdx" @transition="transition" @animationfinish="animationfinish">
				<!-- 全部 -->
				<template v-for="i in 5">
					<swiper-item class="swiper-item">
						<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
							<view class="page-box" v-if="orderList[swiperIdx].length != 0">
								<template v-for="order in orderList[swiperIdx]">
									<yt-order-card :data="order"></yt-order-card>
								</template>
								<u-loadmore :status="loadStatus[swiperIdx]" bgColor="#f2f2f2"></u-loadmore>
							</view>
							<view class="page-box" v-else>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png"></image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</scroll-view>
					</swiper-item>
				</template>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { getUserOrder } from '@/api/omc/order.js';

@Component
export default class Order extends Vue {
	private orderList = [[], [], [], [],[]]
	private list = [
		{ name: '已完成' },
		{ name: '待付款' },
		{ name: '代发货' },
		{ name: '待收货' },
		{ name: '待评价' }
	]
	private current = 0
	private swiperIdx = 0
	private tabsHeight = 0
	private dx = 0
	private loadStatus = ['loadmore','loadmore','loadmore','loadmore','loadmore']
	
	private statusList = [
		this.$orderStatus.COMPLETED, 
		this.$orderStatus.PAYMENT, 
		this.$orderStatus.DELIVERY, 
		this.$orderStatus.RECEIVING, 
		this.$orderStatus.COMMENT
	]

	onLoad() {
		this.loadData(1, 10);
	}
	// 加载数据
	private loadData(page, size) {
		console.log(this.swiperIdx, this.statusList[this.swiperIdx], 'afdadfasd')
		getUserOrder({status: this.statusList[this.swiperIdx], page, size }).then(res => {
			this.orderList[this.swiperIdx].push(...res.data)
		})
	}
	private reachBottom() {
		// // 此tab为空数据
		// if(this.current != 2) {
		// 	this.loadStatus.splice(this.current,1,"loading")
		// 	setTimeout(() => {
		// 		this.getOrderList(this.current);
		// 	}, 1200);
		// }
	}
	// tab栏切换
	private change(index) {
		this.swiperIdx = index;
		this.loadData(1, 10);
	}
	private transition({ detail: { dx } }) {
		this.$refs.tabs.setDx(dx);
	}
	private animationfinish({ detail: { current } }) {
		this.$refs.tabs.setFinishCurrent(current);
		this.swiperIdx = current;
		this.current = current;
	}
}
</script>

<style>
/* #ifndef H5 */
page {
	height: 100%;
	background-color: #f2f2f2;
}
/* #endif */
</style>

<style lang="scss" scoped>
.order-navbar {
	.search-box {
		margin: 0 20rpx;
		.search-bar {
			width: 450rpx;
			/* #ifdef MP */
			width: 210rpx;
			/* #endif */
		}
	}
	.iconfont {
		font-size: 50rpx;
		margin-right: 30rpx;
	}
}
.centre {
	text-align: center;
	margin: 100rpx auto;
	font-size: 32rpx;
	image {
		margin: 0 auto;
		width: 164rpx;
		height: 164rpx;
		border-radius: 50%;
		margin-bottom: 20rpx;
	}
	.tips {
		font-size: 24rpx;
		color: #999999;
		margin-top: 20rpx;
	}
	.btn {
		margin: 80rpx auto;
		width: 200rpx;
		border-radius: 32rpx;
		line-height: 64rpx;
		color: #ffffff;
		font-size: 26rpx;
		background: linear-gradient(270deg, rgba(249, 116, 90, 1) 0%, rgba(255, 158, 1, 1) 100%);
	}
}
.wrap {
	display: flex;
	flex-direction: column;
	height: calc(100vh - var(--window-top));
	width: 100%;
}
.swiper-box {
	flex: 1;
}
.swiper-item {
	height: 100%;
}
</style>
