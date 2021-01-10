<template>
	<!-- 屏蔽自带的关闭功能，手动关闭 -->
	<u-popup :value="visible" :mask-close-able="false" mode="bottom" border-radius="20" @open="open">
		<view class="yt-sku-popup" v-if="JSON.stringify(selectedSku) !== '{}'">
			<view class="selected-wrap">
				<view class="info-box" v-if="selectedSku.skuId">
					<view>
						<image class="image" :src="selectedSku.images[0]"></image>
					</view>
					<view class="price-box">
						<view class="price">￥<text>{{selectedSku.price}}</text></view>
						<view class="number">编号：{{selectedSku.skuId}}</view>
					</view>
				</view>
				<view class="info-box" v-else>
					<view>
						<image class="image" src=""></image>
					</view>
					<view class="price-box">
						暂无该型号，快叫掌柜补货吧
					</view>
				</view>
				<text class="close-btn" @click="handleClose">x</text>
			</view>
			<scroll-view scroll-y="true" class="content-wrap">
				<!-- sku -->
				<view class="sku-box" v-for="(value, key) in spu.skuTemplate" :key="key">
					<view class="box-title">{{key}}</view>
					<view class="content">
						<view v-for="item in value" @click="handleChoose(key, item)"
						class="item" :class="selectedSku.sku[key] === item ? 'active' : ''">
							{{item}}
						</view>
					</view>
				</view>
				<!-- 数量 -->
				<view class="number-box">
					<view class="box-title">数量：{{selectedSku.num || 0}}</view>
					<u-number-box :min="1" :max="selectedSku.num || 1"></u-number-box>
				</view>
				<!-- 服务 -->
				<view class="serve-box" v-if="type === 2">
					<view class="box-title">服务保障</view>
					<view class="content">
						<view class="title">促销特惠</view>
						<view class="list-item">
							<view class="item" v-for="i in 5">
								<view class="text">1年免费换新</view>
								<view class="line"></view>
								<view class="money">￥12</view>
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
		props: ['visible', 'spu', 'selected', 'type'],
		data() {
			return {
				selectedSku: {}
			};
		},
		onBackPress(options) {
			if (options.from === 'navigateBack' && this.visible) {
				this.handleClose();
				return true;
			}
		},
		methods: {
			handleChoose(key, value) {
				const tmp = JSON.parse(JSON.stringify(this.selectedSku.sku));
				tmp[key] = value;
				for (const sku of this.spu.skuList) {
					if (JSON.stringify(sku.sku) === JSON.stringify(tmp)) {
						this.selectedSku = sku;
						return;
					}
				}
				this.selectedSku = {
					sku: tmp
				}
			},
			handleClose() {
				this.$emit("close");
			},
			open() {
				this.selectedSku = this.selected;
			},
			confirm() {
				if (!this.selectedSku.skuId) {
					console.log('没有该商品')
					return;
				}
				this.handleClose();
				this.$emit("confirm", this.selectedSku);
			}
		}
	}
</script>

<style lang="scss" scoped>
.yt-sku-popup {
	padding: 30rpx 30rpx 20rpx;
	.selected-wrap {
		display: flex;
		margin-bottom: $module-margin;
		.info-box {
			flex: 1;
			display: flex;
			.image {
				border-radius: $border-radius;
				width: 150rpx;
				height: 150rpx;
				margin-right: 30rpx;
			}
			.price-box {
				align-self: center;
				.price {
					color: $primary-color;
					font-size: 40rpx;
				}
				.number {
					color: #666;
				}
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
	.content-wrap {
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
