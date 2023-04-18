import { Loader, LoadingOverlay } from '@mantine/core';
import React, { ReactNode, useEffect, useMemo, useState } from 'react';
import useAuthStore from '../components/store/authStore';
import axios from 'axios';
import { useAuthHeader, useAuthUser } from 'react-auth-kit';
import { User } from '../components/util/Types';




const UserProvider = ({ children }: { children?: ReactNode }): JSX.Element => {

  const setUser = useAuthStore((state) => state.setMe);
  console.log("UserProvider layer")
  
  const auth = useAuthUser()()?.user;
  const token = useAuthHeader()();
  
  
  useEffect(()=>{
    console.log("getting user in UserProvider");
    if( auth && token){
    axios({
      method: 'GET',
      url:`http://localhost:8080/market/menu/user`,
      headers:{
      Authorization:`${token}`,
      'Access-Control-Allow-Headers': 'Content-Type,Authorization',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
      'Access-Control-Allow-Credentials': true,
     
        },
        params:{userid: auth.id}
    }).then(({data}:{data:User} ) =>{
       
      if(data){   
        
        setUser(data);
      }
      
    }).catch((error) =>{console.log(error.message);});
  }
     
  },[auth, setUser, token])



  // eslint-disable-next-line react/jsx-no-useless-fragment
  return <>{children}</>;
};

export default UserProvider;