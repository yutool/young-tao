import request from '@/common/utils/request'

export function listRecommendProduct(data: any) {
  return request({
    url: 'gmc/product/listRecommendProduct',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getProduct(id: any) {
  return request({
    url: `gmc/product/${id}`,
    method: 'get',
  })
}

export function confirmOrder(data: any) {
  return request({
    url: `gmc/product/confirmOrder`,
    method: 'post',
    data: JSON.stringify(data)
  })
}
