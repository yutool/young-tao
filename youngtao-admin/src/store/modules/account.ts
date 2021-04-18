import { login, getCurrentUser } from '@/api/uac/auth'
import { setToken, removeToken } from '@/common/utils/auth'
import router from '@/router'

const state = {
  merchant: {}
};

const mutations = {
  SET_MERCHANT: (state: any, merchant: any) => {
    state.merchant = merchant
  }
};

const actions = {
  login(cxy: any, merchant: any) {
    return new Promise((resolve: any, reject: any) => {
      login(merchant).then((res: any) => {
        const { data } = res
        setToken(data.accessToken)
        resolve(res)
      }).catch((error: any) => {
        reject(error)
      })
    })
  },
  currentUser(cxy: any, merchant: any) {
    return new Promise((resolve: any, reject: any) => {
      getCurrentUser().then((res: any) => {
        const data = {res}
        if (data) {
          cxy.commit('SET_MERCHANT', res.data)
        }
        resolve(res)
      }).catch((error: any) => {
        reject(error)
      })
    })
    
  },
  logout(cxy: any) {
    cxy.commit('SET_MERCHANT', {})
    removeToken()
    router.push('/')
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
