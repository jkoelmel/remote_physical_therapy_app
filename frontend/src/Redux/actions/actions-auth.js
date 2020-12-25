import axios from 'axios';

export const getAuth = (url, params) => axios.get(url, {
    params,
  });

export const putAuth = (url, data) => axios.put(url, data);

export const postAuth = (url, data) => axios.post(url, data);

export const deleteAuth = (url) => axios.delete(url);
