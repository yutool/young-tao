import request from '@/common/utils/request';

// 获取订单
export function getSubmenu(pid: string) {
  return request({
    url: `gmc/category/getSubmenu/${pid}`,
    method: 'get'
  });
}
