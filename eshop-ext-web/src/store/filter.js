import {defineStore} from 'pinia'
import ProductApi from "@/services/ProductApi";

export const useFilterStore = defineStore('filter', {
  state: () => ({
    product: {
      filter: {
        name: null, description: null, category: null, priceFrom: null, priceTo: null, properties: null,
      }
    },
  }), getters: {}, actions: {
    async findProducts() {
      const data = await ProductApi.getProductsByFilterQuery(this.product.filter)
      return data?.data?.allProducts
    },
  }, persist: {
    enabled: true
  }
})
