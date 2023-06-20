<template>
  <v-card class="mt-2 ml-2 mr-2 mb-2" title="Public Product">
    <v-row class="ml-2 mr-2">
      <v-col cols="6">
        <v-text-field v-model="product.name" :clearable="false" :readonly="true" label="Name"
                      variant="underlined"></v-text-field>
      </v-col>
      <v-col cols="6">
        <v-text-field v-model="product.price" :clearable="false" :readonly="true"
                      label="Price" type="number" variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col cols="12">
        <v-select v-model="product.productCategory" :clearable="false" :items="classifiers.categories"
                  :readonly="true" item-title="name" label="Product Category"
                  return-object variant="underlined"></v-select>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col>
        <v-text-field v-model="product.description" :clearable="false" :readonly="true"
                      label="Description" variant="underlined"></v-text-field>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <v-col cols="12">
        <PropertiesInput v-model="product.properties" :readonly="true"/>
      </v-col>
    </v-row>
    <v-row class="ml-2 mr-2">
      <ImageCarousel v-model="product.productFiles" :readonly="true" :refresh="loadProduct"></ImageCarousel>
    </v-row>
  </v-card>
</template>

<script>
import {mapActions, mapState} from "pinia";
import {useClassifierStore} from "@/store/classifier";
import ProductApi from "@/services/ProductApi";
import {useSnackbarStore} from "@/store/snackbars";
import PropertiesInput from "@/components/PropertiesInput.vue";
import ImageCarousel from "@/components/ImageCarousel.vue";

export default {
  name: 'PublicProductView',
  components: {PropertiesInput, ImageCarousel},
  computed: {
    ...mapState(useClassifierStore, ['classifiers']),
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
  methods: {
    ...mapActions(useClassifierStore, ['loadProperties', 'loadCategories']),
    ...mapActions(useSnackbarStore, ['openSnackbar']),
    async loadProduct() {
      const {uid} = this.$route.params
      const {data} = await ProductApi.getPublicProductByUid(uid)
      this.product = {...this.product, ...data?.publicProduct}
    },
  },
}
</script>
