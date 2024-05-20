<template>
    <div id="app">
        <NavBar :user="user" />

        <div class="wrapper">
            <div class="inner">
                <router-view />
            </div>
        </div>
    </div>
</template>

<script>
    import NavBar from './components/NavBar.vue'
    //import axios from 'axios';

    export default {
        
        name: 'App',
        components: {
            NavBar
        },
        async created() {
            const user = JSON.parse(localStorage.getItem('user'));
            if (user) {
                this.$store.dispatch('user', user);
            }
            // const response = await axios.get('user');
            // this.$store.dispatch('user', response.data);
        }
    }
    
</script>

<style>
    * {
        box-sizing: border-box;
    }

    body {
        /* background: #1C8EF9 !important; */
        background-image: url('./assets/фон3.png');
        
        min-height: 100vh;
        display: flex;
        font-weight: 400;
    }

    h1, h2, h3, h4, h5, h6, label, span {
        font-weight: 500;
    }

    body, html, #app, #root, .auth-wrapper {
        width: 100%;
        height: 100%;
    }

    #app {
        text-align: center;
    }

    .navbar-light {
        background-color: #ffffff;
        box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.2);
    }

    .wrapper {
        display: flex;
        justify-content: center;
        flex-direction: column;
        text-align: left;
        margin-top: 50px;
        /* background-image: url('./assets/фон.png'); */
    }

    .inner {
        width: 600px;
        margin: auto;
        background: #ffffff;
        box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.2);
        padding: 40px 55px 45px 55px;
        border-radius: 15px;
        transition: all .3s;
    }

    .wrapper .form-control:focus {
        border-color: #167bff;
        box-shadow: none;
    }

    .wrapper h3 {
        text-align: center;
        margin: 0;
        line-height: 1;
        padding-bottom: 20px;
    }

    .custom-control-label {
        font-weight: 400;
    }

    .forgot-password,
    .forgot-password a {
        text-align: right;
        font-size: 13px;
        padding-top: 10px;
        color: #7f7d7d;
        margin: 0;
    }
    
    .forgot-password a {
        color: #167bff;
    }

    .form-group {
        margin-bottom: 20px; 
    }

</style>