<template>
  <v-card class="mt-2 ml-2 mr-2 mb-2" color="primary" title="Filters" variant="outlined">
    <v-row class="ml-2 mr-2">
      <v-col cols="6">
        <v-text-field v-model="product.filter.name" class="text-black" clearable label="Name"
                      variant="underlined"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field v-model="product.filter.category" class="text-black" clearable label="Product Category"
                      variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col cols="6">
        <v-text-field v-model="product.filter.properties" class="text-black" clearable disabled
                      label="Properties" variant="underlined"></v-text-field>
      </v-col>
      <v-col cols="3">
        <v-text-field v-model="product.filter.priceFrom" class="text-black" clearable label="Price From"
                      type="number" variant="underlined"></v-text-field>
      </v-col>
      <v-col cols="3">
        <v-text-field v-model="product.filter.priceTo" class="text-black" clearable label="Price To"
                      type="number" variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col>
        <v-text-field v-model="product.filter.description" class="text-black" clearable label="Description"
                      variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2 mb-2">
      <v-col class="text-right">
        <v-btn class="text-right" color="primary" outlined @click="onSearch">
          Search
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
  <v-card class="mt-2 ml-2 mr-2 mb-2" color="primary" title="Products" variant="outlined">
    <v-row class="ml-2 mr-2 mb-2">
      <v-col class="text-right">
        <v-btn class="text-right" color="primary" outlined @click="onCreate">
          Create new product
        </v-btn>
      </v-col>
    </v-row>
    <v-data-table
      :expanded="expanded"
      :headers="headers"
      :items="items"
      :items-per-page="itemsPerPage"
      class="elevation-1 primary"
      show-expand
    >
      <template v-slot:[`expanded-row`]="{ item }">
        <td :colspan="headers.length+1" class="ml-2 mr-2">
          <v-data-table
            :headers="expendedTableHeaders"
            :items="getProperties(item)"
            class="elevation-1 remove-footer remove-header expanded-row-colors"
          ></v-data-table>
        </td>
      </template>
      <template v-slot:[`item.productCategory`]="{ item }">
        <span>{{ getProductCategory(item) }}</span>
      </template>
    </v-data-table>
  </v-card>
  <ProductCreate></ProductCreate>
</template>

<script>
import {useProductStore} from "@/store/product";
import {mapActions, mapState} from "pinia";
import ProductCreate from "@/views/tabs/product/ProductCreate.vue";

export default {
  name: 'ProductList',
  components: {ProductCreate},
  computed: {
    ...mapState(useProductStore, ['product']),
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
        {title: 'Category', align: 'end', key: 'productCategory'},
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
    ...mapActions(useProductStore, ['findProducts', 'openCreateProductModal']),
    async onSearch() {
      this.items = await this.findProducts()
    },
    onCreate() {
      this.openCreateProductModal()
    },
    getProperties(item) {
      return item?.raw?.properties ?? []
    },
    getProductCategory(item) {
      return item?.raw?.productCategory?.name ?? ""
    },
  },
}
</script>
<style>
.text-black input {
  color: #212121 !important;
}

/* Todo fix VDataTable issues in Vuetify3  */
.remove-footer .v-data-table-footer {
  display: none;
}

.remove-header thead .v-data-table__td {
  display: none;
}

.expanded-row-colors .v-data-table__td {
  background-color: rgba(187, 222, 251, 0.67) !important;
}
</style>
