<template>
    <div>
        <h3>Список игровых площадок</h3>

        <ol class="list-group list-group-numbered">
            <li class="list-group-item d-flex justify-content-between align-items-start"
                v-for="playground in playgrounds" :key="playground.pId" >

                <div class="ms-2 me-auto">
                    <div class="fw-bold">{{ playground.name }}</div>
                    {{ playground.address }}
                </div>

                <div class="mt-2">
                    <button v-if="user && user.role==='ADMIN'" 
                        class="btn btn-sm btn-primary" 
                        @click="openEditModal(playground)"
                    >Edit</button>
                    <button v-if="user && user.role==='ADMIN'" 
                        class="btn btn-sm btn-danger me-2" 
                        @click="deletePlayground(playground.pId)"
                    >Delete</button>
                </div>

                <AddEditPlaygroundModal :show-modal="showEditModal" 
                    :playground="selectedPlayground"
                    mode="edit"
                    @close-modal="closeEditModal" 
                    @submit-success="handleSubmitSuccessEdit" 
                />

                <span class="badge bg-primary rounded-pill">
                    {{ playground.numberOfSeats }}
                </span>
                
            </li>
        </ol>

        <div class="d-grid gap-2">
            <button v-if="user && user.role==='ADMIN'" 
                class="btn btn-primary btn-block" 
                type="submit"
                @click="handleClick"
            >Add playground</button>
        </div>

        <AddEditPlaygroundModal 
            :show-modal="showModal" 
            mode="add"
            @close-modal="closeModal" 
            @submit-success="handleSubmitSuccess" 
        />

    </div>
</template>
  
<script>
    import axios from 'axios';
    import { mapGetters } from 'vuex';
    import AddEditPlaygroundModal from './AddEditPlaygroundModal.vue';

    export default {
        name: 'PlaygroundsList',
        components: {
            AddEditPlaygroundModal
        },

        data() {
            return {
                playgrounds: [],
                showModal: false,
                newPlayground: {
                    name: '',
                    address: '',
                    numberOfSeats: 0
                },
                showEditModal: false,
                selectedPlayground: null,
            };
        },

        computed: {
            ...mapGetters(['user'])
        },

        created() {
            this.fetchPlaygrounds();
            console.log(this.user)
        },

        methods: {
            async fetchPlaygrounds() {
                try {
                    const response = await axios.get('playgrounds');
                    this.playgrounds = response.data;
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
                this.fetchPlaygrounds();
                this.closeModal();
            },

            editPlayground(id) {
                // Реализуйте функциональность редактирования площадки
                console.log('Edit playground:', id);
            },

            async deletePlayground(id) {
                // Реализуйте функциональность удаления площадки
                try {
                    await axios.delete(`playgrounds/${id}`);
                    this.fetchPlaygrounds();
                } catch (error) {
                    console.error('Ошибка при удалении данных:', error);
                }
            },

            handleSubmitSuccessEdit() {
                this.fetchPlaygrounds();
                this.closeEditModal();
            },

            openEditModal(playground) {
                this.selectedPlayground = playground;
                this.showEditModal = true;
            },
            closeEditModal() {
                this.selectedPlayground = null; 
                this.showEditModal = false; // Сбросить выбранную площадку при закрытии модального окна
            }

        }
    };

</script>

<style>
    .list-group.list-group-numbered {
        margin-bottom: 10px;
    }
</style>