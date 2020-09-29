<template>
  <a-list
    itemLayout="horizontal"
    :dataSource="data"
  >
    <a-list-item slot="renderItem" slot-scope="item, index" :key="index">
      <a-list-item-meta>
        <a slot="title">{{ item.title }}</a>
        <span slot="description">
          <span class="security-list-description">{{ item.description }}</span>
          <span v-if="item.value"> : </span>
          <span class="security-list-value">{{ item.value }}</span>
        </span>
      </a-list-item-meta>
      <template v-if="item.actions">
        <a slot="actions" @click="item.actions.callback">{{ item.actions.title }}</a>
      </template>

    </a-list-item>

    <a-modal
      title="设置个人密码"
      style="top: 150px;"
      :width="800"
      v-model="visible"
      @ok="handleOk"
    >
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="旧密码"
          hasFeedback
        >
          <a-input-password
            v-decorator="[
              'old_password',
              {rules: [{ required: true, message: '请输入旧密码'}]}
            ]"
            id="old_password"
            placeholder="请输入旧密码" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="新密码"
          hasFeedback
        >
          <a-input-password
            v-decorator="[
              'new_password',
              {
                rules: [{ required: true, message: '请输入新密码', asyncValidator: validatePassword }],
                validateTrigger: ['change', 'blur']
              }
            ]"
            id="new_password"
            placeholder="请输入新密码" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="确认新密码"
          hasFeedback
        >
          <a-input-password
            v-decorator="[
              'new_password_confirmed',
              {
                rules: [{ required: true, message: '请再次输入新密码', asyncValidator: validatePasswordConfirm }],
                validateTrigger: ['change', 'blur']
              }
            ]"
            id="new_password_confirmed"
            placeholder="请再次输入新密码" />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-list>
</template>

<script>
import { updatePassword } from '@/api/user'

export default {
  data () {
    return {
      validatePassword: (rule, value, callback) => {
        if (value == null || value.trim() === '') {
          rule.message = '请输入密码'
          callback(new Error(rule.message))
          return
        }
        if (value.trim().length < 8) {
          rule.message = '密码长度必须大于等于8位'
          callback(new Error(rule.message))
          return
        }

        if (!/\d/.test(value)) {
          rule.message = '密码必须含至少一个数字'
          callback(new Error(rule.message))
          return
        }

        if (!/[a-z]/.test(value)) {
          rule.message = '密码必须含至少一个小写字母'
          callback(new Error(rule.message))
          return
        }

        if (!/[A-Z]/.test(value)) {
          rule.message = '密码必须含至少一个大写字母'
          callback(new Error(rule.message))
          return
        }

        if (!/[^0-9a-zA-Z]/.test(value)) {
          rule.message = '密码必须含至少一个特殊符号,如！@#$'
          callback(new Error(rule.message))
          return
        }
        this.new_password = value
        callback()
      },
      validatePasswordConfirm: (rule, value, callback) => {
        if (this.new_password && this.new_password !== value) {
          rule.message = '与密码输入不一致'
          callback(new Error(rule.message))
        } else {
          callback()
        }
      },
      visible: false,
      new_password: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: this.$form.createForm(this),
      data: [
        { title: '账户密码', description: '当前密码强度', value: '强', actions: { title: '修改', callback: () => { this.visible = true } } },
        { title: '密保手机', description: '已绑定手机', value: '138****8293', actions: { title: '修改', callback: () => { this.$message.success('This is a message of success') } } },
        { title: '密保问题', description: '未设置密保问题，密保问题可有效保护账户安全', value: '', actions: { title: '设置', callback: () => { this.$message.error('This is a message of error') } } },
        { title: '备用邮箱', description: '已绑定邮箱', value: 'ant***sign.com', actions: { title: '修改', callback: () => { this.$message.warning('This is message of warning') } } },
        { title: 'MFA 设备', description: '未绑定 MFA 设备，绑定后，可以进行二次确认', value: '', actions: { title: '绑定', callback: () => { this.$message.info('This is a normal message') } } }
      ]
    }
  },
  methods: {
    handleOk (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          this.visible = false
          updatePassword({
            password: values.password,
            newPassword: values.new_password
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
