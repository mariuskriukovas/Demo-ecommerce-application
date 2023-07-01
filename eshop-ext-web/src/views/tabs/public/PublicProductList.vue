<template>
  <v-card class="mt-2 ml-2 mr-2 mb-2" color="primary" title="Filters" variant="outlined">
    <v-row class="ml-2 mr-2">
      <v-col>
        <v-text-field v-model="publicProduct.filter" class="text-black" clearable label="Search"
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
    <v-data-table
      :expanded="expanded"
      :headers="headers"
      :items="items"
      :items-per-page="publicProduct.pagination.itemsPerPage"
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
      <template v-slot:[`item.productFiles`]="{ item }">
        <v-img
          :src="getItemSource(item)"
          height="250px"
          width="250px"
        ></v-img>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          color="blue-darken-2"
          icon="mdi-eye"
          size="large"
          @click="navigateToView(item)"
        ></v-icon>
      </template>
      <template v-slot:bottom>
        <v-pagination v-model="publicProduct.pagination.page" :length="pageCount" :total-visible="totalVisible"
                      @click="findProductsByFilter"></v-pagination>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import {mdiAccount,} from '@mdi/js'
import {DEFAULT_IMAGE_URL} from "@/utils/imageUtil";
import router, {PUBLIC_PRODUCT_VIEW_ROUTE_NAME} from "@/router";
import ProductApi from "@/services/ProductApi";
import {mapActions, mapState} from "pinia";
import {useProductStore} from "@/store/product";

export default {
  name: 'PublicProductList',
  computed: {
    ...mapState(useProductStore, ['publicProduct']),
  },
  data() {
    return {
      icons: {
        mdiAccount,
      },
      expanded: [],
      pageCount: 1,
      itemsPerPage: 10,
      totalVisible: 6,
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
        {title: 'Image', align: 'start', key: 'productFiles'},
        {title: 'Actions', align: 'end', key: 'actions'},
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
      items: [],
    };
  },
  async mounted() {
    await this.findProductsByFilter()
  },
  methods: {
    ...mapActions(useProductStore, ['getPagination']),
    async onSearch() {
      this.publicProduct.pagination.page = 1
      await this.findProductsByFilter()
    },
    async findProductsByFilter() {
      const data = (await ProductApi.getPublicProductsByFilter(this.publicProduct.filter, this.getPagination()))?.data?.allPublicProducts
      this.items = data?.content
      this.pageCount = data?.totalPages
    },
    getProperties(item) {
      return item?.raw?.properties ?? []
    },
    getProductCategory(item) {
      return item?.raw?.productCategory?.name ?? ""
    },
    async navigateToView(item) {
      await router.push({name: PUBLIC_PRODUCT_VIEW_ROUTE_NAME, params: {uid: item?.value?.uid}})
    },
    getItemSource(item) {
      const files = item?.raw?.productFiles
      if (files && files.length > 0) {
        return files[0]?.s3Url // primary product image
      } else {
        return DEFAULT_IMAGE_URL
      }
    }
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
