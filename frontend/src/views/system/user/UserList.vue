<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="用户名">
              <a-input placeholder="请输入" v-model="queryString" @keypress="handleKeypressQuery"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="handleQuery">查询</a-button>
              <a-button style="margin-left: 8px" @click="resetQuery">重置</a-button>
            </span>
          </a-col>
          <a-col :md="8" :sm="24" class="table-head-operations">
            <span >
              <a-button type="primary" @click="handleAdd">新增用户</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <s-table
      size="default"
      :columns="columns"
      :data="loadData"
      :pageSize = 5
      :showPagination = true
      :pagination = "pagination"
      :expandIcon="expandIcon"
      rowKey="id"
      ref="table"
    >
      <div
        slot="expandedRowRender"
        slot-scope="record"
        style="margin: 0">
        <a-row
          :gutter="24"
          :style="{ marginBottom: '12px' }">
          <a-col :lg="4" :md="24">
            <a-statistic title="用户类型" :value="record.type | utypetrans" :value-style="{ color: '#3f8600', fontSize: '16px' }" style="margin-right: 50px" />
          </a-col>

          <a-col :lg="4" :md="24">
            <a-statistic title="是否冻结" :value="record.frozen | boolizer" :value-style="{ color: '#3f8600', fontSize: '16px' }" style="margin-right: 50px" />
          </a-col>

          <a-col :lg="4" :md="24">
            <a-statistic title="用户密级" :value="record.userCredentialLevel | credential" :value-style="{ color: '#3f8600', fontSize: '16px' }" style="margin-right: 50px" />
          </a-col>

          <a-col :lg="4" :md="24">
            <a-statistic title="邮箱" :value="record.email | mask_email" :value-style="{ color: '#3f8600', fontSize: '16px' }" style="margin-right: 50px" />
          </a-col>

          <a-col :lg="4" :md="24">
            <a-statistic title="电话" :value="record.phone | mask_phone" :value-style="{ color: '#3f8600', fontSize: '16px' }" style="margin-right: 50px" />
          </a-col>
        </a-row>
      </div>
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit(record)" class="action">编辑</a>
        <a v-show="!isUserFrozen(record)" @click="handleFreeze(record)"  class="action">冻结</a>
        <a v-show="isUserFrozen(record)"  @click="handleunFreeze(record)"  class="action">解冻</a>
        <span slot="actions">
          <a-dropdown class="action">
            <a-menu slot="overlay">
              <a-menu-item><a @click="resetPassword(record)">重置密码</a></a-menu-item>
