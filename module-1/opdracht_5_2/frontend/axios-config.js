import axios from "axios";

// axios.defaults.baseURL = "http://ecs-cluster-staging-alb-1837777129.us-east-1.elb.amazonaws.com";
axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.headers.post['Content-Type'] = 'application/json';
