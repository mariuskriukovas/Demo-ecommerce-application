import {defineStore} from 'pinia'
import ProductApi from "@/services/ProductApi";

export const useProductStore = defineStore('product', {
  state: () => ({
    product: {
      filter: {
        name: null, description: null, category: null, priceFrom: null, priceTo: null, properties: null,
      }, newProduct: {
        name: null, description: null, productCategoryName: null, price: null, properties: null,
      }, isCreateProductModalVisible: false,
    },
  }), getters: {}, actions: {
    async findProducts() {
      const data = await ProductApi.getProductsByFilterQuery(this.product.filter)
      return data?.data?.allProducts
    }, openCreateProductModal() {
      this.product.isCreateProductModalVisible = true
    }, closeCreateProductModal() {
      this.product.isCreateProductModalVisible = false
    }, async createProduct(files = []) {
      const data = await ProductApi.createProduct({...this.product.newProduct, files})
      console.log(data)
      // todo add error and success handling
    },
  }, persist: {
    enabled: true
  }
})
