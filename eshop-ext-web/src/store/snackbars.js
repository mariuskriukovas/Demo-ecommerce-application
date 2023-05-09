import {defineStore} from 'pinia'

export const useSnackbarStore = defineStore('snackbar', {
  state: () => ({
    snackbar: {
      visible: false, message: "",
    }
  }), getters: {}, actions: {
    openSnackbar(message) {
      this.snackbar.visible = true
      this.snackbar.message = message
    }, closeSnackbar() {
      this.snackbar.visible = false
    },
  }, persist: {
    enabled: false
  }
})
