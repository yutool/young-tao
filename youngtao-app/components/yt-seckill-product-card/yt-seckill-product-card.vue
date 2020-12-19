<template>
	<yt-card>
		<view class="yt-seckill-product-card" @click="onclick">
			<view class="product-image">
				<image :src="item.image"></image>
			</view>
			<view class="product-info">
				<!-- 标题 -->
				<view class="title-box">
					<view class="title"> {{item.title}} </view>
					<view class="description">免息 | 前10送惊喜大礼包</view>
				</view>
				<view class="info-bottom">
					<view class="price-wrap">
						<!-- 价格 -->
						<view class="price-box">
							<view>
								<span class="price">￥{{item.price}}</span> 
								<span class="old-price">￥{{item.oldPrice}}</span>
							</view>
							<view class="description">xxx历史最低价</view>
						</view>
						<!-- 抢购按钮 -->
						<view class="buy-box">
							<view class="text">去抢购</view>
							<view class="items-between">
								<u-line-progress class="flex-1" height="10" :percent="70" active-color="#fff" inactive-color="rgba(255,255,255,0.2)">
								</u-line-progress>
								<view class="percent">70%</view>
							</view>
						</view>
					</view>
					<!-- 店铺 -->
					<view class="shop-box items-between">
						<view>apple官方旗舰店</view>
						<view>...</view>
					</view>
				</view>
			</view>
		</view>
	</yt-card>
</template>

<script>
	export default {
		data() {
			return {
				item:	{
					image: 'https://img-cdn-qiniu.dcloud.net.cn/uploads/example/product1.jpg',
					title: 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机',
					price: 8888,
					oldPrice: 9999
				}
			};
		},
		methods: {
			onclick() {
				uni.navigateTo({url: '/pages/product/product'});
			}
		}
	}
</script>

<style lang="scss" scoped>
.yt-seckill-product-card {
	display: flex;
	justify-content: space-between;
	padding: 20rpx 0;
	// 图片
	.product-image {
		image {
			width: 200rpx;
			height: 200rpx;
		}
	}
	.product-info {
		width: 460rpx;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		// 标题
		.title-box {
			.title {
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
				margin-bottom: 6rpx;
			}
			.description {
				font-size: 22rpx;
			}
		}
		.info-bottom {
			.price-wrap {
				display: flex;
				justify-content: space-between;
				align-items: flex-end;
				margin-bottom: 6rpx;
				// 价格
				.price-box {
					.price {
						font-size: 36rpx;
						color: #fc4304;
						margin-right: 6rpx;
					}
					.old-price {
						color: #ccc;
						font-size: 20rpx;
						text-decoration: line-through;
					}
					.description {
						font-size: 20rpx;
						color: #aaa;
					}
				}
				// 抢购按钮
				.buy-box {
					color: #fff;
					width: 130rpx;
					padding: 10rpx 16rpx 12rpx;
					background-color: #fc4304;
					border-radius: 10rpx;
					text-align: center;
					.text {
						font-size: 20rpx;
						margin-bottom: 4rpx;
					}
					.percent {
						font-size: 10rpx;
						margin-left: 2rpx;
					}
				}
			}
			// 商店
			.shop-box {
				font-size: 22rpx;
				color: #666;
			}
		}
	}
}
</style>
