import axios from "axios";

export default (basePath) => {
  // Todo improve latter
  const appStorage = JSON.parse(sessionStorage.getItem('app'))
  const authenticatedHeader = appStorage?.auth?.authenticatedHeader ?? null

  const headers = {
    Accept: "application/json", "Content-Type": "application/json",
  }

  if (authenticatedHeader) {
    headers['Authorization'] = authenticatedHeader
  }

  return axios.create({
    baseURL: `/${basePath}`, withCredentials: false, headers,
  });
};
