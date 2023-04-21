<template>
  <v-app-bar color="primary" flat>
    <v-app-bar-title>
      Demo UI for Marius ecommerce application
    </v-app-bar-title>
    <template v-slot:append>
      <v-btn v-if="store.isAuthenticated" color="white" @click="logout">
        Sign out
      </v-btn>
    </template>
  </v-app-bar>
</template>

<script setup>
import {useAppStore} from '@/store/app'
import router from "@/router";

const store = useAppStore()

async function logout() {
  store.logout()
  await router.push({path: '/'})
}
</script>
