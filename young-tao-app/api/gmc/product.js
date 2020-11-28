import request from '@/common/request'

export function getProduct(id) {
	return request.get({
		url: `/gmc/product/${id}`,
	})
}

export function confirmOrder(data) {
	return request.post({
		url: `/gmc/product/confirmOrder`,
		data
	})
}