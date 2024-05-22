<template>
    <form @submit.prevent="handleSubmit">
        <h3>Reset Password</h3>

        <div class="form-group">
            <label>Password</label>
            <input type="password" class="form-control" v-model="password" placeholder="Password"/>
        </div>

        <div class="form-group">
            <label>Confirm Password</label>
            <input type="password" class="form-control" v-model="password_confirm" placeholder="Confirm Password"/>
        </div>

        <div class="d-grid gap-2">
            <button class="btn btn-primary btn-block" type="submit">Submit</button>
        </div>

    </form>

</template>

<script>
    import axios from 'axios';
    export default {
        name: 'ResetPassword',
        data() {
            return {
                password: '',
                password_confirm: '',
                token: this.$route.params.token,
            }
        },
        methods: {
            async handleSubmit() {
                if (this.password !== this.password_confirm) {
                    this.error = "Passwords do not match!";
                    return;
                }

                try {
                    const response = await axios.post('reset', {
                        newPassword: this.password,
                        token: this.token
                    });

                    console.log(response);
                    this.$router.push('/login');
                } catch (e) {
                    this.error = e.response.data || 'Error occurred!';
                }
            }

        }
    }
</script>