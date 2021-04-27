import request from '@/common/utils/request'

export function createOrder(data: any) {
  return request({
    url: 'omc/order/create',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function queryStatus(paymentId: any) {
  return request({
    url: '/omc/order/queryStatus',
    method: 'post',
    data: {id: paymentId}
  })
}

export function getUserOrder(data: any) {
  return request({
    url: `omc/order/getUserOrder`,
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function orderRefund(data: any) {
  return request({
    url: `omc/order/orderRefund`,
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function deleteOrder(data: any) {
  return request({
    url: 'omc/order/delete',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function recoverOrder(data: any) {
  return request({
    url: 'omc/order/recover',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function acceptOrder(data: any) {
  return request({
    url: 'omc/order/accept',
    method: 'post',
    data: JSON.stringify(data)
  })
}

