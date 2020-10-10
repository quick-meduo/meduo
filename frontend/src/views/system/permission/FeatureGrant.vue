<template>
  <a-modal
    :title="title"
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
        <a-button type="primary" icon="plus" @click="$refs.createPermGroup.add()">新建授权组</a-button>
        <a-button icon="refresh" @click="serviceRefresh()">刷新数据</a-button>
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
        :pageSize="5"
        :pagination="master.pagination"
        :showPagination="true"
        :expandIcon="expandIcon"
        :expand="OnExpaned"
        :expandedRowKeys="master.expandedRowKeys"
        :expandRowByClick = "master.expandRowByClick"
      >
        <div
          slot="expandedRowRender"
          style="margin: 0">
          <a-list size="large" :pagination="slave.pagination" :data-source="slave.dataSource">
            <a-list-item :key="item.id" slot="renderItem" slot-scope="item">
              <a-list-item-meta :description="item.description">
                <a-avatar slot="avatar" size="large" shape="square" src="/feature.png">
                </a-avatar>
                <a slot="title">{{ item.name }}
<!--                  <a-tag class="serviceId">{{ item.sid }}</a-tag>-->
                </a>
              </a-list-item-meta>
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
        </div>
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="action">关联功能</a>
          </template>
        </span>
      </s-table>
      <create-perm-group-form :service-id="svcId" ref="createPermGroup" @ok="handleIntenalOk"/>
    </a-card>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import { findPermissionGroupByServiceId } from '@/api/permission-service'
import CreatePermGroupForm from '@/views/system/permission/CreatePermGroupForm'

export default {
  name: 'FeatureGrant',
  components: {
    STable,
    CreatePermGroupForm,
    Ellipsis
  },
  data () {
    return {
      display: false,
      title: '功能授权',
      svcId: '',
      groupId: '',
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      master: {
        expandedRowKeys: [],
        expandRowByClick: true,
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
              return <ellipsis length="20" tooltip> {{ text }} </ellipsis>
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
          return findPermissionGroupByServiceId(query)
            .then(rsp => {
              return rsp.data
            })
        },
        pagination: {
          defaultPageSize: 5,
          pageSize: 5,
          showTotal: total => `共 ${total} 条数据`,
          showSizeChanger: true,
          pageSizeOptions: ['5', '10', '15', '20', '40']
        }
      },
      slave: {
        dataSource: [],
        pagination: {
          onChange: (current, size) => {
            this.slaveSelectPage(current, size)
          },
          onShowSizeChange: (current, size) => {
            this.pagination.pageSize = size
            this.slaveSelectPage(current, size)
          },
          defaultPageSize: 5,
          pageSize: 5,
          showTotal: total => `共 ${total} 条数据`,
          showSizeChanger: true,
          pageSizeOptions: ['5', '10', '15', '20', '40']
        }
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
      optionAlertShow: true
    }
  },
  watch: {
    visible (newVal, oldVal) {
      this.display = newVal
    },
    svcId (newVal, oldVal) {
      this.master.loadData = parameter => {
        const query = {
          ...parameter,
          params: newVal
        }
        return findPermissionGroupByServiceId(query)
          .then(rsp => {
            return rsp.data
          })
      }
      this.$nextTick(() => {
        this.$refs.table.refresh()
      })
    },
    dataSource () {
      this.$forceUpdate()
    }
  },
  created () {
    this.selectPage(1, 5)
  },
  methods: {
    handleCancel (e) {
      this.$emit('cancel', e)
    },
    handleOk (e) {
      this.$emit('ok', e)
    },
    handleIntenalOk (e) {
      this.$refs.table.refresh()
    },
    show (item) {
      this.title = '功能授权 | ' + item.name
      this.svcId = item.id
      this.display = true
    },
    hide () {
      this.display = false
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
        return findPermissionGroupByServiceId(query)
          .then(rsp => {
            return rsp.data
          })
      }
      this.$nextTick(() => {
        this.$refs.table.refresh()
      })
    },
    selectPage (pageNo, pageSize) {
      const page = {
        pageNo: pageNo,
        serviceId: this.svcId,
        pageSize: pageSize
      }
      findPermissionGroupByServiceId(page).then(rsp => {
        this.dataSource = rsp.data.data
        this.slave.pagination.total = rsp.data.total
        this.slave.pagination.current = rsp.data.pageNo
      })
      this.$nextTick(() => {
        this.$refs.table.refresh()
      })
    },
    OnExpaned (expanded, record) {
      if (expanded) {
        this.slave.record = record
        this.slaveSelectPage(1, 5)
        this.master.expandedRowKeys = []
        this.master.expandedRowKeys.push(record.id)
      } else {
        this.master.expandedRowKeys = []
      }
    },
    slaveSelectPage (pageNo, pageSize) {
      const query = {
        pageNo: pageNo,
        pageSize: pageSize,
        params: this.slave.record.id
      }
      return findPermissionGroupByServiceId(query)
        .then(rsp => {
          this.slave.dataSource = rsp.data.data
          this.slave.pagination.total = rsp.data.total
          this.slave.pagination.current = rsp.data.pageNo
        })
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
    serviceRefresh () {
      this.selectPage(this.master.pagination.current, this.master.pagination.pageSize)
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
    }
  }
}
</script>

<style scoped>
</style>
