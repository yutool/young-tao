import request from '@/common/utils/request'

export function gscCreateOrder(data: any) {
  return request({
    url: 'gpc/order/create',
    method: 'post',
    data: JSON.stringify(data)
  })
}
