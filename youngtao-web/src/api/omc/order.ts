import request from '@/common/utils/request'

export function queryOrderStatus(id: string) {
  return request({
    url: `omc/order/queryOrderStatus/${id}`,
    method: 'get'
  })
}

export function getOrder(id: string) {
  return request({
    url: `omc/order/${id}`,
    method: 'get'
  })
}

export function getUserOrder(data: any) {
  return request({
    url: `omc/order/getUserOrder`,
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function prepareOrder(data: any) {
  return request({
    url: 'omc/order/prepare',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getPrepareOrder(id: string) {
  return request({
    url: `omc/order/prepare/${id}`,
    method: 'get',
  })
}

export function checkPrepareOrder(id: string) {
  return request({
    url: `omc/order/prepare/${id}`,
    method: 'delete',
  })
}

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

export function deleteOrder(id: string) {
  return request({
    url: `omc/order/${id}`,
    method: 'delete',
  })
}
