<template>
	<view class="yt-create-order-goods">
		<!-- 店名 -->
		<view class="shop-name">
			罗技G旗舰店
		</view>
		<!-- 商品 -->
		<view class="goods-wrap">
			<view class="goods-box" v-for="i in 2" :key="i">
				<image class="image" src="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1620020012,789258862&fm=26&gp=0.jpg"></image>
				<view class="info">
					<view class="name">罗技G402游戏鼠标电竞机械竞技无线鼠标新品抢购</view>
					<view class="sku">sku</view>
					<view class="price-number">
						<view class="price">￥219.00</view>
						<view class="number">x1</view>
					</view>
					<view>支持七天无理由退货</view>
				</view>
			</view>
		</view>
		<!-- 配送 -->
		<view class="delivery-wrap">
			<view class="title">配送</view>
			<view class="content">
				<view>樱桃快递（送货上门）</view>
				<view>工作日、双休日与假日均可送货</view>
			</view>
			<view>&gt;</view>
		</view>
		<!-- 退货无忧 -->
		<view class="return-wrap">
			<view class="title">退货无忧</view>
			<view class="content">
				<view>商家赠送</view>
				<view>享运费补贴或免费取件服务</view>
			</view>
		</view>
		<!-- 留言 -->
		<view class="remark-wrap">
			<view class="title">留言</view>
			<input class="content" type="text" value="" placeholder="建议留言前先与商家沟通确认" />
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		}
	}
</script>

<style lang="scss" scoped>
.yt-create-order-goods {
	background-color: #fff;
	padding: 30rpx;
	.shop-name {
		margin-bottom: $module-margin;
	}
	.goods-wrap {
		margin-bottom: $module-margin;
		.goods-box {
			margin-bottom: $module-margin;
			display: flex;
			.image {
				width: 150rpx;
				height: 150rpx;
				margin-right: 30rpx;
			}
			.info {
				width: 520rpx;
				.name {
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
				.price-number {
					display: flex;
					.price {
						flex: 1;
					}
				}
			}
		}
	}
	.delivery-wrap {
		margin-bottom: $module-margin;
		display: flex;
		.content {
			flex: 1;
			text-align: right;
		}
	}
	.return-wrap {
		margin-bottom: $module-margin;
		display: flex;
		.content {
			flex: 1;
			text-align: right;
		}
	}
	.remark-wrap {
		display: flex;
		.title {
			margin-right: 30rpx;
		}
		.content {
			flex: 1;
			text-align: right;
		}
	}
}
</style>
