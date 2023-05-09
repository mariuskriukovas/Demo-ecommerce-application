<template>
  <v-snackbar
    v-model="store.snackbar.visible"
  >
    {{ store.snackbar.message }}

    <template v-slot:actions>
      <v-btn
        color="pink"
        variant="text"
        @click="store.closeSnackbar"
      >
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script setup>
import {useSnackbarStore} from '@/store/snackbars'

const store = useSnackbarStore()
</script>
