import { API_TOKEN } from "./Constants";

const IS_SERVER = typeof window === 'undefined';

export const getAccessToken = (): string | null => {
  if (IS_SERVER) {
    return null;
  }
  return localStorage.getItem(API_TOKEN);
};

export const setAccessToken = (token: string): void => {
  localStorage.setItem(API_TOKEN, token);
};

export const clearTokens = (): void => {
  localStorage.removeItem(API_TOKEN);
};
