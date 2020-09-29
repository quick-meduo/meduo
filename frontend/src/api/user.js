import { axios } from '@/utils/request'

const baseUserUri = '/v1/meduo/admin/user'

const api = {
  selectUsers: baseUserUri + '/findPage',
  validateUid: baseUserUri + '/validateUid',
  save: baseUserUri + '/save',
  lock: baseUserUri + '/lock',
  unlock: baseUserUri + '/unlock',
  remove: baseUserUri + '/delete',
  reset_password: baseUserUri + '/resetPassword',
  update_password: baseUserUri + '/updatePassword',
  selectUserByName: baseUserUri + '/findByName'
}

export default api

export function selectUsers (body) {
  return axios({
    url: api.selectUsers,
    method: 'post',
    data: body
  })
}

export function selectUserByName (body) {
  return axios({
    url: api.selectUserByName,
    method: 'put',
    data: body
  })
}

export function validateUid (uid) {
  return axios({
    url: api.validateUid + '?userId=' + uid,
    method: 'get'
  })
}

export function save (body) {
  return axios({
    url: api.save,
    method: 'post',
    data: body
  })
}

export function freeze (id) {
  return axios({
    url: api.lock + '?id=' + id,
    method: 'put'
  })
}

export function unfreeze (id) {
  return axios({
    url: api.unlock + '?id=' + id,
    method: 'put'
  })
}

export function remove (body) {
  return axios({
    url: api.remove,
    method: 'delete',
    data: body
  })
}

export function resetPassword (body) {
  return axios({
    url: api.reset_password,
    method: 'put',
    data: body
  })
}

export function updatePassword (body) {
  return axios({
    url: api.update_password,
    method: 'put',
    data: body
  })
}

