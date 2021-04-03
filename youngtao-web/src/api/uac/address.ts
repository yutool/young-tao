import request from '@/common/utils/request'

export function getUserAddress() {
  return request({
    url: `uac/shippingAddress/getUserAddress`,
    method: 'get',
  })
}

export function addAddress(data: any) {
  return request({
    url: `uac/shippingAddress/add`,
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function deleteAddress(data: any) {
  return request({
    url: `uac/shippingAddress/delete`,
    method: 'post',
    data: JSON.stringify(data)
  })
}
