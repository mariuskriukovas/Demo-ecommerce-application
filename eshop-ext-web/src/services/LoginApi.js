import ApiInterceptor from "@/plugins/api/ApiInterceptor";

const api = ApiInterceptor("accounts");

export default {
  async login(data) {
    return (await api.post("sessions/login", data))
  },
};
