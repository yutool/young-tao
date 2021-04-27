import request from '@/common/utils/request';

// 获取spu
export function updateSpuInfo(data: any) {
  return request({
    url: `gmc/spu/update`,
    method: 'post',
    data: JSON.stringify(data)
  });
}

// 删除spu
export function deleteSpu(data: any) {
  return request({
    url: `gmc/spu/delete`,
    method: 'post',
    data: JSON.stringify(data)
  });
}

// 删除spu
export function recycleSpu(data: any) {
  return request({
    url: `gmc/spu/recycle`,
    method: 'post',
    data: JSON.stringify(data)
  });
}
