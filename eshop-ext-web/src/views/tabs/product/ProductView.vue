<template>
  <v-card class="mt-2 ml-2 mr-2 mb-2" title="Product">
    <v-row class="ml-2 mr-2">
      <v-col cols="6">
        <v-text-field v-model="product.name" :clearable="!viewMode" :readonly="viewMode" label="Name"
                      variant="underlined"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-select v-model="product.productCategoryName" :clearable="!viewMode" :items="classifiers.categories"
                  :readonly="viewMode" item-title="name" item-value="name"
                  label="Product Category" variant="underlined"></v-select>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col cols="6">
        <v-text-field v-model="product.properties" :clearable="!viewMode" disabled
                      label="Properties" variant="underlined"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field v-model="product.price" :clearable="!viewMode" :readonly="viewMode"
                      label="Price" type="number" variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col>
        <v-text-field v-model="product.description" :clearable="!viewMode" :readonly="viewMode"
                      label="Description" variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row v-if="viewMode" class="ml-2 mr-2">
      <v-carousel v-model="activeIndex" show-arrows="hover">
        <v-carousel-item
          v-for="(item,i) in product.productFiles"
          :key="i"
          :src="item.file.s3Url"
          :width="500"
          aspect-ratio="16/9"
          class="ma-auto"
          cover
        >
        </v-carousel-item>
      </v-carousel>
    </v-row>
    <v-row v-else class="ml-2 mr-2">
      <v-col>
        <v-file-input v-model="product.files" clearable label="Files" multiple variant="underlined"></v-file-input>
      </v-col>
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
import {DEFAULT_IMAGE_URL} from "@/utils/imageUtil";
import router, {PRODUCT_VIEW_ROUTE_NAME} from "@/router";

export default {
  name: 'ProductView',
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
        productCategoryName: null,
        price: null,
        properties: null,
        productFiles: [],
        files: []
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
    async navigateToEdit() {
      await router.push({name: PRODUCT_VIEW_ROUTE_NAME, params: {...this.$route.params, mode: 'edit'}})
    },
    async loadProduct() {
      const {id} = this.$route.params
      const {data} = await ProductApi.getProductById(id)
      this.product = {...this.product, ...data?.product, productCategoryName: data?.product?.productCategory?.name}

      if (this.product.productFiles.length < 1) {
        data.product.productFiles.push({
          file: {
            s3Url: DEFAULT_IMAGE_URL
          }
        })
      }
    },
    async onSave() {
      const data = await ProductApi.updateProduct(this.product)
      console.log(data) // todo implement notifications
    }
  },
}
</script>
