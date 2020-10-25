<template>
	<view class="content">
		<!-- 用户信息 -->
		<view class="user-wrap">
			<view class="userinfo-box">
				<image class="avatar" src="/static/images/missing-face.png"></image>
				<view>
					<text class="username">游客</text>
				</view>
			</view>
			<!-- vip卡片 -->
			<view class="vip-card-box">
				<view class="vip-title">
					<view class="vip-name">樱桃会员</view>
					<text class="buy-btn">立即开通</text>
				</view>
				<view class="module-bottom">Young Union</view>
				<view class="vip-synopsis">开通会员，购物返10倍樱桃</view>
				<image class="card-bg" src="/static/images/vip-card-bg.png" mode=""></image>
			</view>
		</view>
		
		<view class="cover-container" @touchstart="coverTouchstart" @touchmove="coverTouchmove" @touchend="coverTouchend"
			:style="[{ transform: coverTransform, transition: coverTransition }]">
			<!-- 背景 -->
			<image class="arc" src="/static/images/arc.png"></image>
			
			<view class="yt-container">
				<!-- 用户订单 -->
				<view class="order-wrap">
					<view class="order-title" @click="goToOrder">
						<u-section :show-line="false" title="我的订单" sub-title="查看全部订单"></u-section>
					</view>
					<view class="order-box">
						<view class="order-item">
							<text class="iconfont icon-daifukuan item-icon"></text>
							<view class="item-text">代付款</view>
						</view>
						<view class="order-item">
							<text class="iconfont icon-daifahuo item-icon"></text>
							<view class="item-text">待成团</view>
						</view>
						<view class="order-item">
							<text class="iconfont icon-daishouhuo item-icon"></text>
							<view class="item-text">待收货</view>
						</view>
						<view class="order-item">
							<text class="iconfont icon-pingjia item-icon"></text>
							<view class="item-text">评价</view>
						</view>
						<view class="order-item">
							<text class="iconfont icon-shouhou item-icon"></text>
							<view class="item-text">退款/售后</view>
						</view>
					</view>
				</view>
				<!-- 功能列表 -->
				<u-cell-group class="function-list yt-u-cell">
					<view class="history-box">
						<view class="history-title">
							<text class="iconfont icon-yuyuelishishijian"></text>
							<text class="title-text">浏览历史</text>
						</view>
						<scroll-view class="history-scroll" scroll-x>
							<image v-for="i in 6" :key="i" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553105186633&di=c121a29beece4e14269948d990f9e720&imgtype=0&src=http%3A%2F%2Fimg004.hc360.cn%2Fm8%2FM04%2FDE%2FDE%2FwKhQplZ-QteEBvsbAAAAADUkobU751.jpg"></image>
						</scroll-view>
					</view>
					<u-cell-item title="我的收藏" :border-top="true">
						<text slot="icon" class="iconfont icon-collection-b"></text>
					</u-cell-item>
					<u-cell-item title="钱包">
						<text slot="icon" class="iconfont icon-qiandai"></text>
					</u-cell-item>
					<u-cell-item title="收货地址">
						<text slot="icon" class="iconfont icon-lishi"></text>
					</u-cell-item>
					<u-cell-item title="中奖纪录">
						<text slot="icon" class="iconfont icon-youhuiquan-"></text>
					</u-cell-item>
					<u-cell-item title="开通直播">
						<text slot="icon" class="iconfont icon-shezhi"></text>
					</u-cell-item>
					<u-cell-item title="设置">
						<text slot="icon" class="iconfont icon-shezhi"></text>
					</u-cell-item>
				</u-cell-group>
			</view>
		</view>
	</view>
</template>

