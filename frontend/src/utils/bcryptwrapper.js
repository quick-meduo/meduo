import bcrypt from 'bcryptjs'

export default {
  install (Vue) {
    Object.defineProperty(Vue.prototype, '$bcrypt', {
      value: bcrypt
    })
  }
}