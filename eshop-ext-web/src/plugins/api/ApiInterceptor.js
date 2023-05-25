import axios from "axios";
import {isArray, isObject} from "lodash";

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

  const stringify = (value) => {
    if (value instanceof Blob) {
      return value
    } else if (isObject(value)) {
      return JSON.stringify(value)
    } else return value
  }

  instance.mapToFormData = (form) => {
    const formData = new FormData()

    Object.keys(form).forEach((key) => {
      const val = form[key]
      if (isArray(val)) {
        Object.values(val).forEach((value) => {
          formData.append(key, stringify(value))
        })
      } else if (val !== null && val !== undefined) {
        formData.append(key, stringify(val))
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
