import Vue from 'vue'
import moment from 'moment'
import 'moment/locale/zh-cn'
moment.locale('zh-cn')

Vue.filter('NumberFormat', function (value) {
  if (!value) {
    return '0'
  }
  const intPartFormat = value.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') // 将整数部分逢三一断
  return intPartFormat
})

Vue.filter('dayjs', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})

Vue.filter('moment', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})

Vue.filter('boolizer', function (data) {
  return data ? '是' : '否'
})

Vue.filter('credential', function (data) {
  let result = '一般'
  switch (data) {
    case 'LEVEL1':
      result = '一般'
      break
    case 'LEVEL2':
      result = '公开'
      break
    case 'LEVEL3':
      result = '秘密'
      break
    default:
      result = '一般'
  }
  return result
})

Vue.filter('ustatetrans', function (data) {
  let result = '一般'
  switch (data) {
    case 'NORMAL':
      result = '正常'
      break
    case 'ABNORMAL':
      result = '禁用'
      break
    case 'LOCKED':
      result = '锁定'
      break
    default:
      result = '禁用'
  }
  return result
})

Vue.filter('utypetrans', function (data) {
  let result = '其他'
  switch (data) {
    case 'ADMIN':
      result = '管理员'
      break
    case 'SYS':
      result = '系统人员'
      break
    case 'BIZ':
      result = '业务人员'
      break
    case 'OTHER':
      result = '其他'
  }
  return result
})

Vue.filter('mask_phone', function (data) {
  return data.replace(/(\d{3})(\d{4})/, '$1****')
})

Vue.filter('mask_email', function (data) {
  return data.replace(/^(.)(.*)(.@.*)$/,
    (_, a, b, c) => a + b.replace(/./g, '*') + c
  )
})

