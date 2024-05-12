<template>
    <div>
        <h3>Список тренеров</h3>

        <ol class="list-group list-group-numbered">
            <li class="list-group-item d-flex justify-content-between align-items-start"
                v-for="coach in coaches" :key="coach.cId" >

                <div class="ms-2 me-auto">
                    <div class="fw-bold">{{ coach.fullName }}</div>
                </div>

                <div class="mt-2">
                    <button v-if="user && user.role==='ADMIN'" 
                        class="btn btn-sm btn-danger me-2" 
                        @click="deleteCoach(coach.cId)"
                    >Delete</button>
                </div>
            </li>
        </ol>

        <div class="d-grid gap-2">
            <button v-if="user && user.role==='ADMIN'" 
                class="btn btn-primary btn-block" 
                type="submit"
                @click="handleClick"
            >Add coach</button>
        </div>

        <AddCoachModal 
            :show-modal="showModal" 
            @close-modal="closeModal" 
            @submit-success="handleSubmitSuccess" 
        />

    </div>
</template>
  
<script>
    import axios from 'axios';
    import { mapGetters } from 'vuex';
    import AddCoachModal from './AddCoachModal.vue';

    export default {
        name: 'CoachesList',
        components: {
            AddCoachModal
        },

        data() {
            return {
                coaches: [],
                showModal: false,
                newCoach: {
                    fullName: '',
                },
            };
        },

        computed: {
            ...mapGetters(['user'])
        },

        created() {
            this.fetchCoaches();
            console.log(this.user)
        },

        methods: {
            async fetchCoaches() {
                try {
                    const response = await axios.get('coaches');
                    this.coaches = response.data;
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
                this.fetchCoaches();
                this.closeModal();
            },

            async deleteCoach(id) {
                try {
                    await axios.delete(`coaches/${id}`);
                    this.fetchCoaches();
                } catch (error) {
                    console.error('Ошибка при удалении данных:', error);
                }
            },
        }
    };

</script>

<style>
    .list-group.list-group-numbered {
        margin-bottom: 10px;
    }
</style>