import {
  Button,
  Group,
  ScrollArea,
  Space,
  Stack, Text, Title} from '@mantine/core';

import React, { useCallback, useEffect, useRef, useState } from 'react';
import InfinitePaperLayout from '../layout/infinitePaperLayout';
import axios from 'axios';
import {useAuthUser, useAuthHeader} from 'react-auth-kit';
import { CartView, User } from '../components/util/Types';
import CartItemForm from '../components/Item/CartItem';
import { setServers } from 'dns';
import useAuthStore, { useCurrentUser } from '../components/store/authStore';


const Cart=()=> {
  const [error, setError] = useState<string | null>(null);
  const [cart, setCart] = useState<CartView | null>();
  const [sum, SetSum] = useState<number>(0);
  const  token = useAuthHeader()();
  const user = useCurrentUser();
  const setMe = useAuthStore((state) => state.setMe);
  const updateCart =(arrayId:number) =>{
    let newCart = cart;
    const element = newCart?.items.find(item => item.id === arrayId);
    const newSum = element? element?.price * element?.quantity : 0;
    
    newCart?.items.splice(arrayId, 1);
    console.log("new cart",newCart);
     setCart(newCart);  
     console.log("got new cart",cart);
     SetSum(newSum)
 
}
const updateCCart =() =>{
    
  setCart(null);
   console.log("got new cart",cart);

}

  useEffect(() => {
    if(user){
    console.log("fetching cart")
    axios({
      method: 'GET',
      url:`http://localhost:8080/market/menu/cart/content/${user?.id}`,
      headers:{
      Authorization:`${token}`,
      'Access-Control-Allow-Headers': 'Content-Type,Authorization',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
      'Access-Control-Allow-Credentials': true,
      'origin': 'https://localhost:3000'
        },
    }).then(({data}:{data:CartView} ) =>{
      console.log(data);
      if(data){   
          setCart(data);
          SetSum(data.sum)
          
      }
      
    }).catch((error) =>{console.log(error.message);});
  }
  }, [token, user/*, refresh*/]);
 
  useEffect(() => {console.log("empty effect");})
  console.log("rendering"+ cart)
  const handleError = (errorMessage: string) => {
    if (errorMessage === 'email has already been taken') {
      setError('Email is already in use.');
    } else {
      setError(errorMessage);
    
    }
  };

  const makePurchase =() =>{
    console.log(user)
    if (user && user.balance && cart?.sum && user.balance > cart.sum && cart.sum !== 0){
      axios({
        method: 'GET',
        url:`http://localhost:8080/market/menu/cart/make-order`,
        headers:{
        Authorization:`${token}`,
        'Access-Control-Allow-Headers': 'Content-Type,Authorization',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
        'Access-Control-Allow-Credentials': true,
       
          },
          params:{userid: user.id}
      }).then(({data} ) =>{
        console.log(data);
        if(data.success){   
          let newCart = cart;
          newCart?.items.splice(0);
           setCart(newCart);  
           console.log("got new cart",cart);
          
           if(user && user.balance ){
           setMe({id: user.id, email: user.email, nickname: user.nickname, balance: user.balance - sum, roles: user.roles});
           }
           SetSum(0);
        }
        else{
            handleError("items qunatity is invalid,please refresh page")
        }
        
      }).catch((error) =>{console.log(error.message);});
    }
    
  }
  
  return (
    <InfinitePaperLayout>
      <>
        <Group
          noWrap
          position="center"
          px="lg"
          py="md"
          spacing="md"
          sx={{
            backgroundColor: '#495057',
            borderTopLeftRadius: '20px',
            borderTopRightRadius: '20px',
          }}
          w="100%"
        >
      
          <Title>Cart</Title>
        </Group>

        <ScrollArea h="80%" w="80%">
        {cart?<Stack w="100%">
          {cart.items.map((item, index) => (
              <CartItemForm index={index} 
              //delete={deleteItemFromCart} 
              updateCart={updateCart}
              cartItem={item} 
              token={token} 
              isHistory={false}
              />
             
            ))}
           
          </Stack>: <Text>no item</Text>}
          
        </ScrollArea>
         <Group >
            <Text>{` Total sum: ${sum} $`}</Text>
            <Button disabled={sum > 0? false: true} onClick={()=> makePurchase()}>buy</Button>
            
          </Group>
          {error ? <Text color="red">{error}</Text> : <Space h="sm" />}
          <Space h="md"/>
        {/* <CreateShopModal
          opened={createCampaignOpened}
          setOpened={setCreateCampaignOpened}
        /> */}
      </>
    </InfinitePaperLayout>
  );
}

export default Cart;
