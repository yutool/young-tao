import request from '@/common/request'

export function getByPaymentId(id) {
	return request.get({
		url: `/opc/orderPayRecord/get/${id}`
	})
}