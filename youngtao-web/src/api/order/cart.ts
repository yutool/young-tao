import request from '@/common/utils/request'

export function addCart(data: any) {
  return request({
    url: 'api/cart',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getCart(id: string) {
  return request({
    url: `api/cart/user/${id}`,
    method: 'get'
  })
}

export function updateNum(data: any) {
  return request({
    url: `api/cart`,
    method: 'put',
    data: JSON.stringify(data)
  })
}

export function deleteCart(id: number) {
  return request({
    url: `api/cart/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids: any) {
  return request({
    url: `api/cart/batch`,
    method: 'delete',
    data: { ids }
  })
}
