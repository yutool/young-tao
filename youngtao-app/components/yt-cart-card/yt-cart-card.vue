<template>
	<yt-card class="yt-cart-card">
		<view>
			<!-- 店名 -->
			<view class="cart-shop">
				<u-checkbox v-model="value"></u-checkbox>
				<text class="shop-name">{{data.shopName}} &gt;</text>
				<text class="shop-ticket">领券</text>
			</view>
			<!-- 商品列表 -->
			<view class="cart-list">
				<view v-for="sku in data.skuList" :key="sku.skuId" class="product-item">
					<view class="item-checkbox">
						<u-checkbox v-model="value"></u-checkbox>
					</view>
					<view class="item-content" @click="toProduct(sku.spuId)">
						<!-- 商品图片 -->
						<view class="content-img">
							<image :src="sku.image"></image>
						</view>
						<!-- 商品详情 -->
						<view class="content-detail">
							<view class="detail-name">{{sku.spu}}</view>
							<view class="detail-spec" @click.stop="handleClick(sku)">
								<text>{{Object.values(sku.sku).join("; ")}}</text>
							</view>
							<view class="price-box">
								<view class="price-text">￥{{sku.price}}</view>
								<u-number-box v-model="sku.num" input-width="70" input-height="40"></u-number-box>
							</view>
						</view>
					</view>
					<!-- 弹出层 -->
					<yt-sku-popup :visible="show" :product="product" :selected="selectedSku" :type="1" 
						@close="show = false">
					</yt-sku-popup>
				</view>
			</view>
		</view>
	</yt-card>
</template>

<script>
	export default {
		props: ['data'],
		data() {
			return {
				value: false,
				show: false,
				product: {},
				selectedSku: {}
			}
		},
		methods: {
			handleClick(sku) {
			},
			toProduct(spuId) {
				console.log('spuId', spuId)
				uni.navigateTo({
				  url: `/pages/product/product?spuId=${spuId}`
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
.yt-cart-card {
	.cart-shop {
		display: flex;
		align-items: center;
		padding: 20rpx 0;
		.shop-name {
			flex: 1;
		}
	}
	.cart-list {
		.product-item {
			display: flex;
			padding: 20rpx 0;
			.item-checkbox {
				align-self: center;
			}
			.item-content {
				display: flex;
			}
		}
		.content-img {
			margin-right: 20rpx;
			image {
				width: 160rpx;
				height: 160rpx;
				border-radius: 10rpx;
			}
		}
		.content-detail {
			flex: 1;
			display: flex;
			flex-direction: column;
			justify-content: space-around;
		}
		.detail-spec {
			background-color: #eee;
			padding: 6rpx 20rpx;
			border-radius: 10rpx;
			width: 400rpx;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		.price-box {
			display: flex;
			justify-content: space-between;
			align-items: center;
			.price-text {
				font-size: 36rpx;
				color: $primary-color;
			}
		}
	}
}
</style>
