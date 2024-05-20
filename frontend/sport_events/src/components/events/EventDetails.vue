<template>
    <div v-if="event">
        <h2>
            {{ event.name }}
        </h2>
        <p>Coach: {{ event.coach.fullName }}</p>
        <p>Address: {{ event.playground.address }}</p>
        <p>Time: {{ formatDate(event.startTime) }}</p>
        <p>Duration: {{ event.duration }}</p>
        <p>Cost: {{ event.cost }}</p>
        <p>Remaining seats: {{ event.remainingSeats }}</p>
        <p>Max count of seats: {{ event.maxSets }}</p>

        <div v-if="user && user.role==='ADMIN'">
            <h3>Зарегистрированные пользователи:</h3>
            <ul v-if="users.length">
                <li v-for="user in users" :key="user.id">
                    {{ user.firstName }} {{ user.lastName }} ({{ user.email }})
                    <button @click="deleteRegistration(user.id)" class="btn btn-danger btn-sm">Delete</button>
                </li>
            </ul>
            <p v-else>На мероприятие еще никто не зарегистрирован.</p>
        </div>
        

    </div>
</template>
  
<script>
    import axios from 'axios';
    import { mapGetters } from 'vuex';
    
    export default {
        data() {
            return {
                event: null,
                users: [],
            };
        },
        mounted() {
            this.fetchEventDetails();
            this.fetchEventUsers();
        },
        computed: {
            ...mapGetters(['user'])
        },
        methods: {
            async fetchEventDetails() {
                const eventId = this.$route.params.id;
                try {
                    const response = await axios.get(`/events/${eventId}`);
                    this.event = response.data;
                } catch (error) {
                    console.error('Ошибка при загрузке данных мероприятия:', error);
                }
            },
            formatDate(timestamp) {
                const date = new Date(timestamp);
                const options = { day: 'numeric', month: 'long', hour: '2-digit', minute: '2-digit' };
                return date.toLocaleDateString('ru-RU', options);
            },
            async fetchEventUsers() {
                const eventId = this.$route.params.id;
                try {
                    const response = await axios.get(`registrations/event/${eventId}/users`);
                    this.users = response.data;
                } catch (error) {
                    if (error.response && error.response.status === 204) {
                        this.users = []; // Список пуст
                    } else {
                        console.error('Ошибка при загрузке списка пользователей:', error);
                    }
                }
            },
            async deleteRegistration(userId) {
                const eventId = this.$route.params.id;
                try {
                    await axios.delete(`registrations/${eventId}/${userId}`);
                    this.fetchEventUsers(); // Обновить список после удаления пользователя
                } catch (error) {
                    console.error('Ошибка при удалении регистрации:', error);
                }
            },
        }
    };
</script>
  