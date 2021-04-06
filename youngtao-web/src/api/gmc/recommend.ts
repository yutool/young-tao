import request from '@/common/utils/request'

export function getRecommendProduct() {
  return request({
    url: 'gmc/recommend/product',
    method: 'get'
  })
}
