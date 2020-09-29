<template>
  <a-modal
    title="功能管理"
    style="top: 80px;"
    :width="1440"
    v-model="display"
    @cancel="handleCancel"
    @ok="handleOk"
  >
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="功能组名">
                <a-input v-model="queryParam.groupName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="功能组描述">
                <a-input v-model="queryParam.groupDesc" placeholder=""/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="功能名">
                  <a-input v-model="queryParam.featureName" placeholder=""/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="功能描述">
                  <a-input v-model="queryParam.featureDesc" placeholder=""/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="功能授权码">
                  <a-input v-model="queryParam.securityCode" placeholder=""/>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="handleQuery">查询</a-button>
              <a-button style="margin-left: 8px" @click="resetSearchForm">重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
            </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建功能组</a-button>
        <a-button type="dashed" @click="tableOption">{{ optionAlertShow && '关闭' || '开启' }}多选</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1"><a-icon type="delete" />删除</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="master.columns"
        :data="master.loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        :pageSize = 5
        :pagination = "pagination"
        :expandIcon="expandIcon"
        :showPagination = true
        :expand="OnExpaned"
        :expandedRowKeys="master.expandedRowKeys"
        :expandRowByClick = "master.expandRowByClick"
      >
      <div
        slot="expandedRowRender"
        slot-scope="record"
        style="margin: 0">
        <s-table
          size="default"
          rowKey="id"
          ref = "slave"
          :columns="detail.columns"
          :data="slaveDataLoad(record)"
          :pageSize = 5
        >
          <span slot="action" slot-scope="text, record">
            <template>
              <a @click="handleEditFeature(record)">编辑功能</a>
            </template>
          </span>
        </s-table>
      </div>
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click.stop="handleAddFeature(record)" class="action">添加功能</a>
        </template>
      </span>
      </s-table>
      <create-feature-group-form :service-id="svcId" ref="createModal" @ok="handleInternalOk" />
      <create-feature-form ref="modal" :group-id="groupId" @ok="handleInternalOk"></create-feature-form>
    </a-card>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import { findFeatureGroupsByServiceId, findFeatureGroups, findFeaturesByGroupId } from '@/api/feature-service'
import CreateFeatureGroupForm from '@/views/system/permission/CreateFeatureGroupForm'
import CreateFeatureForm from '@/views/system/permission/CreateFeatureForm'

export default {
  name: 'FeatureGroup',
  components: {
    STable,
    Ellipsis,
    CreateFeatureGroupForm,
    CreateFeatureForm
  },
  props: {
    visible: {
      type: Boolean,
      default: true
    },
    serviceId: {
      type: String,
      default: '',
      required: true
    }
  },
  data () {
    return {
      display: this.visible,
      svcId: this.serviceId,
      groupId: '',
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      master: {
        expandRowByClick: true,
        expandedRowKeys: [],
        columns: [
          {
            title: '功能组名',
            dataIndex: 'name'
          },
          {
            title: '功能组描述',
            dataIndex: 'description',
            scopedSlots: { customRender: 'description' },
            customRender: (text, row, index) => {
              return <ellipsis length='20' tooltip> {{ text }} </ellipsis>
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            width: '150px',
            scopedSlots: { customRender: 'action' }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          const query = {
            ...parameter,
            params: this.svcId
          }
          return findFeatureGroupsByServiceId(query)
            .then(rsp => {
              return rsp.data
            })
        }
      },
      detail: {
        columns: [
          {
            title: '功能',
            dataIndex: 'name'
          },
          {
            title: '功能代码',
            dataIndex: 'code',
            customRender: (text, row, index) => {
              return <a-tag color="cyan"> {{ text }} </a-tag>
            }
          },
          {
            title: '功能描述',
            dataIndex: 'description',
            scopedSlots: { customRender: 'description' },
            customRender: (text, row, index) => {
              return <ellipsis length='30' tooltip> {{ text }} </ellipsis>
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            width: '150px',
            scopedSlots: { customRender: 'action' }
          }
        ]
      },
      pagination: {
        defaultPageSize: 5,
        pageSize: 5,
        showTotal: total => `共 ${total} 条数据`,
        showSizeChanger: true,
        pageSizeOptions: ['5', '10', '15', '20', '40']
      },
      selectedRowKeys: [],
      selectedRows: [],

      // custom table alert & rowSelection
      options: {
        alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  watch: {
    visible (newVal, oldVal) {
      this.display = newVal
    },
    serviceId (newVal, oldVal) {
      this.svcId = newVal
      this.master.loadData = parameter => {
        const query = {
          ...parameter,
          params: this.svcId
        }
        return findFeatureGroupsByServiceId(query)
          .then(rsp => {
            return rsp.data
          })
      }
      // this.$nextTick(() => {
      //   this.$refs.table.refresh()
      // })
    }
  },
  created () {
    this.tableOption()
  },
  methods: {
    handleCancel (e) {
      this.$emit('cancel', e)
    },
    handleOk (e) {
      this.$emit('ok', e)
    },
    tableOption () {
      if (!this.optionAlertShow) {
        this.options = {
          alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
          rowSelection: {
            selectedRowKeys: this.selectedRowKeys,
            onChange: this.onSelectChange,
            getCheckboxProps: record => ({
              props: {
                disabled: record.no === 'No 2', // Column configuration not to be checked
                name: record.no
              }
            })
          }
        }
        this.optionAlertShow = true
      } else {
        this.options = {
          alert: false,
          rowSelection: null
        }
        this.optionAlertShow = false
      }
    },

    handleAddFeature (record) {
      console.log(record)
      this.groupId = record.id
      this.$refs.modal.add(record)
      this.$nextTick(() => {
        this.$refs.slave.refresh()
      })
    },
    handleInternalOk () {
      this.$nextTick(() => {
        this.$refs.table.refresh()
        this.$refs.slave.refresh()
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    resetSearchForm () {
      this.queryParam = {}
      this.handleQuery()
    },
    handleQuery () {
      this.master.loadData = parameter => {
        const query = {
          ...parameter,
          params: {
            ...this.queryParam,
            serviceId: this.svcId
          }
        }
        return findFeatureGroups(query)
          .then(rsp => {
            return rsp.data
          })
      }
      this.$nextTick(() => {
        this.$refs.table.refresh()
      })
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
    OnExpaned (expanded, record) {
      if (expanded) {
        this.master.expandedRowKeys = []
        this.master.expandedRowKeys.push(record.id)
      } else {
        this.master.expandedRowKeys = []
      }
    },
    slaveDataLoad (record) {
      const loadData = parameter => {
        const query = {
          ...parameter,
          params: record.id
        }
        return findFeaturesByGroupId(query)
          .then(rsp => {
            return rsp.data
          })
      }
      return loadData
    },
    handleEditFeature (record) {
      this.groupId = record.gid
      this.$refs.modal.edit(record)
      this.$nextTick(() => {
        this.$refs.slave.refresh()
      })
    }
  }
}
</script>

<style scoped>
</style>
