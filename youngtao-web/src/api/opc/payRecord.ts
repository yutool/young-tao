import request from '@/common/utils/request'

export function getPayOrder(paymentId: any) {
  return request({
    url: `opc/orderPayRecord/${paymentId}`,
    method: 'get'
  })
}
