<template>
	<view>
		<u-navbar class="goods-navbar" :immersive="true" :background="{backgroundColor: 'rgba(255,255,255,' + navbar.opacity + ')'}">
			<view class="navbar-content" v-if="navbar.scrollTop > 10" :style="{opacity: navbar.opacity}">
			  <view class="content-item" :class="{'active': tabIndex === 0}">
			    <view>商品</view>
			    <view class="line"></view>
			  </view>
			  <view class="content-item" :class="{'active': tabIndex === 1}">
			    <view>评价</view>
			    <view class="line"></view>
			  </view>
			  <view class="content-item" :class="{'active': tabIndex === 2}">
			    <view>详情</view>
			    <view class="line"></view>
			  </view>
				<!-- #ifndef MP -->
				<view class="content-item" :class="{'active': tabIndex === 3}">
				  <view>推荐</view>
				  <view class="line"></view>
				</view>
				<!-- #endif -->
			</view>
			<view slot="right" class="navbar-right" :class="{'navbar-show': navbar.scrollTop > navbar.threshold}">
				<view class="right-item" :style="'backgroundColor: rgba(0,0,0, '+(0.3-navbar.opacity)+')'">
					<text class="iconfont icon-huaban"></text>
				</view>
				<view class="right-item" :style="'backgroundColor: rgba(0,0,0, '+(0.3-navbar.opacity)+')'">
					<text class="iconfont icon-huaban"></text>
				</view>
				<!-- #ifndef MP -->
				<view class="right-item" :style="'backgroundColor: rgba(0,0,0, '+(0.3-navbar.opacity)+')'">
					<text class="iconfont icon-huaban"></text>
				</view>
				<!-- #endif -->
			</view>
		</u-navbar>
		
		
		<view v-for="i in 20" :key="i" style="padding: 30rpx;">a</view>
	</view>
</template>

<script>
import {between} from '@/common/js/utils.js';

export default {
  data() {
    return {
			tabIndex: 0,
			navbar: {
				scrollTop: 0,
				opacity: 0,
				threshold: 100
			}
    };
  },
	onPageScroll(e) {
		this.navbar.scrollTop = e.scrollTop;
		this.navbar.opacity = Math.max(Math.min(e.scrollTop/this.navbar.threshold, 1), 0);
	},
  methods: {
		onBack() {
		  uni.navigateBack();
		},
  }
};
</script>

<style lang="scss" scoped>
.goods-navbar {
	.navbar-content {
		width: 100%;
		display: flex;
		justify-content: flex-end;
		align-items: center;
		margin-right: 40rpx;
		.content-item {
			margin: 0 10rpx;
			padding: 0 10rpx;
			color: #555555;
			font-size: 26rpx;
		}
		.active {
			.line {
				position: absolute;
				width: 100%;
				height: 6rpx;
				margin-top: 6rpx;
				background: linear-gradient(to right, red, rgba(255,255,255,0.3));
			}
			&.content-item {
				position: relative;
				align-self: flex-end;
			}
		}
	}
	.navbar-right {
		display: flex;
		align-items: center;
		.right-item {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-right: 20rpx;
			width: 50rpx;
			height: 50rpx;
			border-radius: 100%;
		}
		&.navbar-show {
			.right-item {
				color: #666;
			}
		}
	}
}
</style>
