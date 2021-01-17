<template>
	<view class="yt-create-order-goods">
		<!-- 店名 -->
		<view class="shop-wrap">
			{{data.shopName}}
		</view>
		<!-- 商品 -->
		<view class="product-wrap">
			<view class="product-box" v-for="sku in data.skuList" :key="sku.skuId">
				<image class="product-image" :src="sku.image"></image>
				<view class="product-info">
					<view class="name">{{sku.spu}}</view>
					<view class="sku">
						<template v-for="(val, key) of sku.sku">{{val}}:{{key}}; </template>
					</view>
					<view class="price-box">
						<view class="price">￥{{sku.price}}</view>
						<view class="number">
							<u-number-box v-model="sku.count" :min="1" :max="1000" input-height="30"></u-number-box>
						</view>
					</view>
					<view>支持七天无理由退货</view>
				</view>
			</view>
		</view>
		<!-- 服务 -->
		<view class="serve-wrap">
			<!-- 配送 -->
			<view class="box send-box">
				<view class="title">配送</view>
				<view class="content">
					<view>樱桃快递，送货上门</view>
					<view>工作日、双休日与假日均可送货</view>
				</view>
			</view>
			<!-- 退货无忧 -->
			<view class="box return-box">
				<view class="title">退货无忧</view>
				<view class="content">
					<view>商家赠送</view>
					<view>享运费补贴或免费取件服务</view>
				</view>
			</view>
			<!-- 运费 -->
			<view class="box postage-box">
				<view class="title">运费（总量0.565kg)</view>
				<view class="content">￥0.00</view>
			</view>
			<!-- 留言 -->
			<view class="box remark-box">
				<view class="title">留言</view>
				<input class="content" type="text" value="" placeholder="建议留言前先与商家沟通确认" />
			</view>
			<view class="box money-box">
				<view class="content">
					小计：￥{{totalMoney}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['data'],
		computed: {
			totalMoney: function() {
				var sum = 0;
				for (const item of this.data.skuList) {
					sum += item.price * item.count;
				}
				return sum;
			}
		}
	}
</script>

<style lang="scss" scoped>
.yt-create-order-goods {
	background-color: #fff;
	padding: 30rpx;
	.shop-wrap {
		margin-bottom: $module-margin;
	}
	.product-wrap {
		margin-bottom: $module-margin;
		.product-box {
			margin-bottom: $module-margin;
			display: flex;
			.product-image {
				width: 150rpx;
				height: 150rpx;
				margin-right: 30rpx;
			}
			.product-info {
				width: 520rpx;
				.name {
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
					margin-bottom: 10rpx;
				}
				.sku {
					background-color: #efefef;
					margin-bottom: 10rpx;
					border-radius: 10rpx;
					padding: 6rpx 6rpx;
				}
				.price-box {
					display: flex;
					margin-bottom: 10rpx;
					.price {
						flex: 1;
					}
				}
			}
		}
	}
	.serve-wrap {
		.box {
			display: flex;
			margin-bottom: $module-margin;
			.content {
				flex: 1;
				text-align: right;
			}
		}
		.box:last-child {
			margin-bottom: 0;
		}
		.remark-box {
			.title {
				margin-right: 30rpx;
			}
		}
	}
}
</style>
