import ApiInterceptor from "@/plugins/api/ApiInterceptor";
import * as gql from 'gql-query-builder'
import {omitBy} from "lodash";

const api = ApiInterceptor("api/inventory");

export default {
  async getByQuery(query) {
    return (await api.post("graphql", {
      query: query,
    }))?.data
  }, async getProductsByFilterQuery(filter) {
    const validFilters = omitBy(filter, v => v == null || !v?.length)

    const query = gql.query([{
      operation: "allProducts", fields: ["id", "name", "price", "description", {
        productCategory: ["id", "name"],
        properties: ["id", "name", "description"],
        productFiles: ["id", "key", "fileName", "s3Url"],
      }], variables: {
        filter: {
          name: "filter", type: "ProductFilter!", value: validFilters,
        },
      },
    },]);
    return (await api.post("graphql", query))?.data
  }, async getPublicProductsByFilter(filter, pageable) {
    const query = gql.query([{
      operation: "allPublicProducts", fields: ["first", "last", "totalPages", "totalElements", {
        content: ["uid", "name", "price", "description", {
          productCategory: ["uid", "name"],
          properties: ["uid", "name", "description"],
          productFiles: ["key", "fileName", "s3Url"],
        }]
      }], variables: {
        searchBox: {
          name: "searchBox", type: "String!", value: filter,
        }, pageable: {
          name: "pageable", type: "PageablePayload", value: pageable,
        }
      },
    },]);
    return (await api.post("graphql", query))?.data
  }, async getProductById(id) {

    const query = gql.query([{
      operation: "product", fields: ["id", "name", "price", "description", {
        productCategory: ["id", "name"],
        properties: ["id", "name", "description"],
        productFiles: ["id", "key", "fileName", "s3Url"],
      }], variables: {
        id: {
          name: "id", type: "Int!", value: id,
        },
      },
    },]);
    return (await api.post("graphql", query))?.data
  }, async createProduct(data = {}) {
    return (await api.post("products", api.mapToFormData(data), api.fileHeaders))?.data
  }, async updateProduct(id, data = {}) {
    return (await api.put(`products/${id}`, data))?.data
  }, async uploadProductFiles(id, files = []) {
    return (await api.post(`products/${id}/files`, api.mapToFormData({files}), api.fileHeaders))?.data
  },
};
