import axios from "axios";

export default (basePath) => {
  const instance = axios.create({
    baseURL: `/${basePath}`, withCredentials: false, headers: {
      Accept: "application/json", "Content-Type": "application/json",
    },
  });

  return instance;
};
