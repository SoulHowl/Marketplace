
import {
  Button,
  Group,
  ScrollArea,
  Stack,
  Text,
} from '@mantine/core';

import React, { useEffect, useMemo, useState } from 'react';
import { useAuthHeader } from 'react-auth-kit';
import { ItemView } from '../components/util/Types';
import InfinitePaperLayout from '../layout/infinitePaperLayout';
import Rating from '../components/other/Rating';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import ItemPickModal from '../components/Item/ItemPickModal';
import { useCurrentUser } from '../components/store/authStore';


const ItemViewPage=()=> {

   const [itemModalOpened, setItemModalOpened] = useState(false);
  // const [configureGiveawayOpened, setConfigureGiveawayOpened] = useState(false);
  // const [configureEditorsOpened, setConfigureEditorsOpened] = useState(false);
  const [inCart, setInCart] = useState(false);
  const navigate = useNavigate();

  const [itemInfo, setItemInfo] = useState<ItemView | null>(null);
  const me = useCurrentUser();
  const { itemId } = useParams();
  const token = useAuthHeader();
  useEffect(() => {
   
    if(me){
    axios({
        
      method: 'GET',
      
      url:`http://localhost:8080/market/main/${itemId}`,
      headers:{
      Authorization:`${token()}`,
      'Access-Control-Allow-Headers': 'Content-Type,Authorization',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
      'Access-Control-Allow-Credentials': true,
      'origin': 'https://localhost:3000'
        },
      params:{
        userid:me.id
      },
    }).then(({data}:{data:ItemView} ) =>{
      console.log(data);
      if(data){   

      setItemInfo(data);
      setInCart(data.inCart)
      }
      
    }).catch((error) =>{console.log(error.message);});
  }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [me]);

  const selllerAccess = useMemo(
    () => me && itemInfo?.shop.sellerId === me?.id,
    [itemInfo?.shop.sellerId, me]
  );

  return (
    <InfinitePaperLayout>
      <>
          <Stack align="stretch" justify="flex-start" px="50px" py="sm">
            <Text>{itemInfo?.title}</Text>
            <Group noWrap>
                <ScrollArea scrollbarSize={2} sx={{ height: 200 }}>
                  <Text>{itemInfo?.description}</Text>
                </ScrollArea>
                <Rating
                  shopId={itemInfo?.shop.id  ? itemInfo?.shop.id : -1}
                  shopScore={itemInfo?.shop.score}
                  userScore={itemInfo?.shop.myScore}
                />
           
            </Group>
            <Group
              mt="xl"
              noWrap
              p="xs"
              spacing="lg"
              sx={{
                backgroundColor: `#CED4DA`,
                borderRadius: 8,
              }}
            >
           <Text>{`Left: ${itemInfo?.quantity}`}</Text>
           <Text>{`Price: ${itemInfo?.price} $`}</Text>
           {!selllerAccess ? !inCart ?
              <Button onClick={() => setItemModalOpened(true)}>buy</Button>
                : 
              <Button onClick={() => navigate("/menu/cart")}>view cart</Button> 
              : 
            <Button onClick={() => navigate("/shop")}>view shop</Button>}
              
            </Group>
          </Stack>

        <ItemPickModal
          item={itemInfo}
          opened={itemModalOpened}
          setOpened={setItemModalOpened}
          setCart={setInCart}
        />
        
      </>
    </InfinitePaperLayout>
  );
}

export default ItemViewPage;
