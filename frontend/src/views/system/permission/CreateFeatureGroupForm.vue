<template>
  <a-modal
    title="新建功能组"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="功能组名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input placeholder="请输入功能组名" v-decorator="['name', {rules: [{required: true, min: 4, max: 10,message: '请输入功能组名，长度5-10之间！'}]}]" />
        </a-form-item>
        <a-form-item
          label="描述"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-textarea
            rows="4"
            placeholder="请输入功能组描述"
            v-decorator="['description', {rules: [{required: true, min: 5, max: 128, message: '请输入功能组描述,长度5-128之间！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { addFeatureGroup } from '@/api/feature-service'

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
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const model = {
            serviceId: this.serviceId,
            name: values.name,
            description: values.description
          }

          addFeatureGroup(model).then(() => {
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
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>
