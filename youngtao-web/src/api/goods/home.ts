import request from '@/common/utils/request'

export function getMenu() {
  return request({
    url: 'api/home/menu',
    method: 'get'
  })
}

export function getContentMenu() {
  return request({
    url: 'api/home/content/menu',
    method: 'get'
  })
}

export function getContent(data: any) {
  return request({
    url: 'api/home/content',
    method: 'post',
    params: { menus: data }
  })
}
