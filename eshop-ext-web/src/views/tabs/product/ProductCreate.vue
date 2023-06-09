<template>
  <v-dialog
    v-model="product.isCreateProductModalVisible"
    min-width="800px"
    width="auto"
  >
    <v-card class="mt-2 ml-2 mr-2 mb-2" title="Product Create">
      <v-row class="ml-2 mr-2">
        <v-col cols="6">
          <v-text-field v-model="product.newProduct.name" clearable label="Name" variant="underlined"></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field v-model="product.newProduct.price" clearable label="Price"
                        type="number" variant="underlined"></v-text-field>
        </v-col>
      </v-row>
      <v-row class="ml-2 mr-2">
        <v-col cols="12">
          <v-select v-model="product.newProduct.productCategory" :items="classifiers.categories" clearable
                    item-title="name" label="Product Category" return-object
                    variant="underlined"></v-select>
        </v-col>
      </v-row>
      <v-row class="ml-2 mr-2">
        <v-col>
          <v-text-field v-model="product.newProduct.description" clearable label="Description"
                        variant="underlined"></v-text-field>
        </v-col>
      </v-row>
      <v-row class="ml-2 mr-2">
        <v-col>
          <v-file-input v-model="files" clearable label="Files" multiple variant="underlined"></v-file-input>
        </v-col>
      </v-row>
      <v-row class="ml-2 mr-2">
        <v-col cols="12">
          <PropertiesInput v-model="product.newProduct.properties"/>
        </v-col>
      </v-row>
      <v-row class="mb-2 ml-2 mr-2">
        <v-col class="text-right mr-2">
          <v-btn class="text-right mr-2" color="primary" outlined @click="onClose">
            Close
          </v-btn>
          <v-btn class="text-right" color="primary" outlined @click="onCreate">
            Create
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-dialog>
</template>

<script>
import {useProductStore} from "@/store/product";
import {mapActions, mapState} from "pinia";
import {useClassifierStore} from "@/store/classifier";
import {useSnackbarStore} from "@/store/snackbars";
import PropertiesInput from "@/components/PropertiesInput";

export default {
  name: 'ProductCreate',
  computed: {
    ...mapState(useProductStore, ['product']),
    ...mapState(useClassifierStore, ['classifiers']),
  },
  components: {
    PropertiesInput,
  },
  async mounted() {
    await this.loadProperties()
    await this.loadCategories()
  },
  data() {
    return {
      dialog: false,
      files: null,
    };
  },
  methods: {
    ...mapActions(useProductStore, ['closeCreateProductModal', 'createProduct']),
    ...mapActions(useClassifierStore, ['loadProperties', 'loadCategories']),
    ...mapActions(useSnackbarStore, ['openSnackbar']),

    onClose() {
      this.closeCreateProductModal()
    },
    async onCreate() {
      const successMessage = await this.createProduct(this.files)
      if (successMessage) {
        this.openSnackbar(successMessage)
        this.onClose()
      }
    },
  },
}
</script>
