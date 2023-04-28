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
      operation: "allProducts",
      fields: ["id", "name", "price", "description", {
        productCategory: ["id", "name"],
        properties: ["id", "name", "description"]
      }],
      variables: {
        filter: {
          name: "filter",
          type: "ProductFilter!",
          value: validFilters,
        },
      },
    },]);
    return (await api.post("graphql", query))?.data
  },
};
