import axios from "axios";

const dev = import.meta.env.DEV;
if (dev) axios.defaults.baseURL = "http://localhost:8080";

axios.defaults.headers.post['Content-Type'] = 'application/json';
