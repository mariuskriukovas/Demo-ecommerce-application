import ApiInterceptor from "@/plugins/api/ApiInterceptor";

const api = ApiInterceptor("inventory");

export default {
  async deleteProductFile(id) {
    return (await api.delete(`productFiles/${id}`))?.data
  },
};
