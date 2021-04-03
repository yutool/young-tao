import request from '@/common/utils/request'

export function updateUserInfo(data: any) {
  return request({
    url: 'uac/user/update',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function updatePassword(data: any) {
  return request({
    url: 'uac/user/updatePassword',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function register(data: any) {
  return request({
    url: 'uac/user/register',
    method: 'post',
    data: JSON.stringify(data)
  })
}

