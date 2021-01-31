import request from '@/common/request'

export function aliAppPay(data) {
	return request.post({
		url: '/opc/alipay/app',
		data
	})
}

export function aliPayCheck(data) {
	return request.post({
		url: '/opc/alipay/check',
		data
	})
}