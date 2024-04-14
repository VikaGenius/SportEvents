<template>
    <form @submit.prevent="handleSubmit">
        <ErrorMessage v-if="error" :error="error"/>

        <h3>Sign Up</h3>

        <div class="form-group">
            <label>First Name</label>
            <input type="text" class="form-control" v-model="first_name" placeholder="First Name"/>
        </div>

        <div class="form-group">
            <label>Last Name</label>
            <input type="text" class="form-control" v-model="last_name" placeholder="Last Name"/>
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" class="form-control" v-model="email" placeholder="Email"/>
        </div>

        <div class="form-group">
            <label>Password</label>
            <input type="password" class="form-control" v-model="password" placeholder="Password"/>
        </div>

        <div class="form-group">
            <label>Confirm Password</label>
            <input type="password" class="form-control" v-model="password_confirm" placeholder="Confirm Password"/>
        </div>

        <div class="d-grid gap-2">
            <button class="btn btn-primary btn-block" type="submit">Sign Up</button>
        </div>

    </form>
</template>

<script>
    import axios from 'axios';
    import ErrorMessage from './ErrorMessage.vue'
    export default {
        name: 'RegisterForm',
        components: {
            ErrorMessage
        },
        data() {
            return {
                first_name: '',
                last_name: '',
                email: '',
                password: '',
                password_confirm: '',
                error: ''
            }
        },
        methods: {
            async handleSubmit() {
                try {
                    const data = {
                        first_name: this.first_name,
                        last_name: this.last_name,
                        email: this.email,
                        password: this.password,
                        password_confirm: this.password_confirm
                    };
                    
                    const response = await axios.post('register', data);

                    console.log(response);
                    this.$router.push('/login');
                } catch (e) {
                    this.error = 'Error occured!';
                }
            }
        }
    }

</script>
