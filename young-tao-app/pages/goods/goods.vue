<template>
	<view class="container">
		<!-- 导航栏 -->
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
				<!-- #ifndef MP -->
				<view class="right-item" :style="'backgroundColor: rgba(0,0,0, '+(0.3-navbar.opacity)+')'">
					<text class="iconfont icon-huaban"></text>
				</view>
				<!-- #endif -->
				<view class="right-item" :style="'backgroundColor: rgba(0,0,0, '+(0.3-navbar.opacity)+')'">
					<text class="iconfont icon-huaban"></text>
				</view>
				<view class="right-item" :style="'backgroundColor: rgba(0,0,0, '+(0.3-navbar.opacity)+')'">
					<text class="iconfont icon-huaban"></text>
				</view>
			</view>
		</u-navbar>
		
		<!-- 商品信息 -->
		<view class="goods-info-wrap">
			<u-swiper :list="list" border-radius="0" height="750" mode="number" indicator-pos="bottomRight"></u-swiper>
			<view class="goods-info-box">
				<!-- 商品价格 -->
				<view class="price-wrap">
					<view class="price-box">
						<view class="price">￥200.00</view>
						<view class="old-price">￥200.00</view>
					</view>
					<view class="price-right">
						<view class="iconfont icon-huaban"></view>
						<view>降价通知</view>
					</view>
					<view class="price-right">
						<view class="iconfont icon-huaban"></view>
						<view>收藏</view>
					</view>
				</view>
				<!-- 商品标题 -->
				<view class="title-box">
					<u-tag class="title-tag" text="新品" type="success" size="mini" mode="dark" />
					<text class="title">
						vivo iQOO Z1x 水漾白 6GB+128GB 骁龙765G 120Hz竞速屏 5000mAh超大电池 双模5G全网通手机 iqooz1x
					</text>
				</view>
			</view>
		</view>
		
		<!-- 优惠、服务 -->
		<view class="serve-wrap">
			<view class="discount-box">
				<view>优惠</view>
				<view class="discount-content">
					<view>樱桃立减70元优惠券</view>
					<view>满99-19  满49-10</view>
					<view>goumai 1件既可换购热销商品</view>
				</view>
				<view>...</view>
			</view>
			<view class="serve-box">
				<view>服务</view>
				<view class="serve-content">
					<text>假一赔三</text>
					<text>只换不修</text>
				</view>
				<view>...</view>
			</view>
		</view>
		
		<!-- 配送 -->
		<view class="delivery-wrap">
			<view class="selected-box">
				<view>已选</view>
				<view class="content">
					xxxx
				</view>
				<view>...</view>
			</view>
			<view class="ship-box">
				<view>发货</view>
				<view class="content">
					xxxx
				</view>
				<view>...</view>
			</view>
			<view class="destination-box">
				<view>送至</view>
				<view class="content">
					xxxx
				</view>
				<view>...</view>
			</view>
		</view>
		
		<!-- 评价 -->
		<view class="comment-wrap">
			<!-- 评价 -->
			<view class="comment-box">
				<u-section title="评价" sub-title="好评度90%"></u-section>
				<view>标签1、标签2</view>
				<view>
					评价内容
				</view>
			</view>
			<!-- 问大家 -->
			<view class="answer-box">
				<u-section title="问大家" sub-title="查看全部"></u-section>
				<view>list</view>
				<view>list</view>
			</view>
		</view>
		
		<!-- 店铺 -->
		<view class="shop-wrap">
			<view class="shop-box">
				<view class="image">
					image
				</view>
				<view class="content">
					<view>店名</view>
					<view>
						<text>樱桃</text>
						综合体验 ...
					</view>
				</view>
				<view class="evaluate">
					<view>宝贝描述5.0高</view>
					<view>卖家服务5.0高</view>
					<view>物流服务5.0高</view>
				</view>
			</view>
			<view class="recommend-box">
				<u-section title="店铺推荐" sub-title="查看全部"></u-section>
				<view>xxx</view>
			</view>
		</view>
		
		<!-- 详情 -->
		<view class="detail-wrap">
			<u-divider>宝贝详情</u-divider>
			<view>...</view>
		</view>
		
		<!-- 推荐 -->
		<view class="recommend-wrap">
			<u-divider>看了又看</u-divider>
			<view>...</view>
		</view>
		
		<view class="goods-bar">
			<yt-goodsbar></yt-goodsbar>
		</view>
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
			},
			list: [
				{ image: 'https://cdn.uviewui.com/uview/swiper/1.jpg' },
				{ image: 'https://cdn.uviewui.com/uview/swiper/2.jpg' },
				{ image: 'https://cdn.uviewui.com/uview/swiper/3.jpg' }
			],
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
.container {
	margin-bottom: calc(var(--window-bottom) + 125rpx);
}
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
.goods-info-wrap {
	margin-bottom: $module-margin;
	.goods-info-box {
		background-color: #FFF;
		padding: 20rpx;
	}
	.price-wrap {
		margin-bottom: $module-margin;
		display: flex;
	}
	.price-box {
		flex: 1;
		display: flex;
		align-items: baseline;
		.price {
			font-size: 60rpx;
			color: $primary-color;
			margin-right: 10rpx;
		}
		.old-price {
			font-size: 26rpx;
			text-decoration: line-through;
		}
	}
	.price-right {
		text-align: center;
		margin-right: 10rpx;
	}
	.title-box {
		.title-tag {
			margin-right: 10rpx;
		}
		.title {
			font-size: 32rpx;
			color: #262626;
			font-weight: bolder;
		}
	}
}
.serve-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	padding: 20rpx;
	.discount-box {
		display: flex;
		.discount-content {
			flex: 1;
		}
	}
	.serve-box {
		display: flex;
		.serve-content {
			flex: 1;
		}
	}
}
.delivery-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	padding: 20rpx;
	.selected-box {
		display: flex;
		.content {
			flex: 1;
		}
	}
	.ship-box {
		display: flex;
		.content {
			flex: 1;
		}
	}
	.destination-box {
		display: flex;
		.content {
			flex: 1;
		}
	}
}
.comment-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	padding: 20rpx;
	.comment-box {
	}
	.answer-box {
	}
}
.shop-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	padding: 20rpx;
	.shop-box {
		display: flex;
		.content {
			flex: 1;
		}
	}
}
.shop-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	padding: 20rpx;
}
.detail-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	padding: 20rpx;
}
.recommend-wrap {
	margin-bottom: $module-margin;
	background-color: #fff;
	padding: 20rpx;
}
.goods-bar {
	position: fixed;
	bottom: var(--window-bottom);
}
</style>
