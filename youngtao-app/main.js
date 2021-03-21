import Vue from 'vue'
import App from './App'

import store from './store'
import uView from "uview-ui"
Vue.use(uView)

import orderType from '@/common/constant/orderType.js'
Vue.prototype.$orderType = orderType
import orderStatus from '@/common/constant/orderStatus.js'
Vue.prototype.$orderStatus = orderStatus

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
	store,
	...App
})
app.$mount()
