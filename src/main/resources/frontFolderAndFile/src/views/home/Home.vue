<template>
  <el-container class="home-container">
    <el-aside width="200px">
      <div>
        **系统
      </div>
      <div :style="contentStyleObj">
        <el-scrollbar style="height:100%" wrap-class="scrollbar-wrapper">
          <el-menu background-color="#545c64" text-color="#fff" active-text-color="#3CA7FF" unique-opened router :default-active="activePath">
            <el-menu-item :index="item.url" v-for="item in menuList" :key="item.id" @click="saveActivePath(item.url)">
              <i class="el-icon-menu"></i>
              <span>{{item.showNameCn}}</span>
            </el-menu-item>
          </el-menu>
        </el-scrollbar>
      </div>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          一些操作
        </div>
        <el-button type='info' @click="logout">退出</el-button>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer height='30px'>审</el-footer>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'home',
  created() {
    this.getMenuList()
    this.activePath = window.sessionStorage.getItem('pathStr')
    window.addEventListener('resize', this.getHeight)
    this.getHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.getHeight)
  },
  data() {
    return {
      activePath: '',
      isCollapse: true,
      menuList: {},
      contentStyleObj: {
        height: ''
      }
    }
  },
  methods: {
    logout() {
      window.sessionStorage.removeItem('token')
      this.$router.push('/login')
    },
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    async getMenuList() {
      debugger
      const { data: res } = await this.$http.post('/api/funResource/getall', {})
      if (res.code !== 200) return this.$message.error(res.message)
      this.menuList = res.data
    },
    saveActivePath(pathStr) {
      window.sessionStorage.setItem('pathStr', pathStr)
      this.activePath = pathStr
    },
    getHeight() {
      // 获取浏览器高度，减去顶部导航栏的值，70该值也可以动态获取
      this.contentStyleObj.height = window.innerHeight - 60 + 'px'
      // this.contentStyleObj.width = window.innerWidth - 250 + 'px'
    }
  }
}
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #b3c0d1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #333;
  > div {
    display: flex;
    align-items: center;
  }
}

.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  height: 30px;
  line-height: 30px;
  font-size: 12px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  height: 100%;
  min-height: 590px;
  .el-menu {
    border-right: none;
  }
  > div {
    height: 60px;
    line-height: 60px;
    text-align: center;
  }
}
/deep/.el-scrollbar__wrap{
  overflow-x: hidden !important;
}
.el-main {
  background-color: #e9eef3;
  color: #333;
  min-height: 500px;
}
body > .el-container {
  height: 100%;
}
</style>
