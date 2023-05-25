<template>
  <v-card class="mt-2 ml-2 mr-2 mb-2" title="Product">
    <v-row class="ml-2 mr-2">
      <v-col cols="6">
        <v-text-field v-model="product.name" :clearable="!viewMode" :readonly="viewMode" label="Name"
                      variant="underlined"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field v-model="product.price" :clearable="!viewMode" :readonly="viewMode"
                      label="Price" type="number" variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col cols="12">
        <v-select v-model="product.productCategory" :clearable="!viewMode" :items="classifiers.categories"
                  :readonly="viewMode" item-title="name" label="Product Category"
                  return-object variant="underlined"></v-select>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col>
        <v-text-field v-model="product.description" :clearable="!viewMode" :readonly="viewMode"
                      label="Description" variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col cols="12">
        <PropertiesInput v-model="product.properties" :readonly="viewMode"/>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <ImageCarousel v-model="product.productFiles" :readonly="viewMode" :refresh="loadProduct"></ImageCarousel>
    </v-row>
    <v-row class="mb-2 ml-2 mr-2">
      <v-col class="text-right mr-2">
        <v-btn v-if="viewMode" class="text-right mr-2" color="primary" outlined @click="navigateToEdit">
          Edit
        </v-btn>
        <v-btn v-if="!viewMode" class="text-right" color="primary" outlined @click="onSave">
          Save
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import {mapActions, mapState} from "pinia";
import {useClassifierStore} from "@/store/classifier";
import ProductApi from "@/services/ProductApi";
import router, {PRODUCT_VIEW_ROUTE_NAME} from "@/router";
import {useSnackbarStore} from "@/store/snackbars";
import PropertiesInput from "@/components/PropertiesInput.vue";
import ImageCarousel from "@/components/ImageCarousel.vue";

export default {
  name: 'ProductView',
  components: {PropertiesInput, ImageCarousel},
  computed: {
    ...mapState(useClassifierStore, ['classifiers']),
    viewMode() {
      return this.$route.params?.mode !== 'edit'
    }
  },
  async mounted() {
    await this.loadProperties()
    await this.loadCategories()

    await this.loadProduct()
  },
  data() {
    return {
      activeIndex: 0,
      product: {
        name: null,
        description: null,
        productCategory: null,
        price: null,
        properties: null,
        productFiles: [],
      },
    };
  },
  watch: {
    '$route.params': {
      handler: async function (toParams, previousParams) {
        if (previousParams?.mode === 'edit' && !toParams?.mode) {
          if (confirm('All changes will be lost, are you want to proceed ?')) {
            await this.loadProduct()
          }
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    ...mapActions(useClassifierStore, ['loadProperties', 'loadCategories']),
    ...mapActions(useSnackbarStore, ['openSnackbar']),
    async navigateToEdit() {
      await router.push({name: PRODUCT_VIEW_ROUTE_NAME, params: {...this.$route.params, mode: 'edit'}})
    },
    async loadProduct() {
      const {id} = this.$route.params
      const {data} = await ProductApi.getProductById(id)
      this.product = {...this.product, ...data?.product}
    },
    async onSave() {
      const {id} = this.$route.params
      const message = await ProductApi.updateProduct(id, this.product)

      if (message) {
        this.openSnackbar(message)
        await this.loadProduct()
      }
    },
  },
}
</script>
