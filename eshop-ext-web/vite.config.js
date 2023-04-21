// Plugins
import vue from '@vitejs/plugin-vue'
import vuetify, {transformAssetUrls} from 'vite-plugin-vuetify'

// Utilities
import {defineConfig} from 'vite'
import {fileURLToPath, URL} from 'node:url'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue({
      template: {transformAssetUrls}
    }),
    // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vite-plugin
    vuetify({
      autoImport: true,
      styles: {
        configFile: 'src/styles/settings.scss',
      },
    }),
  ],
  define: {'process.env': {}},
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
    extensions: [
      '.js',
      '.json',
      '.jsx',
      '.mjs',
      '.ts',
      '.tsx',
      '.vue',
    ],
  },
  server: {
    port: 3000,
    proxy: {
      '^/accounts': {
        target: 'http://localhost:9000',
        rewrite: path => path.replace(/^\/accounts/, 'api'),
        secure: false,
        changeOrigin: true,
        logLevel: 'debug',
      },
      '^/inventory': {
        target: 'http://localhost:9001',
        rewrite: path => path.replace(/^\/inventory/, 'api'),
        secure: false,
        changeOrigin: true,
        logLevel: 'debug',
      },
    },
  },
})
