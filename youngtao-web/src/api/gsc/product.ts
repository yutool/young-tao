import request from '@/common/utils/request'

export function getTimeMenu() {
  return request({
    url: 'gsc/product/timeMenu',
    method: 'get'
  })
}

export function getProductList(idx: any, page: any, size: any) {
  return request({
    url: `gsc/product/listByTime/${idx}/${page}/${size}`,
    method: 'get'
  })
}

export function getGoods(menu: string, spuId: string) {
  return request({
    url: `gsc/seckill/goods/${menu}/${spuId}`,
    method: 'get'
  })
}

export function prepare(data: any) {
  return request({
    url: 'gsc/seckill/order/prepare',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function queryQueue(userId: string, goodsId: string) {
  return request({
    url: `gsc/seckill/order/queue/${userId}/${goodsId}`,
    method: 'get'
  })
}
