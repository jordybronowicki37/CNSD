import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default ({mode}) => {
  let target = "http://localhost:8080";
  if (mode === "production") {
    // Aws alb uri
    target = "http://ecs-cluster-staging-alb-1837777129.us-east-1.elb.amazonaws.com:8080"
  }

  const config = {
    plugins: [react()],
    server: {
      proxy: {
        '/api': {
          target,
          changeOrigin: true,
        },
      },
    },
  }
  return defineConfig(config);
}
