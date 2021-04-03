import request from '@/common/utils/request'

export function sendRegisterCode(data: any) {
  return request({
    url: 'uac/email/register',
    method: 'post',
    data: JSON.stringify(data)
  })
}
