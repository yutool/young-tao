<template>
	<view class="container">
		<!-- 导航栏 -->
		<u-navbar class="goods-navbar" :immersive="true" :border-bottom="false" :background="{backgroundColor: 'rgba(255,255,255,' + navbar.opacity + ')'}">
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
			</view>
		</u-navbar>
		
		<!-- 商品信息 -->
		<view class="goods-info-wrap">
			<u-swiper :list="list" border-radius="0" height="750" mode="number" indicator-pos="bottomRight"></u-swiper>
			<!-- 商品价格 -->
			<view class="price-box">
				<view class="price-left">
					<view class="price">￥200.00</view>
					<view class="old-price">￥200.00</view>
				</view>
				<view class="price-right">
					<view class="item">
						<view class="iconfont icon-huaban"></view>
						<view>降价通知</view>
					</view>
					<view class="item">
						<view class="iconfont icon-huaban"></view>
						<view>收藏</view>
					</view>
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
		
		<!-- 优惠、服务 -->
		<view class="serve-wrap wrap">
			<view class="discount-box box">
				<view class="title">优惠</view>
				<view class="content">
					<view class="item">
						<u-tag class="item-tag" text="樱桃新人" type="error" size="mini"/>
						<text>立减70元优惠券</text>
					</view>
					<view class="item">
						<u-tag class="item-tag" text="领券" type="error" size="mini"/>
						<u-tag class="ticket" text="满99减15" type="error" size="mini"/>
						<u-tag class="ticket" text="满59减10" type="error" size="mini"/>
					</view>
					<view class="item">
						<u-tag class="item-tag" text="换购" type="error" size="mini"/>
						<text>购买1件既可换购热销商品</text>
					</view>
				</view>
				<view>...</view>
			</view>
			<view class="serve-box box border-top">
				<view class="title">服务</view>
				<view class="content">
					<u-tag class="item" text="假一赔三" type="primary" size="mini"/>
					<u-tag class="item" text="退货运费险" type="primary" size="mini"/>
					<u-tag class="item" text="意外保修" type="primary" size="mini"/>
				</view>
				<view>...</view>
			</view>
		</view>
		
		<!-- 配送 -->
		<view class="delivery-wrap wrap">
			<view class="selected-box box">
				<view class="title">已选</view>
				<view class="content">
					G304 无线游戏鼠标 黑色，0.185kg，1件
				</view>
				<view>...</view>
			</view>
			<view class="ship-box box border-top">
				<view class="title">发货</view>
				<view class="content">
					<view>
						<text>浙江杭州</text>
						<text class="mr-2 ml-2"> | </text>
						<text>配送：快递包邮</text>
					</view>
				</view>
				<view>...</view>
			</view>
			<view class="destination-box box border-top">
				<view class="title">送至</view>
				<view class="content">
					<view>收货地址xxx</view>
					<view><text>现货</text>，23:00前下单，预计10月09日送达</view>
				</view>
				<view>...</view>
			</view>
		</view>
		
		<!-- 评价 -->
		<view class="comment-wrap wrap">
			<!-- 评价 -->
			<view class="comment-box box">
				<view class="title">
					<u-section title="宝贝评价(999)" sub-title="好评度90%" :show-line="false"></u-section>
				</view>
				<view class="tag">
					<u-tag class="mr-3" text="方便快捷(99)" type="error" shape="circle" size="mini" />
					<u-tag class="mr-3" text="倍感舒适(99)" type="error" shape="circle" size="mini" />
					<u-tag class="mr-3" text="放映灵敏(99)" type="error" shape="circle" size="mini" />
				</view>
				<view class="content">
					评价内容
				</view>
			</view>
			<!-- 问大家 -->
			<view class="answer-box box border-top">
				<view class="title">
					<u-section title="问大家" sub-title="查看全部" :show-line="false"></u-section>
				</view>
				<view class="content">
					<view class="item">
						<u-tag class="mr-3" text="问" type="warning" size="mini" mode="dark" />
						<view class="text">这个物品最低多少钱</view>
						<view>10个回答</view>
					</view>
					<view class="item">
						<u-tag class="mr-3" text="问" type="warning" size="mini" mode="dark" />
						<view class="text">这个物品最低多少钱</view>
						<view>10个回答</view>
					</view>
				</view>
			</view>
		</view>
		
		<!-- 店铺 -->
		<view class="shop-wrap wrap">
			<view class="shop-box box">
				<view class="image">
					<image src="http://img11.360buyimg.com/cms/jfs/t19942/13/892321196/28794/11b3ba2d/5b0e425dN7309fe32.jpg"></image>
				</view>
				<view class="content">
					<view class="name">罗技G官方旗舰店</view>
					<view class="rate">
						<u-tag class="mr-3" text="樱桃" type="error" shape="circle" size="mini" mode="dark" />
						<view>
							<text>综合体验</text>
							<u-rate size="20" gutter="2"></u-rate>
						</view>
					</view>
				</view>
				<view class="evaluate">
					<view>宝贝描述5.0 <text>高</text></view>
					<view>卖家服务5.0 <text>高</text></view>
					<view>物流服务5.0 <text>高</text></view>
				</view>
			</view>
			<view class="recommend-box box border-top">
				<view class="title">
					<u-section title="店铺推荐" sub-title="查看全部" :show-line="false"></u-section>
				</view>
				<view class="content">
					...
				</view>
			</view>
		</view>
		
		<!-- 详情 -->
		<view class="detail-wrap wrap">
			<view class="detail-box box">
				<u-divider>宝贝详情</u-divider>
				<view>...</view>
			</view>
		</view>
		
		<!-- 推荐 -->
		<view class="recommend-wrap wrap">
			<view class="recommend-box box">
				<u-divider>看了又看</u-divider>
				<view>...</view>
			</view>
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
$edge-width: 30rpx;
.container {
	margin-bottom: calc(var(--window-bottom) + 130rpx);
}
.wrap {
	margin-bottom: $module-margin;
	background-color: #FFF;
	border-radius: $border-radius;
}
.box {
	margin: 0 $edge-width;
	padding: $edge-width 0;
}
.border-top {
	border-top: 1px solid rgba(0, 0, 0, 0.03);
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
	background-color: #FFF;
	border-radius: 0 0 $border-radius $border-radius;
	.price-box {
		padding: 20rpx;
		display: flex;
		.price-left {
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
			display: flex;
			text-align: center;
			.item {
				margin-right: 10rpx;
			}
		}
	}
	.title-box {
		padding: 0 20rpx 20rpx;
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
	.discount-box {
		display: flex;
		.title {
			font-weight: bolder;
			margin-right: 30rpx;
		}
		.content {
			flex: 1;
			.item {
				margin-bottom: 20rpx;
			}
			.item-tag {
				font-size: 22rpx;
				padding: 0 10rpx;
				margin-right: 20rpx;
				border: none;
			}
			.ticket {
				margin-right: 20rpx;
			}
		}
	}
	.serve-box {
		display: flex;
		align-items: center;
		.title {
			font-weight: bolder;
			margin-right: 30rpx;
		}
		.content {
			flex: 1;
			.item {
				margin-right: 20rpx;
				padding: 2rpx 10rpx;
			}
		}
	}
}
.delivery-wrap {
	.title {
		font-weight: bolder;
		margin-right: 20rpx;
	}
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
	.comment-box {
		.title {
			margin-bottom: 20rpx;
		}
		.tag {
			margin-bottom: 20rpx;
		}
	}
	.answer-box {
		.title {
			margin-bottom: 20rpx;
		}
		.content {
			.item {
				display: flex;
				margin-bottom: 10rpx;
			}
			.text {
				flex: 1;
			}
		}
	}
}
.shop-wrap {
	.shop-box {
		display: flex;
		.image {
			margin-right: 30rpx;
			image {
				width: 120rpx;
				height: 120rpx;
			}
		}
		.content {
			flex: 1;
			.name {
				font-size: 36rpx;
				margin-bottom: 10rpx;
			}
			.rate {
				display: flex;
				align-items: center;
			}
		}
		.evaluate {
			font-size: 24rpx;
			color: #999;
		}
	}
	.recommend-box {
		.title {
			margin-bottom: 20rpx;
		}
	}
}
.detail-wrap {
}
.recommend-wrap {
}
.goods-bar {
	position: fixed;
	bottom: var(--window-bottom);
}
</style>
