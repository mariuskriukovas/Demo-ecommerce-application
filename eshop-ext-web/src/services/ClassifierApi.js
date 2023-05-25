import ApiInterceptor from "@/plugins/api/ApiInterceptor";


const api = ApiInterceptor("inventory");

export default {
  async getClassifierValues(classifier) {
    return (await api.get(`classifiers/${classifier}`))?.data
  },
};
