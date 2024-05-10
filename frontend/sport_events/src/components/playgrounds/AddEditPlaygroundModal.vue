<template>
    <div class="modal" :class="{ 'show': showModal }">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- Заголовок модального окна -->
                <div class="modal-header">
                    <h5 class="modal-title">{{ mode === 'add' ? 'Add Playground' : 'Edit Playground' }}</h5>
                    <button type="button" class="btn-close" @click="closeModal"></button>
                </div>
                <!-- Тело модального окна с формой для добавления/изменения данных -->
                <div class="modal-body">
                    <form @submit.prevent="submitForm">
                        <!-- Форма для добавления/изменения данных игровой площадки -->
                        <div class="mb-3">
                            <label for="name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="name" v-model="formData.name">
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" v-model="formData.address">
                        </div>
                        <div class="mb-3">
                            <label for="seats" class="form-label">Number of Seats</label>
                            <input type="number" class="form-control" id="seats" v-model="formData.numberOfSeats">
                        </div>
                        <button type="submit" class="btn btn-primary">{{ mode === 'add' ? 'Add' : 'Save Changes' }}</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'AddEditPlaygroundModal',
        props: ['showModal', 'mode', 'playground'],
        data() {
            return {
                formData: { ...this.playground }
            };
        },
        watch: {
            playground(newValue) {
                this.formData = { ...newValue };
            }
        },
        methods: {
            closeModal() {
                this.$emit('close-modal');
            },
            async submitForm() {
                try {
                    if (this.mode === 'add') {
                        await axios.post('playgrounds', this.formData);
                    } else if (this.mode === 'edit') {
                        await axios.put(`playgrounds/${this.formData.id}`, this.formData);
                    }
                    this.$emit('submit-success');
                } catch (error) {
                    console.error('Ошибка при отправке данных:', error);
                }
            }
        }
    };
</script>

<style>
    .modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 1000;
        margin-top: 10px;
    }

    .modal.show {
        display: block;
    }
</style>
