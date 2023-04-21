import {defineStore} from 'pinia'
import LoginApi from "@/services/LoginApi";

export const useAppStore = defineStore('app', {
  state: () => ({
    auth: {
      isAuthenticated: null, authenticatedHeader: null,
    },
  }), getters: {
    isAuthenticated(state) {
      return state.auth?.isAuthenticated === true
    }, getAuthHeader(state) {
      return state.auth?.authenticatedHeader
    },
  }, actions: {
    async login(data) {
      const {username, password} = data
      try {
        const authPayload = await LoginApi.login({username, password})
        const headers = authPayload?.headers

        if (headers.has("authorization")) {
          this.auth.isAuthenticated = true;
          this.auth.authenticatedHeader = headers.get("authorization");
        }
      } catch (error) {
        console.error(error)
        throw error
      }
    },
    logout() {
      // Todo add BE call
      this.auth.isAuthenticated = false
      this.auth.authenticatedHeader = null
    }
  },
})
