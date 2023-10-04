import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default () => {
  const config = {
    plugins: [react()],
  }
  return defineConfig(config);
}
