
import { Accordion, Card, Flex, Group, Image, ScrollArea, Stack, Text, Title } from '@mantine/core';
import React, { useEffect, useState } from 'react';
import { CartView, User } from '../components/util/Types';
import {useAuthHeader, useAuthUser} from 'react-auth-kit';
import InfinitePaperLayout from '../layout/infinitePaperLayout';
import CartItem from '../components/Item/CartItem';
import { useCurrentUser } from '../components/store/authStore';
import CartItemForm from '../components/Item/CartItem';
import axios from 'axios';


const HistoryPage=()=> {
  //const me = useAuthStore((state) => state.me);
  
  const token = useAuthHeader()();
  const user= useCurrentUser();
  const [orders, serOrders] = useState<Array<CartView>>([]);

  useEffect(() =>{
    if(user){
    axios({
      method: 'GET',
      url:`http://localhost:8080/market/menu/order-history`,
      headers:{
      Authorization:`${token}`,
      'Access-Control-Allow-Headers': 'Content-Type,Authorization',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
      'Access-Control-Allow-Credentials': true,
     
        },
        params:{userid: user.id}
    }).then(({data}:{data:Array<CartView>} ) =>{
      console.log("orders:",data);
      if(data){   
        serOrders(data);
      }
      
    }).catch((error) =>{console.log(error.message);});
  }
  },[token, user])

  return (
    <InfinitePaperLayout>
      <Stack align="center" justify="center" pt={20} w="80%">
        <Title order={1}>My profile</Title>
        <Group pb="80px" position="apart" w="100%">
          <Stack justify="flex-start" spacing="xl">
            <Card p="lg" radius="md" shadow="sm" withBorder>
              <Text fz="xl">{`Email : ${user?.email}`}</Text>
            </Card>
            <Card p="lg" radius="md" shadow="sm" withBorder>
              <Text fz="xl">{`Nickname : ${user?.nickname}`}</Text>
            </Card>
            <Card p="lg" radius="md" shadow="sm" withBorder>
              <Text fz="xl">{`Balance : ${user?.balance} $`}</Text>
            </Card>    
          </Stack>

          <Flex
            align="center"
            direction="row"
            justify="center"
            sx={{
              backgroundColor: 'grey',
              borderRadius: 250,

              borderWidth: 2,
              height: '330px',
              width: '330px',
            }}
            wrap="nowrap"
          >
            <Image
              alt=""
              height="320px"
              radius={250}
              src={null}
              sx={{}}
              width="320px"
              withPlaceholder
            />
          </Flex>
        </Group>
        <ScrollArea w="100%" h="400px">
          
    
        <Accordion defaultValue="orders" w="100%" radius="xs">
        {orders.map((order, index) =>(
                <Accordion.Item value="active">
                  <Accordion.Control>
                    <Text fz={18} weight={400}>
                      {`Order Number: ${order.id} Total Sum: ${order.sum} $`}
                    </Text>
                  </Accordion.Control>
                  <Accordion.Panel>
                    <Stack>
                    {order.items.map((odItem, index) => (
                       <CartItemForm index={index} 
                       cartItem={odItem} 
                       token={""} 
                       isHistory={true}
                       />
                  
                    ))}
                    </Stack>
                  </Accordion.Panel>
                </Accordion.Item>  
       )
        )}
         </Accordion>
         </ScrollArea>
      </Stack>
    </InfinitePaperLayout>
  );
}

export default HistoryPage;
