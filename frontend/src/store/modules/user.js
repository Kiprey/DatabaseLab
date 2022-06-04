
/*
import Cookies from 'js-cookie'
import userApi from '@/api/user'
// initial state
const state = {
  userName: Cookies.get('adminUserName'),
  userInfo: Cookies.get('adminUserInfo')
}

// actions
const actions = {
  initUserInfo ({ commit }) {
    userApi.getCurrentUser().then(re => {
      commit('setUserInfo', re.response)
    })
  }
}

// mutations
const mutations = {
  setUserName (state, userName) {
    state.userName = userName
    Cookies.set('adminUserName', userName, { expires: 30 })
  },
  setUserInfo: (state, userInfo) => {
    state.userInfo = userInfo
    Cookies.set('adminUserInfo', userInfo, { expires: 30 })
  },
  clearLogin (state) {
    Cookies.remove('adminUserName')
    Cookies.remove('adminUserInfo')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
*/

// initial state
const state = {}

const getters = {
  getUserToken: (_) => {
    return window.sessionStorage.getItem('token')
  },
  getUserAuthorities: (_) => {
    let auth = window.sessionStorage.getItem('authorities')
    return auth === null ? null : auth.split(',')
  }
}

// actions
const actions = {}

// mutations
const mutations = {
  setUserInfo: (_, obj) => {
    let token = obj.token
    let authorities = obj.role
    let auth = []
    for (let i = 0; i < authorities.length; i++) {
      auth.push(authorities[i].authority)
    }

    window.sessionStorage.setItem('token', token)
    window.sessionStorage.setItem('authorities', auth)
  },
  clearLogin: (_) => {
    window.sessionStorage.setItem('token', null)
    window.sessionStorage.setItem('authorities', null)
  }
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
}
