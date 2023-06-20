<template>
  <v-container class="fill-height">
    <v-responsive class="d-flex align-center text-center fill-height">
      <v-row class="d-flex align-center justify-center">
        <v-card style="border: 5px solid #1867C0;" title="Sign Up" width="400">
          <v-form fast-fail @submit.prevent="onSignUp">
            <v-col cols="auto">
              <v-text-field v-model="username" label="User Name"></v-text-field>
            </v-col>
            <v-col cols="auto">
              <v-text-field v-model="password" label="Password"></v-text-field>
            </v-col>
            <v-col cols="auto">
              <v-text-field v-model="repeatPassword" label="Repeat Password"></v-text-field>
            </v-col>
            <v-col cols="auto">
              <v-btn block class="mt-2" color="primary" type="submit">
                Sign Up
              </v-btn>
            </v-col>
          </v-form>
        </v-card>
      </v-row>
    </v-responsive>
  </v-container>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapActions} from "pinia";

// Todo implement me
export default {
  name: 'SignUpView',
  computed: {},
  data() {
    return {
      username: 'userA',
      password: 'userA_pass',
      repeatPassword: 'userA_pass',
    };
  },
  methods: {
    ...mapActions(useAppStore, ['login']),

    async onSignUp() {

    },
  },
}
</script>
