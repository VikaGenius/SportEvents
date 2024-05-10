<template>
    <form @submit.prevent="handleSubmit">
        <ErrorMessage v-if="error" :error="error"/>
        
        <h3>Login</h3>

        <div class="form-group">
            <label>Email</label>
            <input type="email" class="form-control" v-model="email" placeholder="Email"/>
        </div>

        <div class="form-group">
            <label>Password</label>
            <input type="password" class="form-control" v-model="password" placeholder="Password"/>
        </div>

        <div class="d-grid gap-2">
            <button class="btn btn-primary btn-block" type="submit">Login</button>
        </div>

        <p class="forgot-password text-right">
            <router-link to="forgot">Forgot password?</router-link>
        </p>

    </form>

</template>

<script>
    import axios from 'axios';
    import ErrorMessage from '../ErrorMessage.vue'
    export default {
        name: 'LoginForm',
        components: {
            ErrorMessage
        },
        data() {
            return {
                email: '',
                password: '',
                error: '',
                firstName: '',
                lastName: '',
                role: ''
            }
        },
        methods: {
            async handleSubmit() {
                try {
                    const response = await axios.post('login', {
                        email: this.email,
                        password: this.password
                    });

                    localStorage.setItem('token', response.data.token);
                    localStorage.setItem('user', JSON.stringify(response.data.user));
                    this.$store.dispatch('user', response.data.user);
                    this.$router.push('/');
                } catch (e) {
                    this.error = 'Invalid username/password!'
                }

            }

        }
    }
</script>

