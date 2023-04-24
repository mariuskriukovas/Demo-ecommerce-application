import ApiInterceptor from "@/plugins/api/ApiInterceptor";

const api = ApiInterceptor("inventory");

export default {
  async getByQuery(query) {
    return (await api.post("graphql", {
      query: query,
    }))?.data
  }, async getProductsByFilterQuery(filter) {

    // todo implement full query later
    const query = `
      query{
    allProducts(filter: { name: "${filter?.name}" })
  {
        id
        name
        price
        description
        productCategory { id name }
        properties { id name description }
    }
}
    `
    return (await api.post("graphql", {
      query,
    }))?.data
  },
};
