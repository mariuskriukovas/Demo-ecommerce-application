import {defineStore} from 'pinia'
import LoginApi from "@/services/LoginApi";

export const useAppStore = defineStore('app', {
  state: () => ({
    user: {
      username: null, roles: [],
    }, auth: {
      isAuthenticated: null, authenticatedHeader: null,
    },
  }), getters: {
    isAuthenticated(state) {
      return state.auth?.isAuthenticated === true
    }, getAuthHeader(state) {
      return state.auth?.authenticatedHeader
    }, getUsername(state) {
      return state.user?.username
    }, hasRole(state) {
      return (role) => state.user?.roles.find(item => item?.authority === role) !== undefined
    },
  }, actions: {
    async login(data) {
      const {username, password} = data
      try {
        const authPayload = await LoginApi.login({username, password})
        const {data, headers} = authPayload

        if (headers.has("authorization")) {
          this.auth.isAuthenticated = true;
          this.auth.authenticatedHeader = headers.get("authorization");
          this.user = data
        }
      } catch (error) {
        console.error(error)
        throw error
      }
    }, logout() {
      // Todo add BE call
      this.auth = {isAuthenticated: null, authenticatedHeader: null,}
      this.user = {username: null, roles: [],}
    }
  }, persist: {
    enabled: true
  }
})
