import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default () => {
  const config = {
    plugins: [react()],
    server: {
      proxy: {
        '/api': {
          target: "http://localhost:8080",
          changeOrigin: true,
        },
      },
    },
  }
  return defineConfig(config);
}
