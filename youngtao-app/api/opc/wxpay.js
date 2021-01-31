import request from '@/common/request'

export function wxAppPay(data) {
	return request.post({
		url: '/opc/wxpay/app',
		data: data
	})
}