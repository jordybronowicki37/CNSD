import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig(({command, mode}) => {
  const env = loadEnv(mode, process.cwd(), "")
  return {
    plugins: [react()],
    server: {
      proxy: {
        '/users': {
          target: env.VITE_API_URI,
          changeOrigin: true,
          secure: false,
          ws: true,
        },
      },
    },
  }
})
