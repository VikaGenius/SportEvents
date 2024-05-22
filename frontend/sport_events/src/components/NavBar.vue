<template>
    <nav class="navbar navbar-expand navbar-light fixed-top">
            <div class="container">
                <router-link to="/" class="navbar-brand">Home</router-link>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav ml-auto" v-if="!user">
                        <li class="nav-item">
                            <router-link to="login" class="nav-link">Login</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="register" class="nav-link">Sign up</router-link>
                        </li>
                    </ul>
                    <ul class="navbar-nav ml-auto" v-if="user">
                        <li class="nav-item">
                            <a href="javascript:void(0)" @click="handleClick" class="nav-link">Logout</a>
                        </li>
                        <li class="nav-item">
                            <router-link to="playgrounds" class="nav-link">Playgrounds</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="coaches" class="nav-link">Coaches</router-link>
                        </li>
                    </ul>
                    <ul class="navbar-nav ml-auto" v-if="user && user.role==='ADMIN'">
                        <li class="nav-item">
                            <router-link to="applications" class="nav-link">Applications to events</router-link>
                        </li>
                    </ul>
                    <ul class="navbar-nav ml-auto" v-if="user && user.role==='ADMIN'">
                        <li class="nav-item">
                            <router-link to="queries" class="nav-link">Queries</router-link>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>
</template>

<script>
    import {mapGetters} from 'vuex';
    export default {
        name: 'NavBar',
        methods: {
            handleClick() {
                localStorage.removeItem('token');
                this.$store.dispatch('user', null);
                localStorage.removeItem('user');
                this.$router.push('/');
            }
        },
        computed: {
            ...mapGetters(['user'])
        }
    }
</script>


