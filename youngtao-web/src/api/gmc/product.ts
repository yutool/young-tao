import request from '@/common/utils/request'

export function searchProduct(data: any) {
  return request({
    url: `gmc/product/search`,
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
