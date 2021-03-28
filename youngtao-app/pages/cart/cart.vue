<template>
	<view class="container">
		<!-- 标题 -->
		<u-navbar class="cart-navbar" :is-back="false" :border-bottom="false">
			<view class="items-center">
				<view class="cart-title-text">购物车</view>
				<u-notice-bar mode="horizontal" :list="list" volume-size="26" font-size="20" padding="6rpx 10rpx" border-radius="6" style="width: 300rpx;">
				</u-notice-bar>
			</view>
			<view slot="right" class="mr-4">
				<view>管理</view>
			</view>
		</u-navbar>
		
		<!-- 收货地址 -->
		<u-sticky :enable="stickyEnable" :offset-top="stickyTop">
			<view class="shipping-address">
				<text class="address-info">收货地址：xxxcdsfsdfsdfdsfdsfsdxxx</text>
			</view>
		</u-sticky>
		
		<!-- 内容 -->
		<view class="cart-wrap">
			<template v-for="cart in cartList" >
				<yt-cart-card class="cart-item" :data="cart"></yt-cart-card>
			</template>
		</view>
		
		<!-- 支付栏 -->
		<view class="cart-pay">
			<yt-cart-pay></yt-cart-pay>
		</view>
	</view>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { getUserCart } from '@/api/omc/cart.js';

@Component
export default class Cart extends Vue {
	private stickyEnable = true
	private stickyTop = 0
	// #ifdef MP-WEIXIN
	private stickyTop = 130
	// #endif
	private list = [
		'寒雨连江夜入吴',
		'平明送客楚山孤',
		'洛阳亲友如相问',
		'一片冰心在玉壶'
	]
	
	private cartList = [];
	onShow() {
		this.stickyEnable= true;
		this.getUserCart();
	}
	onHide() {
		this.stickyEnable= false
	}
	private getUserCart() {
		getUserCart().then(res => {
			this.cartList = res.data;
		})
	}
}
</script>

<style lang="scss" scoped>
.container {
	margin-bottom: calc(var(--window-bottom) + 120rpx);
}
// 标题
.cart-navbar {
	.cart-title-text {
		margin-left: $container-margin;
		margin-right: 10rpx;
		font-size: 40rpx;
		font-weight: bolder;
	}
}
// 购物车列表
.cart-wrap {
	.cart-item {
		display: block;
		margin: 0 20rpx 20rpx;
	}
}
// 收货地址
.shipping-address {
	background-color: #fff;
	padding-bottom: 10rpx;
	margin-bottom: $module-margin;
	.address-info {
		margin: 0 $container-margin;
		width: 560rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
}
// 工具栏
.cart-pay {
	position: fixed;
	bottom: var(--window-bottom);
}
</style>
