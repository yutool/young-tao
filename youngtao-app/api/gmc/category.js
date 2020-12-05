import request from '@/common/request'

export function getCategory() {
	return request.get({
		url: '/gmc/category/get'
	})
}