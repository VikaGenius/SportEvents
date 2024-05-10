<template>
    <form @submit.prevent="handleSubmit">
        <ErrorMessage v-if="error" :error="error"/>

        <h3>Sign Up</h3>

        <div class="form-group">
            <label>First Name</label>
            <input type="text" class="form-control" v-model="firstName" placeholder="First Name"/>
        </div>

        <div class="form-group">
            <label>Last Name</label>
            <input type="text" class="form-control" v-model="lastName" placeholder="Last Name"/>
        </div>

        <div class="form-group">
            <label>Phone Number</label>
            <input type="text" class="form-control" v-model="phoneNumber" placeholder="Phone Number"/>
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
            <input type="password" class="form-control" v-model="passwordConfirm" placeholder="Confirm Password"/>
        </div>

        <div class="d-grid gap-2">
            <button class="btn btn-primary btn-block" type="submit">Sign Up</button>
        </div>

    </form>
</template>

<script>
    import axios from 'axios';
    import ErrorMessage from '../ErrorMessage.vue'
    export default {
        name: 'RegisterForm',
        components: {
            ErrorMessage
        },
        data() {
            return {
                firstName: '',
                lastName: '',
                phoneNumber: '',
                email: '',
                password: '',
                passwordConfirm: '',
                error: ''
            }
        },
        methods: {
            async handleSubmit() {
                try {
                    const data = {
                        firstName: this.firstName,
                        lastName: this.lastName,
                        phoneNumber: this.phoneNumber,
                        email: this.email,
                        password: this.password,
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
