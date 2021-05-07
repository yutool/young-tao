import request from '@/common/utils/request'

export function register(data: any) {
  return request({
    url: 'uac/merchant/register',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function updateInfo(data: any) {
  return request({
    url: 'uac/merchant/update',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function updatePassword(data: any) {
  return request({
    url: 'uac/merchant/updatePassword',
    method: 'post',
    data: JSON.stringify(data)
  })
}
