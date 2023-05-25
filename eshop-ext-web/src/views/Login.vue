<template>
  <v-container class="fill-height">
    <v-responsive class="d-flex align-center text-center fill-height">
      <v-row class="d-flex align-center justify-center">
        <v-card style="border: 5px solid #1867C0;" title="Login" width="400">
          <v-form fast-fail @submit.prevent="onLogin">
            <v-col cols="auto">
              <v-text-field v-model="username" label="User Name"></v-text-field>
            </v-col>
            <v-col cols="auto">
              <v-text-field v-model="password" label="Password"></v-text-field>
            </v-col>
            <v-col cols="auto">
              <v-btn block class="mt-2" color="primary" type="submit">
                Sign in
              </v-btn>
            </v-col>
            <div class="mt-2">
              <p class="text-body-2">Don't have an account? <a href="#">Sign Up</a></p>
            </div>
          </v-form>
        </v-card>
      </v-row>
    </v-responsive>
  </v-container>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapActions} from "pinia";
import router from "@/router";

export default {
  name: 'LoginView',
  computed: {},
  data() {
    return {
      username: 'userA',
      password: 'userA_pass',
    };
  },
  methods: {
    ...mapActions(useAppStore, ['login']),

    async onLogin() {
      await this.login(this.$data)
      await router.push({path: 'home'})
    },
  },
}
</script>
