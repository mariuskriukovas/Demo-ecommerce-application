<template>
  <v-card class="mt-2 ml-2 mr-2 mb-2" title="Product filter">
    <v-row>
      <v-col cols="6">
        <v-text-field v-model="filter.name" label="Name"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field v-model="filter.category" label="Product Category"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="6">
        <v-text-field v-model="filter.properties" label="Properties"></v-text-field>
      </v-col>
      <v-col cols="3">
        <v-text-field v-model="filter.priceFrom" label="Price From"></v-text-field>
      </v-col>
      <v-col cols="3">
        <v-text-field v-model="filter.priceTo" label="Price To"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-text-field v-model="filter.description" label="Description"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="mb-2">
      <v-col class="text-right mr-2">
        <v-btn class="text-right" @click="loadProducts">
          Search
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
  <v-card class="mt-2 ml-2 mr-2 mb-2" title="Product list">
    <v-data-table
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items="items"
      class="elevation-1"
      item-value="name"
    >
      <template v-slot:[`item.productCategory`]="{ item }">
        <span>{{ parseProductCategory(item) }}</span>
      </template>
      <template v-slot:[`item.properties`]="{ item }">
        <span>{{ item }}</span>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import {useAppStore} from "@/store/app";
import {mapStores} from "pinia";
import ProductApi from "@/services/ProductApi";

export default {
  name: 'ProductView',
  computed: {
    ...mapStores(useAppStore),
  },
  data() {
    return {
      filter: {
        name: null,
        description: null,
        category: null,
        priceFrom: null,
        priceTo: null,
        properties: null,
      },
      itemsPerPage: 10,
      headers: [
        {
          title: 'Name',
          align: 'start',
          sortable: false,
          key: 'name',
        },
        {title: 'Price', align: 'end', key: 'price'},
        {title: 'Description', align: 'end', key: 'description'},
        // {title: 'Category', align: 'end', key: 'productCategory'},
        // {title: 'Properties', align: 'end', key: 'properties'},
      ],
      items: [],
    };
  },
  methods: {
    async loadProducts() {
      const data = await ProductApi.getProductsByFilterQuery(this.filter)
      this.items = data?.data?.allProducts
    },
  },
}
</script>
