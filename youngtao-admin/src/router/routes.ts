const routes = [
  { 
    path: '/', component: () => import('@/views/index.vue') ,
    meta: { requireAuth: true },
    children: [
      { path: '/', redirect: '/product'},
      {
        path: '/product',
        component: () => import('@/views/product/index.vue'),
        children: [
          { path: '/', redirect: 'list'},
          { path: 'list', component: () => import('@/views/product/list.vue') },
          { path: 'add', component: () => import('@/views/product/add.vue') },
          { path: 'recycle', component: () => import('@/views/product/recycle.vue') },
        ]
      },
      {
        path: '/seckill',
        component: () => import('@/views/seckill/index.vue'),
        children: [
          { path: '/', redirect: 'list'},
          { path: 'list', component: () => import('@/views/seckill/list.vue') },
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
    ]
  },
  { path: '/login', component: () => import('@/views/login/index.vue') },
  { path: '/register', component: () => import('@/views/login/register.vue') },
];

export default routes
