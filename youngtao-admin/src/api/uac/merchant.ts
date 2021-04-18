import request from '@/common/utils/request'

export function register(data: any) {
  return request({
    url: 'uac/merchant/register',
    method: 'post',
    data: JSON.stringify(data)
  })
}
