<template>
  <a-card
    style="margin-top: 24px"
    :bordered="false"
  >
    <div slot="extra">
      <a-radio-group>
        <a-radio-button value="all" @click="$refs.createPermGroup.add()">创建授权组</a-radio-button>
        <a-radio-button value="all" @click="serviceRefresh">刷新</a-radio-button>
      </a-radio-group>
      <a-input-search
        style="margin-left: 16px; width: 272px;"
        v-model="queryString"
        @pressEnter="handleQueryKeyPress"
        @search="handleQuery"
        placeholder="请输入查询关键字"/>
    </div>
    <div class="operate">
      <a-button type="dashed" style="width: 100%" icon="plus" @click="handleAddService">添加</a-button>
    </div>
    <a-list size="large" :pagination="pagination" :data-source="dataSource">
      <a-list-item :key="item.id" slot="renderItem" slot-scope="item">
        <a-list-item-meta :description="item.description">
          <a-avatar slot="avatar" size="large" shape="square" :src="item.avatar"/>
          <a slot="title">{{ item.name }}
            <a-tag class="serviceId">{{ item.sid }}</a-tag>
          </a>
        </a-list-item-meta>
        <div slot="actions">
          <a @click="handleFeatureGroupGrant(item)">权限管理</a>
        </div>
        <div slot="actions">
          <a @click="FeatureGroupManage(item)">功能管理</a>
        </div>
        <div slot="actions">
          <a @click="editService(item)">编辑</a>
        </div>
        <div slot="actions">
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item @click="deleteService(item)"><a>删除</a></a-menu-item>
            </a-menu>
            <a>更多
              <a-icon type="down"/>
            </a>
          </a-dropdown>
        </div>
        <div class="list-content">
          <div class="list-content-item">
            <span>创建者</span>
            <p>{{ item.createUser }}</p>
          </div>
          <div class="list-content-item">
            <span>开始时间</span>
            <p>{{ item.createDate | dayjs }}</p>
          </div>
        </div>
      </a-list-item>
    </a-list>
    <a-modal title="添加服务" style="top: 150px;" :width="800" v-model="serviceModel.visible">
      <template slot="footer">
        <a-button key="back" @click="cancelServiceAdd">
          放弃
        </a-button>
        <a-button key="submit" type="primary" :disabled="serviceModel.submit_disable" @click="ServiceAdd">
          确定
        </a-button>
      </template>
      <a-form :form="serviceModel.form">
        <a-form-item
          label="SID"
          hasFeedback
          :labelCol="{lg: {span: 4}, sm: {span: 4}}"
          :wrapperCol="{lg: {span: 17}, sm: {span: 17} }">
          <a-input
            v-decorator="[
              'sid',
              {
                rules: [{ required: true, message: '请输入唯一的服务ID', asyncValidator: validateSid}],
                validateTrigger: ['blur']
              }
            ]"
            name="name"
            placeholder="请输入唯一的服务ID"/>
        </a-form-item>
        <a-form-item
          label="服务名"
          hasFeedback
          :labelCol="{lg: {span: 4}, sm: {span: 4}}"
          :wrapperCol="{lg: {span: 17}, sm: {span: 17} }">
          <a-input
            v-decorator="[
              'name',
              {rules: [{ required: true, message: '请输入服务名' }]}
            ]"
            name="name"
            placeholder="给服务名起个名字"/>
        </a-form-item>
        <a-form-item
          label="服务描述"
          hasFeedback
          :labelCol="{lg: {span: 4}, sm: {span: 4}}"
          :wrapperCol="{lg: {span: 17}, sm: {span: 17} }">
          <a-textarea
            rows="4"
            placeholder="请输入服务描述信息"
            v-decorator="[
              'description',
              {rules: [{ required: true, message: '请输入服务描述信息' }]}
            ]"/>
        </a-form-item>
        <a-form-item
          label="服务图标"
          hasFeedback
          :labelCol="{lg: {span: 4}, sm: {span: 4}}"
          :wrapperCol="{lg: {span: 17}, sm: {span: 17} }">
          <a-input
            v-decorator="[
              'avatar',
              {rules: [{ required: true,type: 'url', message: '请输入服务图标地址', asyncValidator: checkAvatar}],
               validateTrigger: ['change', 'blur']}
            ]"
            name="name"
            placeholder="请输入服务图标地址"
          />
          <a-avatar class="card-avatar" :src="serviceModel.avatar" size="large"/>
        </a-form-item>
      </a-form>
    </a-modal>
    <a-modal title="修改服务" style="top: 150px;" :width="800" v-model="serviceModel.edit.visible">
      <template slot="footer">
        <a-button key="back" @click="cancelServiceUpdate">
          放弃
        </a-button>
        <a-button key="submit" type="primary" :disabled="serviceModel.submit_disable" @click="ServiceUpdate">
          确定
        </a-button>
      </template>
      <a-form :form="serviceModel.form">
        <a-form-item
          label="服务名"
          hasFeedback
          :labelCol="{lg: {span: 4}, sm: {span: 4}}"
          :wrapperCol="{lg: {span: 17}, sm: {span: 17} }">
          <a-input
            v-decorator="[
              'name',
              {rules: [{ required: true, message: '请输入服务名' }]}
            ]"
            name="name"
            placeholder="给服务名起个名字"/>
        </a-form-item>
        <a-form-item
          label="服务描述"
          hasFeedback
          :labelCol="{lg: {span: 4}, sm: {span: 4}}"
          :wrapperCol="{lg: {span: 17}, sm: {span: 17} }">
          <a-textarea
            rows="4"
            placeholder="请输入服务描述信息"
            v-decorator="[
              'description',
              {rules: [{ required: true, message: '请输入服务描述信息' }]}
            ]"/>
        </a-form-item>
        <a-form-item
          label="服务图标"
          hasFeedback
          :labelCol="{lg: {span: 4}, sm: {span: 4}}"
          :wrapperCol="{lg: {span: 17}, sm: {span: 17} }">
          <a-input
            v-decorator="[
              'avatar',
              {rules: [{ required: true, type: 'url', message: '请输入服务图标地址', asyncValidator: checkAvatar}],
               validateTrigger: ['change', 'blur']}
            ]"
            name="name"
            placeholder="请输入服务图标地址"
          />
          <a-avatar class="card-avatar" :src="serviceModel.avatar" size="large"/>
        </a-form-item>
      </a-form>
    </a-modal>
    <a-modal
      title="删除确认"
      style="top: 150px;"
      :width="800"
      v-model="serviceModel.delete.visible"
    >
      <template slot="footer">
        <a-button key="back" @click="cancelServiceDelete">
          放弃
        </a-button>
        <a-button key="submit" type="primary" :disabled="serviceModel.delete.disable" @click="ServiceDelete">
          确定
        </a-button>
      </template>
      <div>
        <a-alert
          :message="serviceModel.delete.message"
          banner
        />
        <a-input
          placeholder="请输入指定内容"
          v-model="serviceModel.delete.VerificationInput"
          @blur="checkServiceDeleteVerification"
          @keyup="checkServiceDeleteVerification"/>
      </div>
    </a-modal>
    <feature-group
      :visible="featureGroup.visible"
      :serviceId="featureGroup.serviceId"
      @cancel="handleCancel"
      @ok="handleCancel"
      @close="handleCancel"/>
    <create-perm-group-form ref="createPermGroup"/>
    <feature-grant :service-id="featureGroup.serviceId" ref="featureGrant"/>
  </a-card>
