import request from '@/common/utils/request'

export function login(data: any) {
  return request({
    url: 'api/oauth/login',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getCurrentUser() {
  return request({
    url: 'api/user/current',
    method: 'get'
  })
}

export function register(data: any) {
  return request({
    url: 'api/user/register',
    method: 'post',
    data: JSON.stringify(data)
  })
}
