<template>
	<view class="container">
		<!-- 标题 -->
		<u-navbar class="category-navbar" :is-back="false" :border-bottom="false" height="50" :background="{backgroundColor: '#f1f1f1'}">
			<view class="items-center">
				<text class="iconfont icon-saoyisao"></text>
				<u-search class="search-bar" placeholder="search" bg-color="#fff" :show-action="false"></u-search>
			</view>
			<view slot="right" class="mr-4">
				<text class="iconfont icon-huaban"></text>
			</view>
		</u-navbar>
		<view class="menu-wrap">
			<!-- 菜单 -->
			<scroll-view scroll-y scroll-with-animation class="menu-box menu-scroll-view">
				<view class="menu-item" v-for="(first, index) in categoryList" :key="first.categoryId" 
					 :class="[current==index ? 'menu-item-active' : '']" :data-current="index" @tap.stop="swichMenu(index)"
				>
					<text class="u-line-1">{{first.name}}</text>
				</view>
			</scroll-view>
			<!-- 商品 -->
			<block v-for="(first, index) in categoryList" :key="first.categoryId" >
				<scroll-view scroll-y class="goods-box" v-if="current==index">
					<view class="goods-item" v-for="second in first.children" :key="second.categoryId">
						<view class="item-title">
							<text>{{second.name}}</text>
						</view>
						<view class="item-container">
							<view class="item-card" v-for="third in second.children" :key="third.categoryId">
								<image class="item-menu-image" :src="third.icon" mode=""></image>
								<view class="item-menu-name">{{third.name}}</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</block>
		</view>
	</view>
</template>

<script>
	import { getCategory } from '@/api/gmc/category.js';
	
	export default {
		data() {
			return {
				categoryList: [],
				current: 0, // 预设当前项的值
				menuHeight: 0, // 左边菜单的高度
				menuItemHeight: 0, // 左边菜单item的高度
			}
		},
		methods: {
			getImg() {
				return Math.floor(Math.random() * 35);
			},
			// 点击左边的栏目切换
			async swichMenu(index) {
				if(index == this.current) return ;
				this.current = index;
				// 如果为0，意味着尚未初始化
				if(this.menuHeight == 0 || this.menuItemHeight == 0) {
					await this.getElRect('menu-scroll-view', 'menuHeight');
					await this.getElRect('menu-item', 'menuItemHeight');
				}
				// 将菜单菜单活动item垂直居中
				this.scrollTop = index * this.menuItemHeight + this.menuItemHeight / 2 - this.menuHeight / 2;
			},
			// 获取一个目标元素的高度
			getElRect(elClass, dataVal) {
				new Promise((resolve, reject) => {
					const query = uni.createSelectorQuery().in(this);
					query.select('.' + elClass).fields({size: true},res => {
						// 如果节点尚未生成，res值为null，循环调用执行
						if(!res) {
							setTimeout(() => {
								this.getElRect(elClass);
							}, 10);
							return ;
						}
						this[dataVal] = res.height;
					}).exec();
				})
			}
		},
		mounted() {
			getCategory().then(data => {
				this.categoryList = data.categoryList;
				console.log(this.categoryList)
			});
		}
	}
</script>

<style lang="scss" scoped>
.container {
	height: calc(100vh);
	/* #ifdef H5 */
	height: calc(100vh - var(--window-top));
	/* #endif */
	display: flex;
	flex-direction: column;
	padding-bottom: var(--window-bottom);
}
// 导航栏
.category-navbar {
	.search-bar {
		width: 530rpx;
		/* #ifdef MP */
		width: 340rpx;
		/* #endif */
	}
	.icon-saoyisao {
		font-size: 46rpx;
		margin-left: 20rpx;
		margin-right: 40rpx;
	}
	.icon-huaban {
		font-size: 50rpx;
	}
}
// 菜单
.menu-wrap {
	flex: 1;
	display: flex;
	overflow: hidden;
	.menu-box {
		width: 200rpx;
		height: 100%;
	}
	.menu-item {
		height: 110rpx;
		box-sizing: border-box;
		border-top-left-radius: 10rpx;
		border-bottom-left-radius: 10rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 26rpx;
		color: #444;
		font-weight: 400;
		line-height: 1;
	}
	.menu-item-active {
		position: relative;
		color: #000;
		font-size: 30rpx;
		font-weight: 600;
		background: #fff;
	}
	.menu-item-active::before {
		content: "";
		position: absolute;
		border-left: 4px solid $u-type-primary;
		height: 32rpx;
		left: 0;
		top: 39rpx;
	}
}
// 商品
.goods-box {
	background-color: #FFF;
	.goods-item {
		border-radius: $border-radius;
		padding: 20rpx;
	}
	.item-title {
		margin-bottom: $module-margin;
		font-size: 30rpx;
		font-weight: bolder;
	}
	.item-menu-name {
		font-size: 24rpx;
	}
	.item-container {
		display: flex;
		flex-wrap: wrap;
	}
	.item-card {
		margin-bottom: $module-margin;
		width: 33.333333%;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
	.item-menu-image {
		width: 120rpx;
		height: 120rpx;
	}
}
</style>
