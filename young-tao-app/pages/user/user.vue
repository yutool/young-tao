<template>
	<view class="content">
		<!-- 用户信息 -->
		<view class="user-wrap">
			<view class="user-info">
				<u-avatar class="user-avatar" :src="src" mode="circle" size="120"></u-avatar>
				<text class="username">登录/注册 &gt;</text>
			</view>
			<u-grid :col="4" :border="false">
				<u-grid-item v-for="i in 4" :key="i" bg-color="none">
					<view>aaaa</view>
					<view class="grid-text">图片</view>
				</u-grid-item>
			</u-grid>
			<u-image width="100%" height="32rpx" src="@/static/images/arc.png" ></u-image>
		</view>
		<!-- 用户订单 -->
		<view class="yt-card">
			<u-grid :col="5" :border="false">
				<u-grid-item v-for="i in 5" :key="i" bg-color="none">
					<u-icon name="photo" :size="46"></u-icon>
					<view class="grid-text">图片</view>
				</u-grid-item>
			</u-grid>
		</view>
		<!-- 管理列表 -->
		<view class="yt-container">
			<u-cell-group>
				<u-cell-item icon="setting-fill" title="个人设置" v-for="i in 6" :key="i"></u-cell-item>
			</u-cell-group>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				src: 'http://pic2.sc.chinaz.com/Files/pic/pic9/202002/hpic2119_s.jpg'
			}
		}
	}
</script>

<style lang="scss">
.user-wrap {
	background-image: url(../../static/images/user.jpg);
	background-repeat: no-repeat;
	background-size: cover;
	
	.user-info {
		padding-top: 30px;
		padding-bottom: 20px;
		display: flex;
		align-items: center;
		.user-avatar {
			margin: 0 20rpx;
		}
		.username {
			color: #fff;
			font-size: 36rpx;
		}
	}
}
</style>
