import request from '@/common/utils/request'

export function getUserAddress(id: string) {
  return request({
    url: `api/address/user/${id}`,
    method: 'get',
  })
}

export function addAddress(data: any) {
  return request({
    url: `api/address`,
    method: 'post',
    data: JSON.stringify(data)
  })
}
