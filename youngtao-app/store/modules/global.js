const state = {
	confirmOrder: []
}

const mutations = {
  SET_CONFIRM_ORDER (state, confirmOrder) {
    state.confirmOrder = confirmOrder
  },
	INIT (state, obj) {
		state.confirmOrder = obj.confirmOrder
	}
}

const actions = {
  setConfirmOrder ({commit}, confirmOrder) {
    commit('SET_CONFIRM_ORDER', confirmOrder)
  },
	init ({commit}, obj) {
		commit('INIT', obj)
	}
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}