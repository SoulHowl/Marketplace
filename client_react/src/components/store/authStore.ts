// import { CurrentUserQuery } from '@data/users/urql_generated/currentUser.generated';
// import { clearTokens } from '@util/tokens';
// import Router from 'next/router';
// import useFeaturesStore from './featuresStore';


import create from 'zustand';
import { User } from '../util/Types';

type AuthState = {
  logOut: (query?: string) => void;
  me: User | null;
  setMe: (me: User | null) => void;
};

const useAuthStore = create<AuthState>((set) => ({
  logOut: () => {
    set({  me: null });
  },
  me: null,
 
  setMe: (me: User | null) => {
    set({ me });
  },
}));

export const useCurrentUser = (): User | null => {
  const me = useAuthStore((state) => state.me);
  return me;
};

export default useAuthStore;
