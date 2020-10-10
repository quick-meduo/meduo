<template>
  <a-modal
    title="新建授权组"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="授权组名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input placeholder="请输入授权组名" v-decorator="['name', {rules: [{required: true, min: 4, max: 25,message: '请输入授权组名，长度5-25之间！'}]}]" />
        </a-form-item>
        <a-form-item
          label="授权组类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select
            @change="requirekPermGroup"
            v-decorator="[
              'type',
              {
                initialValue: 'PERMANENT',
                rules: [
                { required: true, message: '请输入授权组类型'}],
                validateTrigger: ['change', 'blur']}
            ]">
            <a-select-option value="PERMANENT">长期授权</a-select-option>
            <a-select-option value="EXPIRATION">临时授权</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="授权时长（天）"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          v-if="expectExpiration"
        >
          <a-input-number
            :min="1"
            :max="100000"
            :default-value="3"
            v-decorator="['expirationDate', {rules: [{required: false, message: '请输入授权时长'}]}]"
          />
        </a-form-item>
        <a-form-item
          label="描述"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-textarea
            rows="4"
            placeholder="请输入授权组描述"
            v-decorator="['description', {rules: [{required: true, min: 5, max: 128, message: '请输入授权组描述,长度5-128之间！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { create } from '@/api/permission-service'

export default {
  name: 'CreateFeatureGroupForm',
  props: {
    serviceId: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      expectExpiration: false,
      labelCol: {
        xs: { span: 4 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 4 },
        sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,

      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.from = this.$form.createForm(this)
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const model = {
            name: values.name,
            type: values.type,
            expirationDate: values.expirationDate,
            serviceId: this.serviceId,
            description: values.description
          }

          create(model).then(() => {
            this.visible = false
            this.confirmLoading = false
            this.$emit('ok', values)
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.from = null
      this.visible = false
    },
    requirekPermGroup (value) {
      if (value === 'EXPIRATION') {
        this.expectExpiration = true
      } else {
        this.expectExpiration = false
      }
    }
  }
}
</script>

<style scoped>

</style>
