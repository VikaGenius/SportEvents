<template>
    <div class="modal" :class="{ 'show': showModal }">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- Заголовок модального окна -->
                <div class="modal-header">
                    <h5 class="modal-title">{{ mode === 'add' ? 'Add Event' : 'Edit Event' }}</h5>
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
                            <label for="coach" class="form-label">Coach</label>
                            <select class="form-select" id="coach" v-model="formData.coach">
                                <option v-for="coach in coaches" :value="coach.id" :key="coach.cId">{{ coach.fullName }}</option>
                            </select>
                        </div>
    
                        <div class="mb-3">
                            <label for="playground" class="form-label">Playground</label>
                            <select class="form-select" id="playground" v-model="formData.playground" @change="handlePlaygroundChange">
                                <option v-for="playground in playgrounds" :value="playground.id" :key="playground.pId">{{ playground.name }}</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="startTime" class="form-label">Start Time</label>
                            <input type="datetime-local" class="form-control" id="startTime" v-model="formData.startTime">
                        </div>

                        <div class="mb-3">
                            <label for="name" class="form-label">Duration</label>
                            <input type="text" class="form-control" id="duration" v-model="formData.duration">
                        </div>

                        <div class="mb-3">
                            <label for="name" class="form-label">Cost</label>
                            <input type="text" class="form-control" id="cost" v-model="formData.cost">
                        </div>
                        <div class="mb-3">
                            <label for="name" class="form-label">Max number of seats</label>
                            <input type="text" class="form-control" id="cost" v-model="formData.maxSeats">
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
        name: 'AddEditEventModal',
        props: ['showModal', 'mode', 'event'],
        data() {
            return {
                formData: { ...this.event },
                coaches: [],
                playgrounds: [],
                selectedPlayground: null
            };
        },
        watch: {
            event(newValue) {
                this.formData = { ...newValue };
            }
        },
        created() {
            this.fetchCoaches();
            this.fetchPlaygrounds();
        },
        methods: {
            closeModal() {
                this.$emit('close-modal');
            },
            async submitForm() {
                try {
                    this.formData.remainingSeats = this.formData.maxSeats;
                    if (this.mode === 'add') {
                        await axios.post('events', this.formData);
                    } else if (this.mode === 'edit') {
                        await axios.put(`events/${this.formData.eid}`, this.formData);
                    }
                    this.$emit('submit-success');
                } catch (error) {
                    console.error('Ошибка при отправке данных:', error);
                }
            },
            async fetchCoaches() {
                try {
                    const response = await axios.get('coaches');
                    this.coaches = response.data;
                } catch (error) {
                    console.error('Ошибка при получении тренеров:', error);
                }
            },
            async fetchPlaygrounds() {
                try {
                    const response = await axios.get('playgrounds');
                    this.playgrounds = response.data;
                } catch (error) {
                    console.error('Ошибка при получении площадок:', error);
                }
            },
            validateMaxSeats() {
                if (this.selectedPlayground && this.formData.maxSets > this.selectedPlayground.numberOfSeats) {
                    alert('The maximum number of seats exceeds the number of seats available at the selected playground.');
                }
            },
            handlePlaygroundChange() {
                this.selectedPlayground = this.formData.playground;
                this.validateMaxSeats();
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
