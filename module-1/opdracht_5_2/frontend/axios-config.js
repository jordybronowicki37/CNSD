import axios from "axios";

const prod = import.meta.env.PROD;
if (prod) axios.defaults.baseURL = "http://ecs-cluster-staging-alb-1837777129.us-east-1.elb.amazonaws.com";
else axios.defaults.baseURL = "http://localhost:8080";

axios.defaults.headers.post['Content-Type'] = 'application/json';
