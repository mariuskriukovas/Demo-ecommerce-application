import ApiInterceptor from "@/plugins/api/ApiInterceptor";

const api = ApiInterceptor("api/auth");

export default {
  async login(data) {
    return (await api.post("/login", data))
  },
};
