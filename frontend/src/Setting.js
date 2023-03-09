import axios from "axios";

export const LOCAL = "ec2-52-79-203-163.ap-northeast-2.compute.amazonaws.com:8080";
export const api = axios.create({
  baseURL: LOCAL,
  withCredentials: true
});