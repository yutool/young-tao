import request from '@/common/utils/request'

export function aliWebPay(data: any) {
  return request({
    url: 'opc/alipay/web',
    method: 'post',
    data: JSON.stringify(data)
  })
}
