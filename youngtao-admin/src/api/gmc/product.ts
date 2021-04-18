import request from '@/common/utils/request';

// 获取商品
export function getMerchantProduct(data: any) {
  return request({
    url: `gmc/product/getMerchantProduct`,
    method: 'post',
    data: JSON.stringify(data)
  });
}

// 添加商品
export function addProduct(data: any) {
  return request({
    url: `gmc/product/add`,
    method: 'post',
    data: JSON.stringify(data)
  });
}
