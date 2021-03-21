import request from '@/common/request'

export function gscCreateOrder(data) {
	return request.post({
		url: `/gsc/order/create`,
		data
	})
}