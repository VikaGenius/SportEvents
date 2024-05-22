<template>
    <div v-if="user && user.role==='ADMIN'">
        <h1>SQL Query Tool</h1>
        <div class="form-group">
            <label for="sqlQuery">Enter SQL Query:</label>
            <textarea id="sqlQuery" v-model="sqlQuery" class="form-control"></textarea>
        </div>
        <button @click="executeQuery" class="btn btn-primary">Execute Query</button>

        <div v-if="results">
            <h2>Results:</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th v-for="column in columns" :key="column">{{ column }}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(row, index) in results" :key="index">
                        <td v-for="column in columns" :key="column">{{ row[column] }}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div v-if="error" class="alert alert-danger">
            {{ error }}
        </div>
    </div>
</template>
  
<script>
    import axios from 'axios';
    import { mapGetters } from 'vuex';
    export default {
        name: 'QueryWindow',
        data() {
            return {
                sqlQuery: '',
                results: null,
                columns: [],
                error: null
            };
        },
        computed: {
            ...mapGetters(['user'])
        },
        methods: {
            async executeQuery() {
                this.error = null;
                this.results = null;
                try {
                    const response = await axios.post('/sql-query', { query: this.sqlQuery });
                    this.results = response.data.results;
                    if (this.results.length > 0) {
                        this.columns = Object.keys(this.results[0]);
                    } else {
                        this.error = 'No data found.';
                    }
                } catch (err) {
                    this.error = 'Failed to execute query: ' + (err.response?.data?.message || err.message);
                }
            }
        }
    }
</script>
  
<style>
    textarea {
        min-height: 100px;
    }
</style>
  