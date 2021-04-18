import request from '@/common/utils/request';

// 获取订单
export function getMerchantProduct(data: any) {
  return request({
    url: `gsc/product/getMerchantProduct`,
    method: 'post',
    data: JSON.stringify(data)
  });
}
