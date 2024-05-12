import { createRouter, createWebHistory } from 'vue-router'
import HomeSpace from './components/HomeSpace.vue'
import LoginForm from './components/auth/LoginForm.vue'
import RegisterForm from './components/auth/RegisterForm.vue'
import ForgotPassword from './components/auth/ForgotPassword.vue'
import ResetPassword from './components/auth/ResetPassword.vue'
import Playgrounds from './components/playgrounds/PlaygroundsList.vue'
import Coaches from './components/coaches/CoachesList.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: HomeSpace },
        { path: '/login', component: LoginForm },
        { path: '/register', component: RegisterForm },
        { path: '/forgot', component: ForgotPassword },
        { path: '/reset/:token', component: ResetPassword },
        { path: '/playgrounds', component: Playgrounds },
        { path: '/coaches', component: Coaches }

    ]
})

export default router