import { createRouter, createWebHistory } from 'vue-router'
import HomeSpace from './components/HomeSpace.vue'
import LoginForm from './components/auth/LoginForm.vue'
import RegisterForm from './components/auth/RegisterForm.vue'
import ForgotPassword from './components/auth/ForgotPassword.vue'
import ResetPassword from './components/auth/ResetPassword.vue'
import PlaygroundsList from './components/playgrounds/PlaygroundsList.vue'
import CoachesList from './components/coaches/CoachesList.vue'
import EventsList from './components/events/EventList.vue'
import ApplicationsList from './components/applications/ApplicationsList.vue'
import EventDetails from './components/events/EventDetails.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: HomeSpace },
        { path: '/login', component: LoginForm },
        { path: '/register', component: RegisterForm },
        { path: '/forgot', component: ForgotPassword },
        { path: '/reset/:token', component: ResetPassword },
        { path: '/playgrounds', component: PlaygroundsList },
        { path: '/coaches', component: CoachesList },
        { path: '/events', component: EventsList },
        { path: '/applications', component: ApplicationsList},
        { path: '/events/:id', component: EventDetails }

    ]
})

export default router