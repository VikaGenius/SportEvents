import axios from 'axios';

axios.defaults.baseURL = 'http://localhost8000/';
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');