import {defineStore} from 'pinia'
import ProductApi from "@/services/ProductApi";

export const useProductStore = defineStore('product', {
  state: () => ({
    product: {
      filter: {
        name: null, description: null, category: null, priceFrom: null, priceTo: null, properties: null,
      }, newProduct: {
        name: null, description: null, productCategory: null, price: null, properties: [],
      }, isCreateProductModalVisible: false,
    }, publicProduct: {
      filter: "", pagination: {
        itemsPerPage: 10, page: 1,
      },
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
      return await ProductApi.createProduct({...this.product.newProduct, files})
    }, getPagination() {
      const pagination = {...this.publicProduct.pagination}
      pagination.page = pagination?.page - 1
      return pagination
    },
  }, persist: {
    enabled: true
  }
})
