<template>
	<view>
		<view class="yt-order-card" v-for="order in data" :key="order.id">
			<view class="shop-box">
				<view class="shop-name">
					<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
					<view class="name">{{ order.store }}</view>
					<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
				</view>
				<view class="order-state">{{ order.deal }}</view>
			</view>
			<view class="item-box" v-for="(item, index) in order.goodsList" :key="index">
				<view class="image"><image :src="item.goodsUrl" mode="aspectFill"></image></view>
				<view class="content">
					<view class="title u-line-2">{{ item.title }}</view>
					<view class="type">{{ item.type }}</view>
					<view class="delivery-time">发货时间 {{ item.deliveryTime }}</view>
				</view>
				<view class="price-box">
					<view class="price">
						￥{{ item.price }}
					</view>
					<view class="number">x{{ item.number }}</view>
				</view>
			</view>
			<view class="total-box">
				共{{ order.goodsList.length }}件商品 合计:
				<text class="total-price">
					￥ 100.00
				</text>
			</view>
			<view class="function-box">
				<view class="more"><u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon></view>
				<view class="btn-list">
					<view class="logistics btn">查看物流</view>
					<view class="exchange btn">卖了换钱</view>
					<view class="evaluate btn">评价</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['data'],
		methods: {
			// 价格小数
			priceDecimal() {
				return val => {
					if (val !== parseInt(val)) return val.slice(-2);
					else return '00';
				};
			},
			// 价格整数
			priceInt() {
				return val => {
					if (val !== parseInt(val)) return val.split('.')[0];
					else return val;
				};
			},
			// 总价
			totalPrice(item) {
				let price = 0;
				item.map(val => {
					price += parseFloat(val.price);
				});
				return price.toFixed(2);
			},
			// 总件数
			totalNum(item) {
				let num = 0;
				item.map(val => {
					num += val.number;
				});
				return num;
			},
		}
	}
</script>

<style lang="scss" scoped>
.yt-order-card {
	width: 710rpx;
	background-color: #ffffff;
	margin: 20rpx auto;
	border-radius: 20rpx;
	box-sizing: border-box;
	padding: 20rpx;
	font-size: 28rpx;
	.shop-box {
		display: flex;
		justify-content: space-between;
		.shop-name {
			display: flex;
			align-items: center;
			.name {
				margin: 0 20rpx;
				font-size: 32rpx;
				font-weight: bold;
			}
		}
		.order-state {
			color: $u-type-warning-dark;
		}
	}
	.item-box {
		display: flex;
		margin: 20rpx 0 0;
		.image {
			margin-right: 20rpx;
			image {
				width: 200rpx;
				height: 200rpx;
				border-radius: 10rpx;
			}
		}
		.content {
			.title {
				font-size: 28rpx;
				line-height: 50rpx;
			}
			.type {
				margin: 10rpx 0;
				font-size: 24rpx;
				color: $u-tips-color;
			}
			.delivery-time {
				color: #e5d001;
				font-size: 24rpx;
			}
		}
		.price-box {
			margin-left: 10rpx;
			padding-top: 10rpx;
			text-align: right;
			.price {
				font-size: 24rpx;
			}
			.number {
				color: $u-tips-color;
				font-size: 24rpx;
			}
		}
	}
	.total-box {
		margin-top: 20rpx;
		text-align: right;
		font-size: 24rpx;
		.total-price {
			font-size: 32rpx;
		}
	}
	.function-box {
		display: flex;
		margin-top: 40rpx;
		padding: 0 10rpx;
		align-items: center;
		.more {
			flex: 1;
		}
		.btn-list {
			display: flex;
			.btn {
				line-height: 52rpx;
				width: 160rpx;
				border-radius: 26rpx;
				border: 2rpx solid $u-border-color;
				font-size: 26rpx;
				text-align: center;
				color: $u-type-info-dark;
				margin-left: 20rpx;
			}
			.evaluate {
				color: $u-type-warning-dark;
				border-color: $u-type-warning-dark;
			}
		}
	}
}
</style>
