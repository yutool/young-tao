<template>
	<!-- 屏蔽自带的关闭功能，手动关闭 -->
	<u-popup :value="visible" :mask-close-able="false" mode="bottom" border-radius="20">
		<view class="yt-sku-popup">
			<view class="basic-info">
				<image class="image" src="http://img10.360buyimg.com/mobilecms/s372x372_jfs/t1/112035/15/17621/129863/5f5ba84eE098a6257/ed106a5c3d02855d.jpg!q70.dpg.webp"></image>
				<view class="info-wrap">
					<view class="price-box">
						￥<text class="price">99</text>
					</view>
					<view class="number">编号：166666666</view>
				</view>
				<text class="close-btn" @click="handleClose">x</text>
			</view>
			<scroll-view scroll-y="true" class="goods-content">
				<view>
					<!-- sku -->
					<view class="sku-box" v-for="i in 2" :key="i">
						<view class="box-title">颜色</view>
						<view class="content">
							<view class="item" v-for="i in 6">G304升级Hero传感器</view>
						</view>
					</view>
					<!-- 数量 -->
					<view class="number-box">
						<view class="box-title">数量</view>
						<u-number-box :min="1" :max="100"></u-number-box>
					</view>
					<!-- 服务 -->
					<view class="serve-box">
						<view class="box-title">服务保障</view>
						<view class="content" v-for="i in 2" :key="i">
							<view class="title">促销特惠</view>
							<view class="list-item">
								<view class="item" v-for="i in 6">
									<view class="text">1年免费换新</view>
									<view class="line"></view>
									<view class="money">￥12</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
			<view>
				<button class="confirm-btn" @click="confirm">确定</button>
			</view>
		</view>
	</u-popup>
</template>

<script>
	export default {
		props: ['visible'],
		data() {
			return {
				
			};
		},
		onBackPress() {
			if (this.visible === true) {
				this.handleClose();
			}
			return true;
		},
		methods: {
			handleClose() {
				this.$emit("close");
			},
			confirm() {
				this.handleClose();
				this.$emit("confirm");
			}
		}
	}
</script>

<style lang="scss" scoped>
.yt-sku-popup {
	padding: 30rpx 30rpx 20rpx;
	.basic-info {
		display: flex;
		margin-bottom: $module-margin;
		.image {
			border-radius: $border-radius;
			width: 150rpx;
			height: 150rpx;
			margin-right: 30rpx;
		}
		.info-wrap {
			flex: 1;
			align-self: flex-end;
			.price-box {
				color: $primary-color;
				.price {
					font-size: 40rpx;
				}
			}
			.number {
				color: #666;
			}
		}
		.close-btn {
			font-size: 40rpx;
			height: 40rpx;
			width: 40rpx;
			text-align: center;
			color: #999;
		}
	}
	.goods-content {
		height: calc(60vh);
		margin-bottom: $module-margin;
		.box-title {
			font-weight: bolder;
			margin-bottom: $module-margin;
		}
		.sku-box {
			margin-bottom: $module-margin;
			.content {
				display: flex;
				flex-wrap: wrap;
				.item {
					margin: 0 10rpx 20rpx;
					padding: 10rpx 30rpx;
					background-color: #D3D3D3;
					color: #262626;
					background: #f2f2f2;
					border-radius: 30rpx;
				}
				.active {
					background: #fcedeb;
					border: 1px solid #f2270c;
					color: #f2270c;
				}
			}
		}
		.number-box {
			margin-bottom: $module-margin;
			display: flex;
			justify-content: space-between;
		}
		.serve-box {
			.title {
				margin-bottom: $module-margin;
			}
			.list-item {
				display: flex;
				flex-wrap: wrap;
				.item {
					margin: 0 10rpx 20rpx;
					padding: 10rpx 30rpx;
					background-color: #D3D3D3;
					color: #262626;
					background: #f2f2f2;
					border-radius: 30rpx;
					display: flex;
					align-items: center;
					.line {
						width: 1rpx;
						height: 20rpx;
						background-color: #999;
						margin: 0 15rpx;
					}
				}
				.active {
					background: #fcedeb;
					border: 1px solid #f2270c;
					color: #f2270c;
				}
			}
		}
	}
	.confirm-btn {
		background: linear-gradient(135deg,#f2140c,#f2270c 70%,#f24d0c);
		font-size: 32rpx;
		color: #FFF;
		border-radius: 100rpx;
	}
}
</style>
