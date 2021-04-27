import request from '@/common/utils/request';

// 获取订单
export function getMerchantOrder(data: any) {
  return request({
    url: `omc/order/getMerchantOrder`,
    method: 'post',
    data: JSON.stringify(data)
  });
}

// 订单发货
export function orderDelivery(data: any) {
  return request({
    url: `omc/order/delivery`,
    method: 'post',
    data: JSON.stringify(data)
  });
}
