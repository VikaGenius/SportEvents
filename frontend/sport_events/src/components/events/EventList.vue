<template>
    <h3>List of events</h3>

    <div class="d-grid gap-2">
        <button v-if="user && user.role==='ADMIN'" 
            class="btn btn-primary btn-block" 
            type="submit"
            mode="add"
            @click="handleClick"
        >Add event</button>
    </div>

    <div class="card w-100 mb-3" v-for="event in events" :key="event.eId" >
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <h5 class="card-title">{{ event.name }}</h5>
                <span class="badge bg-primary rounded-pill">{{ event.remainingSeats }} seats</span>
            </div>

            <p class="card-text">Coach: {{ event.coach.fullName }}</p>
            <p class="card-text">Address: {{ event.playground.address }}</p>
            <p class="card-text">Time: {{ formatDate(event.startTime) }}</p>
            <p class="card-text">Duration: {{ event.duration }}</p>

            <router-link :to="'/events/' + event.eid" >Details</router-link>

            <button 
                v-if="user" 
                :class="{'btn btn-primary btn-block w-100': true, 'disabled': registeredEvents[event.eId]}"
                :disabled="registeredEvents[event.eid]"
                @click="registerToEvent(event.eid)"
            >
                {{ registeredEvents[event.eid] ? 'Have you joined!' : 'Join to event' }}
            </button>

            <div v-if="showConfirmationModal" class="modal" style="display:block">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Подтверждение</h5>
                            <button type="button" class="close" @click="closeConfirmationModal">×</button>
                        </div>
                        <div class="modal-body">
                            <p>Ваша заявка принята к рассмотрению, ожидайте подтверждения на почту.</p>
                        </div>
                    </div>
                </div>
            </div> 

            <div class="mt-2">
                <button v-if="user && user.role==='ADMIN'" 
                    class="btn btn-sm btn-primary w-100" 
                    @click="openEditModal(event)"
                >Edit</button>
                <button v-if="user && user.role==='ADMIN'" 
                    class="btn btn-sm btn-danger me-2 w-100" 
                    @click="deleteEvent(event.eid)"
                >Delete</button>
            </div>

            <AddEditEventModal :show-modal="showEditModal" 
                :event="selectedEvent"
                mode="edit"
                @close-modal="closeEditModal" 
                @submit-success="handleSubmitSuccessEdit" 
            />
        </div>
    </div>

    <AddEditEventModal 
            :show-modal="showModal" 
            mode="add"
            @close-modal="closeModal" 
            @submit-success="handleSubmitSuccess" 
    />

</template>

<script>
    import axios from 'axios';
    import { mapGetters } from 'vuex';
    import AddEditEventModal from './AddEditEventModal.vue';

    export default {
        name: 'EventsList',
        components: {
            AddEditEventModal
        },

        data() {
            return {
                events: [],
                registeredEvents: {},
                showModal: false,
                newEvent: {
                    name: '',
                    coach: 0,
                    playground: 0,
                    startTime: null,
                    duration: '',
                    remainingSeats: 0,
                    cost: 0,
                    maxSeats: 0,
                },
                showEditModal: false,
                selectedEvent: null,
                showConfirmationModal: false,
            };
        },

        computed: {
            ...mapGetters(['user'])
        },

        created() {
            this.fetchEvents();
        },

        methods: {
            async fetchEvents() {
                try {
                    const [eventsResponse, registrationResponse] = await Promise.all([
                        axios.get('events'),
                        axios.get(`registrations/user/${this.user.id}/events`)
                    ]);
                    this.events = eventsResponse.data;
                    this.registeredEvents = {};
                    registrationResponse.data.forEach(event => {
                        this.registeredEvents[event.eid] = true;
                    });
                } catch (error) {
                    console.error('Ошибка при получении данных:', error);
                }
            },

            handleClick() {
                this.showModal = true;
            },

            closeModal() {
                this.showModal = false;
            },

            handleSubmitSuccess() {
                this.fetchEvents();
                this.closeModal();
            },

            async deleteEvent(id) {
                try {
                    await axios.delete(`events/${id}`);
                    this.fetchEvents();
                } catch (error) {
                    console.error('Ошибка при удалении данных:', error);
                }
            },

            handleSubmitSuccessEdit() {
                this.fetchEvents();
                this.closeEditModal();
            },

            openEditModal(event) {
                this.selectedEvent = event;
                this.showEditModal = true;
            },

            closeEditModal() {
                this.selectedEvent = null; 
                this.showEditModal = false; // Сбросить выбранную площадку при закрытии модального окна
            },

            formatDate(timestamp) {
                const date = new Date(timestamp);
                const options = { day: 'numeric', month: 'long', hour: '2-digit', minute: '2-digit' };
                return date.toLocaleDateString('ru-RU', options);
            },
            async registerToEvent(eventId) {
                try {
                    const registrationDTO = { user: this.user.id, event: eventId };
                    console.log(registrationDTO)
                    await axios.post('registrations', registrationDTO);
                    this.showConfirmationModal = true; // Открыть модальное окно подтверждения
                    this.registeredEvents[eventId] = true;
                } catch (error) {
                    console.error('Ошибка при регистрации на мероприятие:', error);
                }
            },
            closeConfirmationModal() {
                this.showConfirmationModal = false;
            },


        }
    };
</script>

<style>
    .d-grid.gap-2 {
        margin-top: 10px;
        margin-bottom: 10px;
    }
    .btn.btn-sm.btn-primary {
        margin-bottom: 5px;
    }
    .custom-card {
        width: 90%;
        margin: 0 auto;
    }
    .d-flex {
        display: flex;
    }
    .justify-content-between {
        justify-content: space-between;
    }
    .align-items-center {
        align-items: center;
    }

    .modal {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1050;
    width: 100%;
    height: 100%;
    overflow: hidden;
    outline: 0;
    background-color: rgba(0,0,0,0.5);
}
.modal-dialog {
    position: relative;
    margin: 10% auto;
    max-width: 500px;
}

.modal-content {
    position: relative;
    display: flex;
    flex-direction: column;
    width: 100%;
    pointer-events: auto;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid rgba(0,0,0,.2);
    border-radius: 0.3rem;
    outline: 0;
}
</style>