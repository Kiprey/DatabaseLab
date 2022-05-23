import { post, postWithLoadTip } from '@/utils/request'

export default {
  login: query => postWithLoadTip(`/api/account/login`, query),
  logout: () => post(`/api/account/logout`),
  register: query => post(`/api/account/register`, query),
  changePass: query => post(`/api/account/changepass`, query)
}
