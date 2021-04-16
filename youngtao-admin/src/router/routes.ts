const routes = [
  { path: '/', component: () => import('@/views/index.vue') },
  {
    path: '/product',
    component: () => import('@/views/product/index.vue'),
    children: [
      { path: '/', redirect: 'list'},
      { path: 'list', component: () => import('@/views/product/list.vue') },
      { path: 'add', component: () => import('@/views/product/add.vue') },
    ]
  },
  {
    path: '/seckill',
    component: () => import('@/views/seckill/index.vue'),
    children: [
      { path: '/', redirect: 'list'},
      { path: 'list', component: () => import('@/views/seckill/list.vue') },
      { path: 'add/:id', component: () => import('@/views/seckill/add.vue') },
    ]
  },
  {
    path: '/order',
    component: () => import('@/views/order/index.vue'),
    children: [
      { path: '/', redirect: 'list'},
      { path: 'list', component: () => import('@/views/order/list.vue') },
      { path: 'pending', component: () => import('@/views/order/pending.vue') },
    ]
  },
];

export default routes
