import axios from "axios";
import {isArray} from "lodash";

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

  const instance = axios.create({
    baseURL: `/${basePath}`, withCredentials: false, headers,
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
