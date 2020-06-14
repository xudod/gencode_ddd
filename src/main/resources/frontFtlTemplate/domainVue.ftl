<template>
  <div>
    <el-breadcrumb separator="/" style="margin-bottom: 15px;">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>${domainCnName}</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <div>
        <el-button type="primary" @click="add${domainName}DV = true" style="margin-right: 20px; margin-bottom: 15px;">添加${domainCnName}</el-button>
      </div>
      <el-table :data="${domainName}List" stripe border size="mini">
        <el-table-column type="index"></el-table-column>
	    <#list frontTableInfoList as frontInfo>
	    <#if frontInfo.tableShow == "true">
        <el-table-column label="${frontInfo.fieldLabel}" prop="${frontInfo.fieldName}"></el-table-column>
		</#if>
		</#list>
        <el-table-column label="状态" width="80px">
          <template slot-scope='scope'>
            <el-switch v-model="scope.row.status" active-value="1" inactive-value="0" @change="${domainName}StateChange(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120px">
          <template slot-scope='scope'>
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit${domainName}DVShow(scope.row.id)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="del${domainName}ById(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.current" :page-sizes="[10, 20, 50, 100]" :page-size="queryInfo.size" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>
    <el-dialog title="添加${domainCnName}" :visible.sync="add${domainName}DV" width="750px" :before-close="handleClose" @close="add${domainName}DVClose">
      <el-form inline :model="add${domainName}Form" :rules="add${domainName}FormRules" ref="add${domainName}FormRef" label-width="120px" size="small">
        <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "true">
        <el-form-item label="${frontInfo.fieldLabel}" prop="${frontInfo.fieldName}">
          <el-input v-model="add${domainName}Form.${frontInfo.fieldName}"></el-input>
        </el-form-item>
		</#if>
		</#list>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="add${domainName}DV = false">取 消</el-button>
        <el-button type="primary" @click="add${domainName}">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="编辑${domainCnName}" :visible.sync="edit${domainName}DV" width="750px" @close="edit${domainName}DVClose">
      <el-form inline :model="edit${domainName}Form" :rules="edit${domainName}FormRules" ref="edit${domainName}FormRef" label-width="120px" size="small">
        <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "true">
        <el-form-item label="${frontInfo.fieldLabel}" prop="${frontInfo.fieldName}">
          <el-input v-model="edit${domainName}Form.${frontInfo.fieldName}"></el-input>
        </el-form-item>
		</#if>
		</#list>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="edit${domainName}DV = false">取 消</el-button>
        <el-button type="primary" @click="edit${domainName}">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        query: '',
        // 当前的页数
        current: 1,
        // 当前每页显示多少条数据
        size: 10
      },
      total: 0,
      ${domainName}List: null,
      add${domainName}DV: false,
      add${domainName}Form: {
        <#list frontFormInfoList as frontInfo>
        <#if frontInfo.fromShow == "true">
        ${frontInfo.fieldName}: ''<#if frontInfo_has_next>,</#if>
        </#if>
        </#list>
      },
      add${domainName}FormRules: {
        <#list frontFormInfoList as frontInfo>
        <#if frontInfo.fromShow == "true">
        ${frontInfo.fieldName}: [
          <#if frontInfo.fieldRequired == "true">
          { required: true, message: '该属性不能为空', trigger: 'blur' },
          </#if>
          { max: ${frontInfo.fieldMax}, message: '长度不能超过 ${frontInfo.fieldMax} 个字符', trigger: 'blur' }
        ]<#if frontInfo_has_next>,</#if>
	    </#if>
		</#list>
      },
      edit${domainName}DV: false,
      edit${domainName}Form: {
	    <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "true">
        ${frontInfo.fieldName}: ''<#if frontInfo_has_next>,</#if>
	    </#if>
		</#list>
      },
      edit${domainName}FormRules: {
	    <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "true">
        ${frontInfo.fieldName}: [
          <#if frontInfo.fieldRequired == "true">
          { required: true, message: '该属性不能为空', trigger: 'blur' },
          </#if>
          { max: ${frontInfo.fieldMax}, message: '长度不能超过 ${frontInfo.fieldMax} 个字符', trigger: 'blur' }
        ]<#if frontInfo_has_next>,</#if>
	    </#if>
		</#list>
      }
    }
  },
  created() {
    this.get${domainName}List()
  },
  methods: {
    async get${domainName}List() {
      const { data: res } = await this.$http.post('/api/${domainUrl}/getpage', this.queryInfo)
      if (res.code !== 200) return this.$message.error(res.message)
      this.${domainName}List = res.data.records
      this.total = res.data.total
    },
    // 监听 pagesize 改变的事件
    handleSizeChange(newSize) {
      // console.log(newSize)
      this.queryInfo.size = newSize
      this.get${domainName}List()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange(newPage) {
      console.log(newPage)
      this.queryInfo.current = newPage
      this.get${domainName}List()
    },
    async ${domainName}StateChange(domaimInfo) {
      console.log(domaimInfo)
      const { data: res } = await this.$http.post('/api/${domainUrl}/upstatebykey', domaimInfo)
      if (res.code !== 200) {
        if (domaimInfo.useStatus === '1') {
          domaimInfo.useStatus = '0'
        } else {
          domaimInfo.useStatus = '1'
        }
        return this.$message.error(res.message)
      }
      this.$message({
        showClose: true,
        message: '更新${domainCnName}状态成功！',
        type: 'success',
        duration: 1500
      })
      this.get${domainName}List()
    },
    add${domainName}DVClose() {
      this.$refs.add${domainName}FormRef.resetFields()
    },
    async add${domainName}Btn() {
      const { data: res } = await this.$http.post('/api/${domainUrl}/getbykey', { id: this.projectId })
      if (res.code !== 200) {
        this.$message.error(res.message)
      }
      res.data.id = undefined
      this.add${domainName}Form = res.data
      this.add${domainName}Form.projectId = this.projectId
      this.add${domainName}DV = true
    },
    add${domainName}() {
      this.$refs.add${domainName}FormRef.validate(async valid => {
        if (!valid) return false
        const { data: res } = await this.$http.post('/api/${domainUrl}/add', this.add${domainName}Form)
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message({
          showClose: true,
          message: res.message,
          type: 'success',
          duration: 1500
        })
        this.add${domainName}DV = false
        this.get${domainName}List()
      })
    },
    edit${domainName}DVClose() {
      this.$refs.edit${domainName}FormRef.resetFields()
    },
    handleClose() {

    },
    async edit${domainName}DVShow(id) {
      this.edit${domainName}DV = true
      this.edit${domainName}Form.id = id
      const { data: res } = await this.$http.post('/api/${domainUrl}/getbykey', this.edit${domainName}Form)
      if (res.code !== 200) {
        this.$message.error(res.message)
      }
      this.edit${domainName}Form = res.data
    },
    edit${domainName}() {
      this.$refs.edit${domainName}FormRef.validate(async valid => {
        if (!valid) return false
        const { data: res } = await this.$http.post('/api/${domainUrl}/upbykey', this.edit${domainName}Form)
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.edit${domainName}DV = false
        this.get${domainName}List()
        this.$message.success(res.message)
      })
    },
    del${domainName}ById(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const { data: res } = await this.$http.post('/api/${domainUrl}/delbykey', { id })
        if (res.code !== 200) {
          return this.$message.error(res.message)
        }
        this.get${domainName}List()
        this.$message.success(res.message)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style lang="less" scoped>
.el-pagination {
  margin-top: 10px;
}
.el-form {
  margin: 0 35px !important;
}
</style>
