import axios from "axios";

axios.defaults.baseURL = "http://ecs-cluster-staging-alb-1837777129.us-east-1.elb.amazonaws.com";
axios.defaults.headers.post['Content-Type'] = 'application/json';
