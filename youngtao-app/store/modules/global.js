const state = {
	order: {}
}

const mutations = {
  SET_ORDER (state, order) {
    state.order = order
  },
	INIT (state, obj) {
		state.order = obj.order
	}
}

const actions = {
  setOrder ({commit}, order) {
    commit('SET_ORDER', order)
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