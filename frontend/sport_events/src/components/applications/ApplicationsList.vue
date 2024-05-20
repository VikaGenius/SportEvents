<template>
    <div>
    <h3>Неподтверждённые заявки</h3>
    <ul class="list-group">
        <li v-for="application in applications" :key="application.ueid" class="list-group-item d-flex justify-content-between align-items-center">
        {{ application.user.firstName }} {{ application.user.lastName }} хочет записаться на мероприятие "{{ application.event.name }}"
        <span>
            <button class="btn btn-success" @click="confirmApplication(application.ueId)">Подтвердить</button>
            <button class="btn btn-danger" @click="rejectApplication(application.ueId)">Отклонить</button>
        </span>
        </li>
    </ul>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'ApplicationsList',
        data() {
            return {
                applications: []
            };
        },
        created() {
            this.fetchUnconfirmedApplications();
        },
        methods: {
            async fetchUnconfirmedApplications() {
                try {
                    const response = await axios.get('/registrations/unconfirmed');
                    console.log(response.data)
                    this.applications = response.data;
                } catch (error) {
                    console.error('Ошибка при загрузке данных:', error);
                }
            },
            async confirmApplication(registrationId) {
                try {
                    await axios.put(`/registrations/confirm/${registrationId}`);
                    this.fetchUnconfirmedApplications(); // Обновить список после подтверждения
                } catch (error) {
                    console.error('Ошибка при подтверждении заявки:', error);
                }
            },
            async rejectApplication(registrationId) {
                try {
                    await axios.delete(`/registrations/${registrationId}`);
                    this.fetchUnconfirmedApplications(); // Обновить список после отклонения
                } catch (error) {
                    console.error('Ошибка при отклонении заявки:', error);
                }
            }
        }
    };
</script>

<style>
    .list-group-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px; /* добавить внутренние отступы */
        margin-bottom: 5px; /* добавить отступы между элементами списка */
        border-radius: 5px; /* слегка закруглить углы */
        background-color: #f8f9fa; /* задать фоновый цвет */
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* добавить тень для элементов списка */
    }

</style>
