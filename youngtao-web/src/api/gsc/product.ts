import request from '@/common/utils/request'

export function getTimeMenu() {
  return request({
    url: 'gsc/product/timeMenu',
    method: 'get'
  })
}

export function getProductList(idx: any, page: any, size: any) {
  return request({
    url: `gsc/product/listByTime/${idx}/${page}/${size}`,
    method: 'get'
  })
}

export function gscConfirmOrder(data: any) {
  return request({
    url: `gsc/product/confirmOrder`,
    method: 'post',
    data: JSON.stringify(data)
  })
}
