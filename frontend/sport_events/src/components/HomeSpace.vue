<template>
    <div>
        <h3 v-if="user">Hi, {{ user.firstName }} {{ user.lastName }} </h3>
        <h3 v-if="!user">You are not logged in! </h3>
        <EventList />
    </div>
</template>

<script>
    import { mapGetters } from 'vuex';
    import EventList from './events/EventList.vue'
    export default {
        name: 'HomeSpace',
        computed: {
            ...mapGetters(['user'])
        },
        components: {
            EventList,
        },
    }

</script>