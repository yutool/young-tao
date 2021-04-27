const routes = [
  { path: '/', component: () => import('@/views/home/index.vue'), meta: { title: '聚美商城'} },
  {
    path: '/product',
    component: () => import('@/views/product/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'detail/:id', component: () => import('@/views/product/detail.vue') },
    ]
  },
  {
    path: '/market',
    component: () => import('@/views/market/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list', component: () => import('@/views/market/list.vue') },
    ]
  },
  {
    path: '/seckill',
    component: () => import('@/views/seckill/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list', component: () => import('@/views/seckill/list.vue') },
    ]
  },
  {
    path: '/order',
    component: () => import('@/views/order/index.vue'),
    meta: { requireAuth: true },
    children: [
      { path: '/', redirect: {name: 'cart'} },
      { path: 'cart', name: 'cart', component: () => import('@/views/order/cart.vue') },
      { path: 'confirm/:id', name: 'confirm' , component: () => import('@/views/order/confirm.vue') },
      { path: 'pay/:id', name: 'pay', component: () => import('@/views/order/pay.vue') },
      { path: 'pay_success', name: 'pay_success', component: () => import('@/views/order/pay_success.vue') },
      { path: 'pay_fail', name: 'pay_fail', component: () => import('@/views/order/pay_fail.vue') },
    ]
  },
  {
    path: '/center',
    component: () => import('@/views/center/index.vue'),
    meta: { requireAuth: true },
    children: [
      { path: '/', redirect: 'order' },
      { path: 'order', component: () => import('@/views/center/order.vue' ) },
      { path: 'orderRecycle', component: () => import('@/views/center/orderRecycle.vue' ) },
    ]
  },
  {
    path: '/user',
    component: () => import('@/views/user/index.vue'),
    meta: { requireAuth: true },
    children: [
      { path: '/', redirect: 'info' },
      { path: 'info', component: () => import('@/views/user/info.vue') },
      { path: 'address', component: () => import('@/views/user/address.vue') },
      { path: 'security', component: () => import('@/views/user/security.vue') },
    ]
  },
];

export default routes
