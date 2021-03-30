import request from '@/common/utils/request'

export function queryOrderStatus(id: string) {
  return request({
    url: `api/order/queryOrderStatus/${id}`,
    method: 'get'
  })
}

export function getOrder(id: string) {
  return request({
    url: `api/order/${id}`,
    method: 'get'
  })
}

export function getUserOrder(id: string, page: number, size: number) {
  return request({
    url: `api/order/user/${id}/${page}/${size}`,
    method: 'get'
  })
}

export function prepareOrder(data: any) {
  return request({
    url: 'api/order/prepare',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getPrepareOrder(id: string) {
  return request({
    url: `api/order/prepare/${id}`,
    method: 'get',
  })
}

export function checkPrepareOrder(id: string) {
  return request({
    url: `api/order/prepare/${id}`,
    method: 'delete',
  })
}

export function createOrder(data: any) {
  return request({
    url: 'api/order/create',
    method: 'post',
    data: JSON.stringify(data)
  })
}


export function deleteOrder(id: string) {
  return request({
    url: `api/order/${id}`,
    method: 'delete',
  })
}
