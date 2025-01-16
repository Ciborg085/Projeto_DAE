import { defineNuxtConfig } from 'nuxt/config'

export default defineNuxtConfig({
  modules: [
      '@pinia/nuxt'
  ],

  runtimeConfig: {
      public: {
          API_URL: process.env.API_URL || 'http://localhost:8080/academics/api'
      }
  },

  compatibilityDate: '2025-01-14'
})