// 菜单 侧边栏
export default [
  { path: '/index', title: '首页', icon: 'home' },
  {
    title: '基础信息',
    icon: 'user',
    children: [
      { path: '/client-manage', title: '客户管理', icon: '' },
      { path: '/apply-manage', title: '进件管理', icon: '' },
      { path: '/number-manage', title: '号码管理', icon: '' }
    ]
  },
  {
    title: '贷前风控',
    icon: 'wpexplorer',
    children: [
      { path: '/info-detection', title: '信息检测', icon: '' },
      { path: '/page2', title: '页面 2', icon: '' },
      { path: '/page3', title: '页面 3', icon: '' }
    ]
  },
  {
    title: '系统设置',
    icon: 'windows',
    children: [
      { path: '/emp-manage', title: '员工管理', icon: '' },
      { path: '/page2', title: '页面 2', icon: '' },
      { path: '/page3', title: '页面 3', icon: '' }
    ]
  }
]
