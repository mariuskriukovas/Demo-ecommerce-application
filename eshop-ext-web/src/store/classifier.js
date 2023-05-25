import {defineStore} from 'pinia'
import ClassifierApi from "@/services/ClassifierApi";

export const useClassifierStore = defineStore('classifier', {
  state: () => ({
    classifiers: {
      properties: [], categories: [],
    },
  }), getters: {}, actions: {
    async loadProperties() {
      if (this.classifiers.properties?.length === 0) {
        this.classifiers.properties = await ClassifierApi.getClassifierValues("properties")
      }
    }, async loadCategories() {
      if (this.classifiers.categories?.length === 0) {
        this.classifiers.categories = await ClassifierApi.getClassifierValues("categories")
      }
    },
  }, persist: {
    enabled: true
  }
})
