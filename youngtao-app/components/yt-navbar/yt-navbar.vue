<template>
	<view>
		<view class="goods-head" :style="'background: rgba(255,255,255,' + PageScrollTop / 100 + ')'">
		  <!-- 返回 -->
		  <view class="back" @click="onBack">
		    <view class="back-one" :class="{ action: PageScrollTop > 120 }">
		      <text></text>
		    </view>
		  </view>
		  <!-- tab切换 -->
		  <view class="head-tab" v-if="PageScrollTop > 120">
		    <view class="tab" :class="{'action':TabShow===0}">
		      <text>商品</text>
		      <text class="line"></text>
		    </view>
		    <view class="tab" :class="{'action':TabShow===1}">
		      <text>评价</text>
		      <text class="line"></text>
		    </view>
		    <view class="tab" :class="{'action':TabShow===2}">
		      <text>详情</text>
		      <text class="line"></text>
		    </view>
		  </view>
		  <!-- 分享更多 -->
		  <view class="share-more">
		    <view class="share-more-one" :class="{ action: PageScrollTop > 120 }">
		      <view class="list">
		        <text class="iconfont icon-huaban"></text>
		      </view>
		      <view class="list">
		        <text class="iconfont icon-huaban"></text>
		      </view>
					<view class="list">
					  <text class="iconfont icon-huaban"></text>
					</view>
		    </view>
		  </view>
		</view>
	
		<view v-for="i in 20" :key="i" style="padding: 30rpx;">a</view>
	</view>
</template>

<script>
export default {
  data() {
    return {
      TabShow: 0,
      PageScrollTop: 0,
    };
  },
	onPageScroll(e) {
		this.PageScrollTop = e.scrollTop;
		console.log(this.PageScrollTop)
	},
  methods: {
  }
};
</script>

<style lang="scss" scoped>
.goods-head {
	position: fixed;
	left: 0;
	top: 0;
	z-index: 1000;
	display: flex;
	align-items: center;
	justify-content: center;
	width: 100%;
	height: 100rpx;
	background: rgba(255, 255, 255, 0);
	/* #ifdef APP-PLUS */
	height: calc(100rpx + var(--status-bar-height));
	/* #endif */
	/* #ifdef MP */
	height: 200rpx;
	/* #endif */
	.back {
		position: absolute;
		left: 0;
		top: 0;
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100rpx;
		height: 100%;
		/* #ifdef APP-PLUS */
		padding-top: 50rpx;
		/* #endif */
		/* #ifdef MP */
		padding-top: 100rpx;
		/* #endif */
		// 返回
		.back-one {
			display: flex;
			align-items: center;
			justify-content: center;
			width: 50rpx;
			height: 50rpx;
			background-color: rgba(0,0,0,0.3);
			border-radius: 100%;
			text {
				display: flex;
				width: 20rpx;
				height: 20rpx;
				border-left: 2rpx solid #FFFFFF;
				border-bottom: 2rpx solid #FFFFFF;
				transform: rotate(45deg);
			}
		}
		.action{
			background-color: transparent;
			text{
				border-color: #555555;
			}
		}
	}
	// tab切换
	.head-tab{
		display: flex;
		align-items: center;
		height: 100%;
		/* #ifdef APP-PLUS */
		padding-top: 50rpx;
		/* #endif */
		/* #ifdef MP */
		padding-top: 100rpx;
		/* #endif */
		.tab{
			position: relative;
			margin: 0 20rpx;
			padding: 0 10rpx;
			text{
				color: #555555;
				font-size: 26rpx;
			}
		}
		.action{
			text {
				color: #212121;
				font-size: 28rpx;
			}
			.line {
				position: absolute;
				left: 0;
				bottom: -10rpx;
				width: 100%;
				height: 6rpx;
				background: linear-gradient(to right, red, rgba(255,255,255,0.3));
			}
		}
	}
	// 分享更多
	.share-more{
		position: absolute;
		right: 0;
		top: 0;
		width: 140rpx;
		height: 100%;
		/* #ifdef APP-PLUS */
		padding-top: 50rpx;
		/* #endif */
		/* #ifdef MP */
		padding-top: 100rpx;
		/* #endif */
		.share-more-one{
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding-right: 20rpx;
			height: 100%;
			.list{
				display: flex;
				align-items: center;
				justify-content: center;
				width: 50rpx;
				height: 50rpx;
				background-color: rgba(0,0,0,0.3);
				border-radius: 100%;
				text{
					font-size: 28rpx;
					color: #FFFFFF;
				}
			}
		}
		.action{
			.list{
				background-color: transparent;
				text{
					color: #555555;
				}
			}
		}
	}
}
</style>
