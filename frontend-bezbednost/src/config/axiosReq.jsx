import axios from "axios";

const axiosReq = axios.create();

axiosReq.interceptors.request.use(
  (config) => {
    const token =  sessionStorage.getItem("token");
    return {
      ...config,
      headers: {
        ...(token !== null && { Authorization: `Bearer ${token}` }),
        ...config.headers,
      },
    };
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosReq;