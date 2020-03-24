// 菜单 侧边栏
export default [
  { path: '/index', title: '首页', icon: 'home' },
  {
    title: '基础信息',
    icon: 'user',
    children: [
      { path: '/client-manage', title: '客户管理' },
      { path: '/apply-manage', title: '进件管理' },
      { path: '/number-manage', title: '号码管理' }
    ]
  },
  {
    title: '贷前风控',
    icon: 'wpexplorer',
    children: [
      { path: '/page1', title: '页面 1' },
      { path: '/page2', title: '页面 2' },
      { path: '/page3', title: '页面 3' }
    ]
  },
  {
    title: '系统设置',
    icon: 'windows',
    children: [
      { path: '/emp-manage', title: '员工管理' },
      { path: '/page2', title: '页面 2' },
      { path: '/page3', title: '页面 3' }
    ]
  }
]