<!--              <a-menu-item><a @click="bindMobile(record)">绑定手机</a></a-menu-item>-->
<!--              <a-menu-item><a @click="bindEMail(record)">绑定邮箱</a></a-menu-item>-->
              <a-menu-divider></a-menu-divider>
              <a-menu-item><a @click="handleDelete(record)"><a-tag color="red" style="border: 0">删除用户</a-tag></a></a-menu-item>
            </a-menu>
            <a>更多<a-icon type="down"/></a>
          </a-dropdown>
        </span>
      </span>
    </s-table>

    <a-modal
      :title="modalTitle"
      style="top: 20px;"
      :width="800"
      v-model="visible"
      @ok="handleOk"
    >
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="唯一识别码"
          hasFeedback
        >
          <a-input
            v-decorator="[
              'uid',
              {rules: [
                { required: true, type: 'string', asyncValidator: validateUID, message: '请输入用户ID，要求具有唯一性，建议采用name@domain的形式' }],
               validateTrigger: ['blur']}
            ]"
            placeholder="唯一识别码"
            id="no"
            :disabled="winType == 'edit'"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用户名"
          hasFeedback
        >
          <a-input
            v-decorator="[
              'name',
              {rules: [{ required: true, message: '请输入用户名' }]}
            ]"
            id="user_name"
            placeholder="起一个名字" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="密码"
          hasFeedback
          v-if="winType == 'new'"
        >
          <a-input-password
            v-decorator="[
              'new_password',
              {rules: [{ required: true, message: '请输入密码', asyncValidator: validatePassword }]}
            ]"
            id="new_password"
            placeholder="请输入密码" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="确认密码"
          hasFeedback
          v-if="winType == 'new'"
        >
          <a-input-password
            v-decorator="[
              'password_confirmed',
              {
                rules: [{ required: true, message: '请再次输入密码', asyncValidator: validatePasswordConfirm }],
                validateTrigger: ['change', 'blur']
              }
            ]"
            id="password_confirmed"
            placeholder="请再次输入密码" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="手机"
          hasFeedback
          v-if="winType == 'new'"
        >
          <a-input
            v-decorator="[
              'phone',
              {rules: [
                { required: true, message: '请输入用于联系的手机电话' , pattern: /^1[34578]\d{9}$/ }],
               validateTrigger: ['change', 'blur']}
            ]"
            placeholder="13800138000"
            id="user_phone"/>
        </a-form-item>

        <a-form-item
          v-if="winType == 'new'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="电邮"
          hasFeedback
        >
          <a-input
            v-decorator="[
              'email',
              {rules: [
                { required: true, type: 'email', message: '请输入正确的电子邮箱地址'}],
               validateTrigger: ['change', 'blur']}
            ]"
            placeholder="youname@yourcompany.com"
            id="user_email"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="密级"
          hasFeedback
        >
          <a-select
            v-decorator="[
              'userCredentialLevel',
              { initialValue: 'LEVEL1',
                rules: [
                { required: false, message: '请选择用户密级'}],
               validateTrigger: ['change', 'blur']}
            ]"
            >
            <a-select-option value="LEVEL1">一般</a-select-option>
            <a-select-option value="LEVEL2">公开</a-select-option>
            <a-select-option value="LEVEL3">秘密</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态"
          hasFeedback
        >
          <a-select
            v-decorator="[
              'state',
              { initialValue: 'LOCKED',
                rules: [
                { required: false, message: '请输入用户状态'}],
               validateTrigger: ['change', 'blur']}
            ]">
            <a-select-option value="NORMAL">正常</a-select-option>
            <a-select-option value="ABNORMAL">异常</a-select-option>
            <a-select-option value="LOCKED">锁定</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="类型"
          hasFeedback
        >
          <a-select
            v-decorator="[
              'type',
              {
                initialValue: 'OTHER',
                rules: [
                { required: false, message: '请输入用户类型'}],
                validateTrigger: ['change', 'blur']}
            ]">
            <a-select-option value="ADMIN">管理员</a-select-option>
            <a-select-option value="SYS">系统人员</a-select-option>
            <a-select-option value="BIZ">业务人员</a-select-option>
            <a-select-option value="OTHER">其他</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="头像"
          hasFeedback
        >
          <a-input
            v-decorator="[
              'avatar',
              {rules: [
                { required: false, type: 'url', message: '请输入正确头像地址'}],
               validateTrigger: ['change', 'blur']}
            ]"
            placeholder="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
            />
        </a-form-item>
      </a-form>
    </a-modal>
    <a-modal
      title="删除用户"
      style="top: 150px;"
      :width="800"
      v-model="userDelConfirm.visible"
    >
      <template slot="footer">
        <a-button key="back" @click="handleCancelDeletion">
          放弃
        </a-button>
        <a-button key="submit" type="primary" :disabled="userDelConfirm.disable"  @click="handleConfirmDeletion">
          确定
        </a-button>
      </template>
      <a-alert
        :message="userDelConfirm.message"
        banner
      />
      <a-input placeholder="请输入用户ID" v-model="userDelConfirm.uid" @blur="checkInputMatchUid" @keyup="checkInputMatchUid"/>
    </a-modal>
    <a-modal
      title="重置用户密码"
      style="top: 150px;"
      :width="800"
      v-model="passwordResetConfirm.visible"
    >
      <template slot="footer">
        <a-button key="back" @click="cancelPasswordReset">
          放弃
        </a-button>
        <a-button key="submit" type="primary" :disabled="passwordResetConfirm.disable"  @click="handlePasswordReset">
          确定
        </a-button>
      </template>
      <div>
        <a-alert
          :message="passwordResetConfirm.message"
          banner
        />
        <a-radio-group v-model="passwordResetConfirm.method">
          <a-radio :style="passwordResetConfirm.radioStyle" :value="1">
            通过绑定邮箱复位密码
          </a-radio>
          <a-radio :style="passwordResetConfirm.radioStyle" :value="2">
            通过绑定手机复位密码
          </a-radio>
        </a-radio-group>
        <a-input placeholder="请输入指定内容" v-model="passwordResetConfirm.requireInput" @blur="checkResetInputConfirm" @keyup="checkResetInputConfirm"/>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import AStatistic from 'ant-design-vue/es/statistic/Statistic'
