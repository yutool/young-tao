import request from '@/common/request'

export function getGoods(data) {
	return request.post('/gmc/goods', data)
}