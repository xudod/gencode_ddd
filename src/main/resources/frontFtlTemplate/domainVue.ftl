<template>
  <div>
    <el-breadcrumb separator="/" style="margin-bottom: 15px;">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>${domainCnName}/${tableNameCn}</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <div>
        <el-button type="primary" @click="add${tableName}DV = true" style="margin-right: 20px; margin-bottom: 15px;">添加${tableNameCn}</el-button>
      </div>
      <el-table :data="${tableName}List" stripe border size="mini">
        <el-table-column type="index"></el-table-column>
	    <#list frontTableInfoList as frontInfo>
	    <#if frontInfo.tableShow == "1">
        <el-table-column label="${frontInfo.fieldLabel}" prop="${frontInfo.fieldName}"></el-table-column>
		</#if>
		</#list>
        <el-table-column label="状态" width="80px">
          <template slot-scope='scope'>
            <el-switch v-model="scope.row.status" active-value="1" inactive-value="0" @change="${tableName}StateChange(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120px">
          <template slot-scope='scope'>
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit${tableName}DVShow(scope.row.id)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="del${tableName}ById(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.current" :page-sizes="[10, 20, 50, 100]" :page-size="queryInfo.size" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>
    <el-dialog title="添加${tableNameCn}" :visible.sync="add${tableName}DV" width="750px" :before-close="handleClose" @close="add${tableName}DVClose">
      <el-form inline :model="add${tableName}Form" :rules="add${tableName}FormRules" ref="add${tableName}FormRef" label-width="120px" size="small">
        <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "1">
        <el-form-item label="${frontInfo.fieldLabel}" prop="${frontInfo.fieldName}">
          <el-input v-model="add${tableName}Form.${frontInfo.fieldName}"></el-input>
        </el-form-item>
		</#if>
		</#list>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="add${tableName}DV = false">取 消</el-button>
        <el-button type="primary" @click="add${tableName}">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="编辑${tableNameCn}" :visible.sync="edit${tableName}DV" width="750px" @close="edit${tableName}DVClose">
      <el-form inline :model="edit${tableName}Form" :rules="edit${tableName}FormRules" ref="edit${tableName}FormRef" label-width="120px" size="small">
        <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "1">
        <el-form-item label="${frontInfo.fieldLabel}" prop="${frontInfo.fieldName}">
          <el-input v-model="edit${tableName}Form.${frontInfo.fieldName}"></el-input>
        </el-form-item>
		</#if>
		</#list>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="edit${tableName}DV = false">取 消</el-button>
        <el-button type="primary" @click="edit${tableName}">确 定</el-button>
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
      ${tableName}List: null,
      add${tableName}DV: false,
      add${tableName}Form: {
        <#list frontFormInfoList as frontInfo>
        <#if frontInfo.fromShow == "1">
        ${frontInfo.fieldName}: ''<#if frontInfo_has_next>,</#if>
        </#if>
        </#list>
      },
      add${tableName}FormRules: {
        <#list frontFormInfoList as frontInfo>
        <#if frontInfo.fromShow == "1">
        ${frontInfo.fieldName}: [
          <#if frontInfo.fieldRequired == "1">
          { required: true, message: '该属性不能为空', trigger: 'blur' },
          </#if>
          { max: ${frontInfo.fieldMax}, message: '长度不能超过 ${frontInfo.fieldMax} 个字符', trigger: 'blur' }
        ]<#if frontInfo_has_next>,</#if>
	    </#if>
		</#list>
      },
      edit${tableName}DV: false,
      edit${tableName}Form: {
	    <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "1">
        ${frontInfo.fieldName}: ''<#if frontInfo_has_next>,</#if>
	    </#if>
		</#list>
      },
      edit${tableName}FormRules: {
	    <#list frontFormInfoList as frontInfo>
	    <#if frontInfo.fromShow == "1">
        ${frontInfo.fieldName}: [
          <#if frontInfo.fieldRequired == "1">
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
    this.get${tableName}List()
  },
  methods: {
    async get${tableName}List() {
      const { data: res } = await this.$http.post('/api/${domainUrl}/getpage', this.queryInfo)
      if (res.code !== 200) return this.$message.error(res.message)
      this.${tableName}List = res.data.records
      this.total = res.data.total
    },
    // 监听 pagesize 改变的事件
    handleSizeChange(newSize) {
      // console.log(newSize)
      this.queryInfo.size = newSize
      this.get${tableName}List()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange(newPage) {
      console.log(newPage)
      this.queryInfo.current = newPage
      this.get${tableName}List()
    },
    async ${tableName}StateChange(domaimInfo) {
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
        message: '更新${tableNameCn}状态成功！',
        type: 'success',
        duration: 1500
      })
      this.get${tableName}List()
    },
    add${tableName}DVClose() {
      this.$refs.add${tableName}FormRef.resetFields()
    },
    async add${tableName}Btn() {
      const { data: res } = await this.$http.post('/api/${domainUrl}/getbykey', { id: this.projectId })
      if (res.code !== 200) {
        this.$message.error(res.message)
      }
      res.data.id = undefined
      this.add${tableName}Form = res.data
      this.add${tableName}Form.projectId = this.projectId
      this.add${tableName}DV = true
    },
    add${tableName}() {
      this.$refs.add${tableName}FormRef.validate(async valid => {
        if (!valid) return false
        const { data: res } = await this.$http.post('/api/${domainUrl}/add', this.add${tableName}Form)
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message({
          showClose: true,
          message: res.message,
          type: 'success',
          duration: 1500
        })
        this.add${tableName}DV = false
        this.get${tableName}List()
      })
    },
    edit${tableName}DVClose() {
      this.$refs.edit${tableName}FormRef.resetFields()
    },
    handleClose() {

    },
    async edit${tableName}DVShow(id) {
      this.edit${tableName}DV = true
      this.edit${tableName}Form.id = id
      const { data: res } = await this.$http.post('/api/${domainUrl}/getbykey', this.edit${tableName}Form)
      if (res.code !== 200) {
        this.$message.error(res.message)
      }
      this.edit${tableName}Form = res.data
    },
    edit${tableName}() {
      this.$refs.edit${tableName}FormRef.validate(async valid => {
        if (!valid) return false
        const { data: res } = await this.$http.post('/api/${domainUrl}/upbykey', this.edit${tableName}Form)
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.edit${tableName}DV = false
        this.get${tableName}List()
        this.$message.success(res.message)
      })
    },
    del${tableName}ById(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const { data: res } = await this.$http.post('/api/${domainUrl}/delbykey', { id })
        if (res.code !== 200) {
          return this.$message.error(res.message)
        }
        this.get${tableName}List()
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
