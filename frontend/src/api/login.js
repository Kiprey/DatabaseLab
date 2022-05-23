import { post, postWithLoadTip } from '@/utils/request'

export default {
  login: query => postWithLoadTip(`/api/account/login`, query),
  logout: () => post(`/api/account/logout`),
  register: query => post(`/api/account/register`, query),
  changePass: query => post(`/api/account/changepass`, query),

  getInfo: () => postWithLoadTip('/api/account/getinfo'),
  saveInfo: (data) => post('/api/account/saveinfo', data),

  queryuserbyadmin: (data, params) => post('/api/account/queryuserbyadmin', data, params),
  deluserbyadmin: id => post('/api/account/deluserbyadmin', null, { 'username': id }),
  adduserbyadmin: data => post('/api/account/adduserbyadmin', data),
  modifypassbyadmin: data => post('/api/account/modifypassbyadmin', data)
}
