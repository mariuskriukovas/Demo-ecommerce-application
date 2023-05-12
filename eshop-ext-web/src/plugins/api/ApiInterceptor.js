import axios from "axios";
import {isArray} from "lodash";

export default (basePath) => {
  const headers = {
    Accept: "application/json", "Content-Type": "application/json",
  }

  const instance = axios.create({
    baseURL: `/${basePath}`, withCredentials: false, headers,
  });

  instance.interceptors.request.use(function (config) {
    const appStorage = JSON.parse(sessionStorage.getItem('app'))
    const authenticatedHeader = appStorage?.auth?.authenticatedHeader ?? null

    if (authenticatedHeader) {
      config.headers.Authorization = authenticatedHeader;
    }

    return config;
  });

  instance.mapToFormData = (form) => {
    const formData = new FormData()

    Object.keys(form).forEach((key) => {
      const val = form[key]
      if (isArray(val)) {
        Object.values(val).forEach((value) => // ?? JSON.stringify(value)
          value instanceof Blob ? formData.append(key, value) : formData.append(key, value),)
      } else if (val !== null && val !== undefined) {
        formData.append(key, val)
      }
    })

    return formData
  }

  instance.fileHeaders = {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }

  return instance;
};
