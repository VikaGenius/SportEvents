import { createApp } from 'vue'
import App from './App.vue'
import Router from './router'
import './axios'
import store from './vuex'

const app = createApp(App);
app.use(Router);
app.use(store);
app.mount('#app');
