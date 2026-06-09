import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  base: '/pdfforge/',
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'https://pdfforge-3iaz.onrender.com',
        changeOrigin: true,
      }
    }
  }
})
