import request from '@/common/utils/request'

export function getDateMenu() {
  return request({
    url: 'api/seckill/goods/menus',
    method: 'get'
  })
}

export function getGoodsList(menu: string) {
  return request({
    url: `api/seckill/goods/time_list/${menu}`,
    method: 'get'
  })
}

export function getGoods(menu: string, spuId: string) {
  return request({
    url: `api/seckill/goods/${menu}/${spuId}`,
    method: 'get'
  })
}

export function prepare(data: any) {
  return request({
    url: 'api/seckill/order/prepare',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function queryQueue(userId: string, goodsId: string) {
  return request({
    url: `api/seckill/order/queue/${userId}/${goodsId}`,
    method: 'get'
  })
}
