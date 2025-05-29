import { createApp } from 'vue' // 引入 Vue 的 createApp 函数，用于创建一个 Vue 应用实例。
import App from './App.vue'  // 引入 App.vue 组件，这是 Vue 应用的根组件。
import router from './router' // 引入路由配置，用于配置路由规则。
import ElementPlus from 'element-plus' //  引入 Element Plus，用于构建前端 UI 组件。
import 'element-plus/dist/index.css' //  引入 Element Plus 的样式文件，用于 渲染组件。

// 创建 Vue 应用实例
const app = createApp(App)

// 使用路由插件
app.use(router)

// 使用 ElementPlus UI 库
app.use(ElementPlus)

// 将应用挂载到指定的 DOM 元素上
app.mount('#app')