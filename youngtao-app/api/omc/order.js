import request from '@/common/request'

export function createOrder(data) {
	return request.post({
		url: `/omc/order/create`,
		data
	})
}

export function queryStatus(paymentId) {
	return request.post({
		url: '/omc/order/queryStatus',
		data: {id: paymentId}
	})
}

export function getUserOrder(data) {
	return request.post({
		url: '/omc/order/getUserOrder',
		data
	})
}