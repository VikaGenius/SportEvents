import { createRouter, createWebHistory } from 'vue-router'
import HomeSpace from './components/HomeSpace.vue'
import LoginForm from './components/LoginForm.vue'
import RegisterForm from './components/RegisterForm.vue'
import ForgotPassword from './components/ForgotPassword'
import ResetPassword from './components/ResetPassword'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: HomeSpace },
        { path: '/login', component: LoginForm },
        { path: '/register', component: RegisterForm },
        { path: '/forgot', component: ForgotPassword },
        { path: '/reset/:token', component: ResetPassword }

    ]
})

export default router