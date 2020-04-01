import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

/**
 * 在主框架内显示
 */
const frameIn = [
  {
    path: '/',
    redirect: { name: 'index' },
    component: layoutHeaderAside,
    children: [
      // 首页
      {
        path: 'index',
        name: 'index',
        meta: {
          auth: true
        },
        component: _import('system/index')
      },
      {
        path: 'client-manage',
        name: 'client-manage',
        meta: {
          title: '客户管理',
          auth: true
        },
        component: _import('system/basic/client/client-manage')
      },
      {
        path: 'client-detail',
        name: 'client-detail',
        meta: {
          title: '查看进件的申请人',
          auth: true
        },
        component: _import('system/basic/client/client-detail')
      },
      {
        path: 'apply-manage',
        name: 'apply-manage',
        meta: {
          title: '进件管理',
          auth: true
        },
        component: _import('system/basic/apply/apply-manage')
      },
      {
        path: 'apply-detail',
        name: 'apply-detail',
        meta: {
          title: '查看客户的进件列表',
          auth: true
        },
        component: _import('system/basic/apply/apply-detail')
      },
      {
        path: 'number-manage',
        name: 'number-manage',
        meta: {
          title: '号码管理',
          auth: true
        },
        component: _import('system/basic/number/number-manage')
      },
      {
        path: 'number-detail',
        name: 'number-detail',
        meta: {
          title: '机主详情信息',
          auth: true
        },
        component: _import('system/basic/number/number-detail')
      },
      {
        path: 'emp-manage',
        name: 'emp-manage',
        meta: {
          title: '员工管理',
          auth: true
        },
        component: _import('system/sys-setting/employee/emp-manage')
      },
      {
        path: 'info-detection',
        name: 'info-detection',
        meta: {
          title: '信息检测',
          auth: true
        },
        component: _import('system/risk-control/info-detection/info-detection')
      },
      {
        path: 'detection-result',
        name: 'detection-result',
        meta: {
          title: '信息检测结果',
          auth: true
        },
        component: _import('system/risk-control/info-detection/detection-result')
      },
      {
        path: 'risk-predict',
        name: 'risk-predict',
        meta: {
          title: '风险预测',
          auth: true
        },
        component: _import('system/risk-control/risk-predict/risk-predict')
      },
      //  风险预测结果
      {
        path: 'predict-result',
        name: 'predict-result',
        meta: {
          title: '风险预测结果',
          auth: true
        },
        component: _import('system/risk-control/risk-predict/predict-result')
      },
      // 系统 前端日志
      {
        path: 'log',
        name: 'log',
        meta: {
          title: '前端日志',
          auth: true
        },
        component: _import('system/log')
      },
      // 刷新页面 必须保留
      {
        path: 'refresh',
        name: 'refresh',
        hidden: true,
        component: _import('system/function/refresh')
      },
      // 页面重定向 必须保留
      {
        path: 'redirect/:route*',
        name: 'redirect',
        hidden: true,
        component: _import('system/function/redirect')
      },
    ]
  }
]

/**
 * 在主框架之外显示
 */
const frameOut = [
  // 登录
  {
    path: '/login',
    name: 'login',
    component: _import('system/login')
  }
]

/**
 * 错误页面
 */
const errorPage = [
  {
    path: '*',
    name: '404',
    component: _import('system/error/404')
  }
]

// 导出需要显示菜单的
export const frameInRoutes = frameIn

// 重新组织后导出
export default [
  ...frameIn,
  ...frameOut,
  ...errorPage
]
