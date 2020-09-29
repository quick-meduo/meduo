import { axios } from '@/utils/request'

const baseUserUri = '/v1/meduo/admin/service'

const api = {
  create: baseUserUri + '/create',
  list: baseUserUri + '/list',
  listPage: baseUserUri + '/listPage',
  update: baseUserUri + '/update',
  delete: baseUserUri + '/delete',
  validateSid: baseUserUri + '/validateSid',
  findByFeatureKey: baseUserUri + '/findServiceByFeatureKey',
  addFeatureGroup: baseUserUri + '/addFeatureGroup',
  findFeatureGroupsByServiceId: baseUserUri + '/findFeatureGroupsByServiceId',
  findFeatureGroups: baseUserUri + '/findFeatureGroups',
  addFeature: baseUserUri + '/addFeature',
  findFeaturesByGroupId: baseUserUri + '/findFeaturesByGroupId',
  updateFeature: baseUserUri + '/updateFeature'
}

export default api

export function create (body) {
  return axios({
    url: api.create,
    method: 'post',
    data: body
  })
}

export function list () {
  return axios({
    url: api.list,
    method: 'get'
  })
}

export function listPage (body) {
  return axios({
    url: api.listPage,
    method: 'put',
    data: body
  })
}

export function update (body) {
  return axios({
    url: api.update,
    method: 'put',
    data: body
  })
}

export function del (id) {
  return axios({
    url: api.delete + '?id=' + id,
    method: 'delete'
  })
}

export function validateSid (sid) {
  return axios({
    url: api.validateSid + '?sid=' + sid,
    method: 'get'
  })
}

export function findByFeatureKey (key) {
  return axios({
    url: api.findByFeatureKey,
    method: 'put',
    data: key
  })
}

export function addFeatureGroup (body) {
  return axios({
    url: api.addFeatureGroup,
    method: 'post',
    data: body
  })
}

export function findFeatureGroupsByServiceId (body) {
  return axios({
    url: api.findFeatureGroupsByServiceId,
    method: 'put',
    data: body
  })
}

export function findFeatureGroups (body) {
  return axios({
    url: api.findFeatureGroups,
    method: 'put',
    data: body
  })
}

export function addFeature (body) {
  return axios({
    url: api.addFeature,
    method: 'post',
    data: body
  })
}

export function updateFeature (body) {
  return axios({
    url: api.updateFeature,
    method: 'put',
    data: body
  })
}

export function findFeaturesByGroupId (body) {
  return axios({
    url: api.findFeaturesByGroupId,
    method: 'put',
    data: body
  })
}


