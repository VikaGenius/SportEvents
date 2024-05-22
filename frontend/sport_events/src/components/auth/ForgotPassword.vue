<template>
    <form @submit.prevent="handleSubmit">
        <div v-if="message" class="alert alert-succes" role="alert">
            {{ message }}
        </div>

        <ErrorMessage v-if="error" :error="error" />

        <h3>Forgot Password</h3>

        <div class="form-group">
            <label>Email</label>
            <input type="email" class="form-control" v-model="email" placeholder="Email" />
        </div>

        <div class="d-grid gap-2">
            <button class="btn btn-primary btn-block" type="submit">Submit</button>
        </div>

    </form>

</template>

<script>
    import axios from 'axios';
    import ErrorMessage from '../ErrorMessage.vue';

    export default {
        name: 'ForgotPassword',
        components: {
            ErrorMessage
        },
        data() {
            return {
                email: '',
                message: '',
                error: ''
            }
        },
        methods: {
            async handleSubmit() {
                try {
                    console.log(this.email)
                    await axios.post('forgot', { email: this.email });
                    this.message = 'The email was sent!';
                    this.error = '';
                } catch(e) {
                    this.error = 'Error occured!';
                    this.message = '';
                }
            }
        }
    }
</script>