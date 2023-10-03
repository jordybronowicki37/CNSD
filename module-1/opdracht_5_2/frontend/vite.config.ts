import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default ({mode}) => {
  let target = "http://localhost:8080";
  if (mode === "production") {
    // TODO add aws api uri
    target = "http://localhost:8081"
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
