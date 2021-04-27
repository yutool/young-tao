import request from '@/common/utils/request';

// 添加活动商品
export function addOrUpdateSku(data: any) {
  return request({
    url: `gsc/sku/addOrUpdate`,
    method: 'post',
    data: JSON.stringify(data)
  });
}

// 添加活动商品
export function getSkuById(skuId: any) {
  return request({
    url: `gsc/sku/get/${skuId}`,
    method: 'get'
  });
}

// 添加删除商品
export function deleteById(data: any) {
  return request({
    url: `gsc/sku/deleteById`,
    method: 'post',
    data: JSON.stringify(data)
  });
}
