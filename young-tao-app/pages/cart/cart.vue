<template>
	<view>
		<yt-cart-card></yt-cart-card>
		<yt-cart-card></yt-cart-card>
		<yt-cart-card></yt-cart-card>
		
		<view class="cart-pay">
			<yt-cart-pay></yt-cart-pay>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
		}
	}
</script>

<style lang="scss" scoped>
.cart-pay {
	position: fixed;
	bottom: var(--window-bottom);
}
</style>
