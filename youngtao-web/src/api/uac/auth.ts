import request from '@/common/utils/request'

export function login(data: any) {
  return request({
    url: 'uac/oauth/login',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getCurrentUser() {
  return request({
    url: 'uac/oauth/currentUser',
    method: 'get'
  })
}
