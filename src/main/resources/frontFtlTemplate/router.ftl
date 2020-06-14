import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/Home'
import Welcome from '../views/home/Welcome'
<#list domainInfoList as keys, domainInfo>
import ${domainInfo} from '../views/home/${keys}/${domainInfo}'
</#list>

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
    redirect: '/welcome',
    children: [
      { path: '/welcome', component: Welcome },
      <#list domainInfoList as keys, domainInfo>
      { path: '/${domainInfo}', component: ${domainInfo} }<#if keys_has_next>,</#if>
      </#list>
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