<script>
	let startY = 0, moveY = 0, pageAtTop = true;
	export default {
		data() {
			return {
				coverTransform: 'translateY(0px)',
				coverTransition: '0s',
				moving: false
			}
		},
		methods: {
			/**
			 *  会员卡下拉和回弹
			 *  1.关闭bounce避免ios端下拉冲突
			 *  2.由于touchmove事件的缺陷（以前做小程序就遇到，比如20跳到40，h5反而好很多），下拉的时候会有掉帧的感觉
			 *    transition设置0.1秒延迟，让css来过渡这段空窗期
			 *  3.回弹效果可修改曲线值来调整效果，推荐一个好用的bezier生成工具 http://cubic-bezier.com/
			 */
			coverTouchstart(e) {
				if (pageAtTop === false) {
					return;
				}
				this.coverTransition = 'transform .1s linear';
				startY = e.touches[0].clientY;
			},
			coverTouchmove(e) {
				moveY = e.touches[0].clientY;
				let moveDistance = moveY - startY;
				if (moveDistance < 0){
					this.moving = false;
					return;
				}
				this.moving = true;
				if (moveDistance >= 80 && moveDistance < 100) {
					moveDistance = 80;
				}
					
				if (moveDistance > 0 && moveDistance <= 80) {
					this.coverTransform = `translateY(${moveDistance}px)`;
				}
			},
			coverTouchend() {
				if (this.moving === false) {
					return;
				}
				this.moving = false;
				this.coverTransition = 'transform 0.3s cubic-bezier(.21,1.93,.53,.64)';
				this.coverTransform = 'translateY(0px)';
			},
			goToOrder() {
				uni.navigateTo({
					url: "../order/order"
				})
			}
		}
	}
</script>

<style lang="scss">
// 用户信息
.user-wrap {
	padding: 100rpx 20rpx 0;
	background-image: url('/static/images/user-bg.jpg');
	background-repeat: no-repeat;
	background-size: 100% 100%;
	// 用户信息
	.userinfo-box {
		display: flex;
		align-items: center;
		margin-bottom: 50rpx;
		.avatar{
			width: 130rpx;
			height: 130rpx;
			border: 5rpx solid #fff;
			border-radius: 50%;
			margin-right: 20rpx;
		}
		.username{
			font-size: 36rpx;
		}
	}
	// vip卡片
	.vip-card-box {
		color: #f7d680;
		background: linear-gradient(left, rgba(0,0,0,.7), rgba(0,0,0,.8));
		border-radius: 16rpx 16rpx 0 0;
		overflow: hidden;
		position: relative;
		padding: 20rpx 20rpx 60rpx;
		.vip-title {
			margin-bottom: $module-margin;
			display: flex;
			align-items: center;
		}
		.buy-btn {
			padding: 6rpx 12rpx;
			font-size: 22rpx;
			color: #36343c;
			border-radius: 20px;
			background: linear-gradient(left, #f9e6af, #ffd465);
			z-index: 99;
		}
		.vip-name {
			flex: 1;
			color: #f7d680;
		}
		.vip-synopsis {
			margin-bottom: $module-margin;
			color: #d8cba9;
		}
		.card-bg {
			position: absolute;
			top: 0rpx;
			right: 0;
			width: 380rpx;
			height: 100%;
		}
	}
}
// 覆盖层
.cover-container {
	margin-top: -160rpx;
	margin-bottom: calc(var(--window-bottom) + 20rpx);
	padding-top: 10rpx;
	position: relative;
	background: #f5f5f5;
	.arc {
		position: absolute;
		left: 0;
		top: -35rpx;
		width: 100%;
		height: 36rpx;
	}
}
// 订单信息
.order-wrap {
	border-radius: $border-radius;
	margin-bottom: $module-margin;
	background-color: #fff;
	.order-title {
		padding: 20rpx;
		border-bottom: 2rpx solid #eee;
	}
	.order-box {
		display: flex;
		justify-content: space-around;
		padding: 20rpx 10rpx;
	}
	.order-item {
		text-align: center;
	}
	.item-icon {
		font-size: 60rpx;
	}
	.item-text {
		color: #333;
		font-size: 26rpx;
	}
}
// 功能列表
.function-list {
	.history-box {
		padding: 26rpx 32rpx;
	}
	.history-title {
		margin-bottom: $module-margin;
	}
	.title-text {
		font-size: 28rpx;
		color: #606266;
	}
	.history-scroll {
		white-space: nowrap;
		image {
			border-radius: $border-radius;
			width: 160rpx;
			height: 160rpx;
			margin-right: 20rpx;
		}
	}
	.iconfont {
		margin-right: 10rpx;
	}
}
</style>
