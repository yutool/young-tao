import request from '@/common/request'

export function createOrder(data) {
	return request.post({
		url: `/omc/order/create`,
		data
	})
}