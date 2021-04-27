import request from '@/common/utils/request'

export function getCategory() {
  return request({
    url: 'gmc/category/get',
    method: 'get'
  })
}

export function getMenu() {
  return request({
    url: 'gmc/category/menu',
    method: 'get'
  })
}

export function getRecommendMenu() {
  return request({
    url: 'gmc/category/recommendMenu',
    method: 'get'
  })
}
