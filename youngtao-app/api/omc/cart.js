import request from '@/common/request'

export function addCart(data) {
	return request.post({
		url: `/omc/cart/add`,
		data
	})
}

export function getUserCart() {
	return request.get({
		url: `/omc/cart/getUserCart`,
	})
}