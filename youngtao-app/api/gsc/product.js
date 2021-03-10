import request from '@/common/request'

export function getSeckillPage(page, size) {
	return request.get({
		url: `/gsc/product/getSeckillPage`
	})
}