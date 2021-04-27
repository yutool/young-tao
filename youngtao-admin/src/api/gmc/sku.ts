import request from '@/common/utils/request';

// 更新sku
export function updateSkuInfo(data: any) {
  return request({
    url: `gmc/sku/update`,
    method: 'post',
    data: JSON.stringify(data)
  });
}

// 删除sku
export function deleteSku(data: any) {
  return request({
    url: `gmc/sku/delete`,
    method: 'post',
    data: JSON.stringify(data)
  });
}
