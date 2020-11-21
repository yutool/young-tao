import request from '@/common/request'

export function getGoods(data) {
	return request.post({
		url: '/gmc/goods',
		data
	})
}