import 'ant-design-vue/es/statistic/style'
import { selectUsers, selectUserByName, validateUid, save, freeze, unfreeze, remove,
  resetPassword } from '@/api/user'
import md5 from 'md5'

export default {
  name: 'UserTblList',
  components: {
    STable,
    AStatistic
  },
  data () {
    return {
      description: '列表使用场景：后台管理中的权限管理以及角色管理，可用于基于 RBAC 设计的角色权限控制，颗粒度细到每一个操作类型。',
      modalTitle: '新增用户',
      visible: false,
      passwordResetConfirm: {
        visible: false,
        method: 1,
        requireInput: '',
        requireInputConfirmed: '',
        disable: true,
        message: '',
        radioStyle: {
          display: 'block',
          height: '30px',
          lineHeight: '30px'
        }
      },
      userDelConfirm: {
        visible: false,
        message: '',
        disable: true,
        uid: ''
      },
      winType: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: this.$form.createForm(this),
      mdl: {},

      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      queryString: null,
      // 表头
      columns: [
        {
          title: 'UID',
          dataIndex: 'uid'
        },
        {
          title: '用户名',
          dataIndex: 'name'
        },
        {
          title: '状态',
          dataIndex: 'state',
          customRender: (text, row, index) => {
            switch (text) {
              case 'NORMAL':
                return <a-tag color="cyan">正常</a-tag>
              case 'LOCKED':
                return <a-tag color="grey">锁定</a-tag>
              case 'ABNORMAL':
                return <a-tag color="red">异常</a-tag>
              default:
                return text
            }
          }
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      innerColumns: [
        { title: 'Date', dataIndex: 'date', key: 'date' },
        { title: 'Name', dataIndex: 'name', key: 'name' },
        { title: 'Status', key: 'state', scopedSlots: { customRender: 'status' } },
        { title: 'Upgrade Status', dataIndex: 'upgradeNum', key: 'upgradeNum' },
        {
          title: 'Action',
          dataIndex: 'operation',
          key: 'operation',
          scopedSlots: { customRender: 'operation' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return selectUsers({
          ...parameter
        })
          .then(res => {
            console.log('selectUsers.call', res.data)
            return res.data
          })
      },
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
        this.password = value
        callback()
      },
      validatePasswordConfirm: (rule, value, callback) => {
        if (this.password && this.password !== value) {
          rule.message = '与密码输入不一致'
          callback(new Error(rule.message))
        } else {
          callback()
        }
      },
      validateUID: (rule, value, callback) => {
        if (!value || value.trim() === '') {
          callback(new Error('UID不允许为空'))
        } else if (value.trim().length < 5) {
          rule.message = 'UID太短，不被允许，至少需要5位'
          callback(new Error(rule.message))
        } else {
          return validateUid(value).then(
            res => {
              if (res.data === false) {
                callback()
              } else {
                rule.message = value + '已经被注册'
                if (this.winType === 'new') {
                  callback(new Error(rule.message))
                } else {
                  callback()
                }
              }
            }, () => {
              rule.message = value + '后台验证出错'
              callback(new Error(rule.message))
            }
          ).catch(() => {
            rule.message = value + '后台验证出错'
            callback(new Error(rule.message))
          })
        }
      },
      selectedRowKeys: [],
      selectedRows: [],
      pagination: {
        defaultPageSize: 5,
        pageSize: 5,
        showTotal: total => `共 ${total} 条数据`,
        showSizeChanger: true,
        pageSizeOptions: ['5', '10', '15', '20', '40']
      }
    }
  },
  created () {
  },
  methods: {
    handleEdit (record) {
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.winType = 'edit'
      this.modalTitle = '修改用户'

      this.$nextTick(() => {
        this.form.setFieldsValue({ ...record })
      })
    },
    handleAdd (record) {
      this.mdl = Object.assign({}, {})
      this.form = this.$form.createForm(this)
      this.visible = true
      this.winType = 'new'
      this.modalTitle = '新增用户'
    },
    handleOk (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          this.visible = false
          if (values.password_confirmed) {
            // md5 used
            values.encryptedPassword = md5(values.password_confirmed)
          }
          values.id = this.mdl.id ? this.mdl.id : null
          // console.log(this.$bcrypt.hashSync(values.password_confirmed, 'salt'))
          save(values).then(() => {
            this.$nextTick(() => {
              this.$refs.table.refresh()
            })
          })
        }
      })
    },
    handleKeypressQuery (event) {
      if (event.keyCode === 13) {
        this.handleQuery()
      }
    },
    handleQuery () {
      if (!this.queryString || this.queryString.trim() === '') {
        this.resetQuery()
        return
      }
      this.loadData = parameter => {
        return selectUserByName({
          ...parameter,
          params: {
            name: this.queryString
          }
        })
          .then(res => {
            console.log('selectUserByName.call', res.data)
            return res.data
          })
      }
      this.$nextTick(() => {
        this.$refs.table.refresh()
      })
    },
    resetQuery () {
      this.loadData = parameter => {
        return selectUsers({
          ...parameter
        })
          .then(res => {
            console.log('selectUsers.call', res.data)
            return res.data
          })
      }
      this.queryString = null
      this.$nextTick(() => {
        this.$refs.table.refresh()
      })
    },
    handleFreeze (record) {
      freeze(record.id).then(() => {
        this.$nextTick(() => {
          this.$refs.table.refresh()
        })
      })
    },
    handleunFreeze (record) {
      unfreeze(record.id).then(
        () => {
          this.$nextTick(() => {
            this.$refs.table.refresh()
          })
        }
      )
    },
    handleDelete (record) {
      this.userDelConfirm.visible = true
      this.userDelConfirm.uid = ''
      this.userDelConfirm.disable = true
      this.userDelConfirm.message = '账户删除后将很难找回，如只是临时限制使用，你可以锁定或临时冻结。如确实需要删除，请根据提示输入对应的用户ID:  '.concat(record.uid)
      this.userDelConfirm.body = Object.assign({}, record)
    },
    resetPassword (record) {
      this.passwordResetConfirm.visible = true
      this.passwordResetConfirm.uid = record.uid
      this.passwordResetConfirm.disable = true
      this.passwordResetConfirm.requireInputConfirmed = this.makeid(6)
      this.passwordResetConfirm.requireInput = ''
      this.passwordResetConfirm.message = '将通过用户绑定的邮箱或手机给用户发送临时密码复位连接，为防止误操作，请输入:  '.concat(this.passwordResetConfirm.requireInputConfirmed)
    },
    handleConfirmDeletion () {
      const body = [
        this.userDelConfirm.body
      ]
      remove(body).then(
        () => {
          this.$nextTick(() => {
            this.$refs.table.refresh()
          })
        }
      )
      this.userDelConfirm.uid = ''
      this.userDelConfirm.visible = false
      this.userDelConfirm.body = null
      this.userDelConfirm.message = null
    },
    handleCancelDeletion () {
      this.userDelConfirm.uid = ''
      this.userDelConfirm.visible = false
      this.userDelConfirm.body = null
      this.userDelConfirm.message = null
    },
    handlePasswordReset () {
      this.passwordResetConfirm.visible = false
      this.passwordResetConfirm.uid = null
      resetPassword({
        method: this.passwordResetConfirm.method,
        uid: this.passwordResetConfirm.uid
      })
    },
    cancelPasswordReset () {
      this.passwordResetConfirm.visible = false
      this.passwordResetConfirm.uid = null
    },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    checkInputMatchUid () {
      this.userDelConfirm.disable = (this.userDelConfirm.uid !== this.userDelConfirm.body.uid)
    },
    checkResetInputConfirm () {
      this.passwordResetConfirm.disable = (this.passwordResetConfirm.requireInput !== this.passwordResetConfirm.requireInputConfirmed)
    },
    expandIcon (props) {
      const {
        onExpand,
        expanded
      } = props

      if (expanded) {
        return <a-icon type="down" onClick={onExpand}/>
      } else {
        return <a-icon type="right" onClick={onExpand}/>
      }
    },
    makeid (length) {
      let result = ''
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
      const charactersLength = characters.length
      for (var i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength))
      }
      return result
    },
    isUserFrozen (record) {
      return record.frozen
    }
  },
  watch: {
  }
}
</script>

<style lang="less" scoped>
  .table-head-operations {
     text-align: right;
  }

  .field {
    margin-left: 10px;
    font-size: larger;
  }

  .action {
    margin-left: 5px;
  }
</style>