</template>

<script>
/* eslint-disable space-before-function-paren */
import { create, listPage, update, del, validateSid, findByFeatureKey } from '@/api/feature-service'
import FeatureGroup from '@/views/system/permission/FeatureGroup'
import FeatureGrant from '@/views/system/permission/FeatureGrant'
import CreatePermGroupForm from '@/views/system/permission/CreatePermGroupForm'

export default {
  name: 'FeatureList',
  components: {
    FeatureGroup,
    CreatePermGroupForm,
    FeatureGrant
  },
  data () {
    return {
      dataSource: [],
      pageNo: 1,
      pageSize: 5,
      queryString: null,
      pagination: {
        onChange: (current, size) => {
          this.selectPage(current, size)
        },
        pageSize: 5,
        total: 0,
        pageSizeOptions: ['5', '10', '20', '30', '40'],
        showTotal: total => `共 ${total} 条数据`,
        onShowSizeChange: (current, size) => {
          this.pagination.pageSize = size
          this.selectPage(current, size)
        },
        showSizeChanger: true,
        showQuickJumper: true
      },
      featureGroup: {
        visible: false,
        serviceId: ''
      },
      serviceModel: {
        visible: false,
        submit_disable: false,
        mdl: {},
        edit: {
          visible: false
        },
        delete: {
          id: '',
          disable: true,
          message: '',
          VerificationCode: '',
          VerificationInput: '',
          visible: false
        },
        avatar: '',
        form: this.$form.createForm(this)
      }
    }
  },
  created () {
    this.selectPage(1, 5)
  },
  methods: {
    makeid (length) {
      let result = ''
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
      const charactersLength = characters.length
      for (var i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength))
      }
      return result
    },
    selectPage (pageNo, pageSize) {
      const page = {
        pageNo: pageNo,
        pageSize: pageSize
      }
      listPage(page).then(rsp => {
        this.dataSource = rsp.data.data
        this.pagination.total = rsp.data.total
        this.pagination.current = rsp.data.pageNo
      })
    },
    handleAddService () {
      this.serviceModel.visible = true
      this.serviceModel.avatar = ''
      this.serviceModel.form = this.$form.createForm(this)
    },
    cancelServiceAdd () {
      this.serviceModel.visible = false
    },
    ServiceAdd (e) {
      this.serviceModel.form.validateFields((err, values) => {
        if (!err) {
          this.serviceModel.visible = false
          const body = {
            name: values.name,
            sid: values.sid,
            description: values.description,
            avatar: values.avatar
          }
          create(body).then(
            () => {
              this.$nextTick(() => {
                this.serviceRefresh()
              })
            }
          )
        }
      })
    },
    checkAvatar (rule, value, callback) {
      if (value && value.match(/\.(jpeg|jpg|gif|png)$/) != null) {
        callback()
        this.serviceModel.avatar = value
      } else {
        rule.message = '请输入有效的URL，以(jpeg|jpg|gif|png)结尾'
        callback(new Error(rule.message))
      }
    },
    editService (record) {
      this.serviceModel.edit.visible = true
      this.serviceModel.mdl = Object.assign({}, record)
      this.serviceModel.avatar = record.avatar
      this.$nextTick(() => {
        this.serviceModel.form.setFieldsValue({
          name: record.name,
          description: record.description,
          avatar: record.avatar
        })
      })
    },
    cancelServiceUpdate () {
      this.serviceModel.edit.visible = false
    },
    ServiceUpdate () {
      this.$nextTick(() => {
        this.serviceModel.form.validateFields((err, values) => {
          if (!err) {
            this.serviceModel.edit.visible = false
            const body = {
              id: this.serviceModel.mdl.id,
              name: values.name,
              description: values.description,
              avatar: values.avatar
            }
            update(body).then(
              () => {
                this.$nextTick(() => {
                  this.serviceRefresh()
                })
              }
            )
          }
        })
      })
    },
    deleteService (record) {
      this.serviceModel.delete.VerificationCode = this.makeid(6)
      this.serviceModel.delete.VerificationInput = null
      this.serviceModel.delete.id = record.id
      this.serviceModel.delete.message = `'${record.name}'被删除后数据将很难恢复，如确实需要删除，请输入:${this.serviceModel.delete.VerificationCode}`
      this.serviceModel.delete.visible = true
    },
    cancelServiceDelete () {
      this.serviceModel.delete.visible = false
      this.serviceModel.delete.id = ''
    },
    ServiceDelete () {
      del(this.serviceModel.delete.id).then(
        () => {
          this.$nextTick(() => {
            this.serviceRefresh()
          })
        }
      )
      this.serviceModel.delete.visible = false
      this.serviceModel.delete.id = ''
    },
    checkServiceDeleteVerification () {
      this.serviceModel.delete.disable = (this.serviceModel.delete.VerificationCode !== this.serviceModel.delete.VerificationInput)
    },
    serviceRefresh () {
      this.selectPage(this.pagination.current, this.pagination.pageSize)
    },
    FeatureGroupManage (record) {
      if (this.featureGroup.visible === true) {
        this.featureGroup.visible = false
      } else {
        this.featureGroup.serviceId = record.id
        this.featureGroup.visible = true
      }
    },
    handleCancel (e) {
      this.featureGroup.visible = false
    },
    validateSid: (rule, value, callback) => {
      if (!value || value.trim() === '') {
        callback(new Error('SID不允许为空'))
      } else if (!/^[a-zA-Z]+/.test(value.trim())) {
        rule.message = 'SID必须已字母开头'
        callback(new Error(rule.message))
      } else if (value.trim().length < 5) {
        rule.message = 'SID太短，不被允许，至少需要5位'
        callback(new Error(rule.message))
      } else if (value.trim().length > 10) {
        rule.message = 'SID太长，不被允许，至多包含10位'
        callback(new Error(rule.message))
      } else {
        return validateSid(value).then(
          res => {
            if (res.data === false) {
              callback()
            } else {
              rule.message = value + '已经被占用'
              callback(new Error(rule.message))
            }
          }, () => {
            rule.message = value + '后台验证出错!'
            callback(new Error(rule.message))
          }
        ).catch(() => {
          rule.message = value + '后台验证出错'
          callback(new Error(rule.message))
        })
      }
    },
    handleQuery () {
      const page = {
        pageNo: 1,
        pageSize: this.pagination.pageSize,
        params: this.queryString
      }
      findByFeatureKey(page).then(
        rsp => {
          this.dataSource = rsp.data.data
          this.pagination.total = rsp.data.total
          this.pagination.current = rsp.data.pageNo
        }
      )
    },
    handleQueryKeyPress () {
      this.handleQuery()
    },
    handleFeatureGroupGrant (record) {
      this.featureGroup.serviceId = record.id
      this.$refs.featureGrant.show(record)
    }
  },
  watch: {
    dataSource () {
      this.$forceUpdate()
    }
  }
}
</script>

<style lang="less" scoped>
  .ant-avatar-lg {
    width: 48px;
    height: 48px;
    line-height: 48px;
  }

  .list-content-item {
    color: rgba(0, 0, 0, .45);
    display: inline-block;
    vertical-align: middle;
    font-size: 14px;
    margin-left: 40px;

    span {
      line-height: 20px;
    }

    p {
      margin-top: 4px;
      margin-bottom: 0;
      line-height: 22px;
    }
  }

  .serviceId {
    color: blue;
    margin-left: 10px;
  }
</style>
