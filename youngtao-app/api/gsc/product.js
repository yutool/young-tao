import request from '@/common/request'

export function getSeckillPage(page, size) {
	return request.get({
		url: `/gsc/product/getSeckillPage`
	})
}

export function getTimeMenu() {
	return request.get({
		url: `/gsc/product/timeMenu`
	})
}

export function listByTime(idx, page, size) {
	return request.get({
		url: `/gsc/product/listByTime/${idx}/${page}/${size}`
	})
}