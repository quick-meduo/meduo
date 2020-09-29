import { axios } from '@/utils/request'

const baseUserUri = '/v1/meduo/admin/PermissionGroup'

const api = {
  create: baseUserUri + '/create'
}

export default api

export function create (body) {
  return axios({
    url: api.create,
    method: 'post',
    data: body
  })
}
