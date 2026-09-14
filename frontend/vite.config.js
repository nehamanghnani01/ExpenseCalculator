import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    watch: {
      // Docker Desktop bind mounts on Windows don't reliably emit native
      // fs events, so HMR silently misses changes without polling.
      usePolling: true,
    },
  },
})
