<template>
  <v-card class="mt-2">
    <v-textarea v-model="query" label="Query Dsl Input" rows="10"></v-textarea>
    <v-textarea v-model="output" label="Output" rows="10"></v-textarea>
    <v-col cols="auto">
      <v-btn block class="mt-2" color="primary" @click="load">
        Execute Query
      </v-btn>
    </v-col>
  </v-card>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapStores} from "pinia";
import ProductApi from "@/services/ProductApi";

export default {
  name: 'InventoryView',
  computed: {
    ...mapStores(useAppStore),
  },
  data() {
    return {
      query: `
      query{
    product(id:1)
  {
        id
        name
        price
        description
        productCategory { id name }
        properties { id name description }
    }
}
    `,
      output: null,
    };
  },
  methods: {
    async load() {
      const data = await ProductApi.getByQuery(this.query)
      this.output = JSON.stringify(data)
    }
  },
}
</script>
