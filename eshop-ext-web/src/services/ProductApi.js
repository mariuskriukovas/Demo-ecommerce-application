import ApiInterceptor from "@/plugins/api/ApiInterceptor";
import * as gql from 'gql-query-builder'
import {omitBy} from "lodash";


const api = ApiInterceptor("inventory");

export default {
  async getByQuery(query) {
    return (await api.post("graphql", {
      query: query,
    }))?.data
  }, async getProductsByFilterQuery(filter) {
    const validFilters = omitBy(filter, v => v == null || !v?.length)

    const query = gql.query([{
      operation: "allProducts", fields: ["id", "name", "price", "description", {
        productCategory: ["id", "name"], properties: ["id", "name", "description"], productFiles: [{
          file: ["s3Url"],
        }]
      }], variables: {
        filter: {
          name: "filter", type: "ProductFilter!", value: validFilters,
        },
      },
    },]);
    return (await api.post("graphql", query))?.data
  }, async getProductById(id) {

    const query = gql.query([{
      operation: "product", fields: ["id", "name", "price", "description", {
        productCategory: ["id", "name"], properties: ["id", "name", "description"], productFiles: [{
          file: ["s3Url"],
        }]
      }], variables: {
        id: {
          name: "id", type: "Int!", value: id,
        },
      },
    },]);
    return (await api.post("graphql", query))?.data
  }, async createProduct(data = {}) {
    return (await api.post("products", api.mapToFormData(data), api.fileHeaders))?.data
  }, async updateProduct(data = {}) {
    return (await api.put("products", api.mapToFormData(data), api.fileHeaders))?.data
  },
};
