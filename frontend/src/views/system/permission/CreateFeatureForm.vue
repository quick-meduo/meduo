<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="功能名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input placeholder="请输入功能名" v-decorator="['name', {rules: [{required: true, min: 4, max: 10,message: '请输入功能名，长度5-10之间！'}]}]" />
        </a-form-item>
        <a-form-item
          label="功能代码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input placeholder="请输入功能代码" v-decorator="['code', {rules: [{required: true, min: 4, max: 256,message: '请输入功能名，多个代码用逗号分割！'}]}]" />
        </a-form-item>
        <a-form-item
          label="描述"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-textarea
            rows="4"
            placeholder="请输入功能描述"
            v-decorator="['description', {rules: [{required: true, min: 5, max: 128, message: '请输入功能描述,长度5-128之间！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { addFeature, updateFeature } from '@/api/feature-service'

export default {
  name: 'createFeatureForm',
  props: {
    groupId: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      title: '新增功能',
      labelCol: {
        xs: { span: 4 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 4 },
        sm: { span: 13 }
      },
      record: null,
      visible: false,
      confirmLoading: false,
      newAdded: false,

      form: this.$form.createForm(this)
    }
  },
  methods: {
    add (record) {
      this.record = record
      this.title = '新增功能'
      this.visible = true
      this.newAdded = true
    },
    edit (record) {
      this.record = record
      this.title = '编辑功能'
      this.visible = true
      this.newAdded = false
      this.$nextTick(() => {
        this.form.setFieldsValue({ ...record })
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const model = {
            gid: this.groupId,
            name: values.name,
            code: values.code,
            description: values.description
          }
          if (this.newAdded === false) {
            model.id = this.record.id
            model.gid = this.record.gid
            updateFeature(model).then(() => {
              this.visible = false
              this.confirmLoading = false
              this.$emit('ok', values)
            })
          } else {
            addFeature(model).then(() => {
              this.visible = false
              this.confirmLoading = false
              this.$emit('ok', values)
            })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>