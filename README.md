# graduationProject
d2-admin ：前端页面展示
### 启动d2-admin
##### 安装依赖
```java
npm i
```

##### 开发调试

```java
npm run dev
```

##### 前后端分离开发
配置vue.config.js文件
```java
proxy: {
    '/api': {
      target: 'http://47.100.186.132/your-path/api',
      ws: true,
      changeOrigin: true,
      pathRewrite: {
        '^/api': ''
      }
    }
  }
```
> 请求 /api/login 时转发到 http://47.100.186.132/your-path/api/login