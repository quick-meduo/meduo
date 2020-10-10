import { axios } from '@/utils/request'

const baseUserUri = '/v1/meduo/admin/PermissionGroup'

const api = {
  create: baseUserUri + '/create',
  listPage: baseUserUri + '/listPage',
  findPermissionGroupByServiceId: baseUserUri + '/findPermissionGroupByServiceId'
}

export default api

export function create (body) {
  return axios({
    url: api.create,
    method: 'post',
    data: body
  })
}

export function listPage (body) {
  return axios({
    url: api.listPage,
    method: 'put',
    data: body
  })
}

export function findPermissionGroupByServiceId (body) {
  return axios({
    url: api.findPermissionGroupByServiceId,
    method: 'put',
    data: body
  })
}