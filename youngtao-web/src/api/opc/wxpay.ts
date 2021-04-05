import request from '@/common/utils/request'

export function weixinPay(data: any) {
  return request({
    url: 'api/pay/wx/native',
    method: 'post',
    data: JSON.stringify(data)
  })
}
