import ApiInterceptor from "@/plugins/api/ApiInterceptor";

const api = ApiInterceptor("inventory");

export default {
  async getByQuery(query) {
    return (await api.post("graphql", {
      query: query,
    }))?.data
  },
};
