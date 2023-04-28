<template>
  <v-card class="mt-2 ml-2 mr-2 mb-2" title="Product filter">
    <v-row>
      <v-col cols="6">
        <v-text-field v-model="product.filter.name" label="Name"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field v-model="product.filter.category" label="Product Category"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="6">
        <v-text-field v-model="product.filter.properties" label="Properties"></v-text-field>
      </v-col>
      <v-col cols="3">
        <v-text-field v-model="product.filter.priceFrom" label="Price From"></v-text-field>
      </v-col>
      <v-col cols="3">
        <v-text-field v-model="product.filter.priceTo" label="Price To"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-text-field v-model="product.filter.description" label="Description"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="mb-2">
      <v-col class="text-right mr-2">
        <v-btn class="text-right" @click="onSearch">
          Search
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
  <v-card class="mt-2 ml-2 mr-2 mb-2" title="Product list">
    <v-data-table
      :expanded="expanded"
      :items-per-page="itemsPerPage"
      :items="items"
      :headers="headers"
      class="elevation-1 primary"
      show-expand
    >
      <template v-slot:[`expanded-row`]="{ item }" >
        <td :colspan="headers.length+1">
          <v-data-table
            :headers="expendedTableHeaders"
            :items="getProperties(item)"
            class="elevation-2 ml-2 mr-2"
            hide-default-footer
          ></v-data-table>
        </td>
      </template>
      <!--      <template v-slot:[`item.properties`]="{ item }">-->
      <!--        <span>{{ item }}</span>-->
      <!--      </template>-->
    </v-data-table>
  </v-card>
</template>

<script>
import {useFilterStore} from "@/store/filter";
import {mapActions, mapState} from "pinia";

export default {
  name: 'ProductView',
  computed: {
    ...mapState(useFilterStore, ['product']),
  },
  data() {
    return {
      singleExpand: true,
      expanded: [],
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
      expendedTableHeaders: [
        {
          title: 'Name',
          align: 'start',
          sortable: false,
          key: 'name',
        },
        {title: 'Description', align: 'end', key: 'description'},
      ],
      // not seeing purpose of persisting items
      items: [],
    };
  },
  methods: {
    ...mapActions(useFilterStore, ['findProducts']),
    async onSearch() {
      this.items = await this.findProducts()
    },
    getProperties(item) {
      return item?.raw?.properties ?? []
    }
  },
}
</script>
<style>
</style>
