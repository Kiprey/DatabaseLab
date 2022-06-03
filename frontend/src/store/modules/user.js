
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
const state = {
  token: '',
  authorities: []
}

// actions
const actions = {}

// mutations
const mutations = {
  setUserInfo: (state, obj) => {
    let token = obj.token
    let authorities = obj.role
    console.log(authorities)
    let auth = []
    for (let i = 0; i < authorities.length; i++) {
      auth.push(authorities[i].authority)
    }

    state.token = token
    state.authorities = auth
    window.sessionStorage.setItem('token', token)
    window.sessionStorage.setItem('authorities', auth)
  },
  clearLogin: (state) => {
    state.token = ''
    state.authorities = []
  },
  getUserToken: (state) => {
    return state.token
  },
  getUserAuthorities: (state) => {
    return state.authorities
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
