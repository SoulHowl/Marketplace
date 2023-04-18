import {
  ActionIcon,
  Checkbox,
  Group,
  Loader,
  ScrollArea,
  Select,
  SimpleGrid,
  Space,
  Text,
  Stack,
  Card,
  Box,
} from '@mantine/core';

import { IconPlus } from '@tabler/icons';

import React, { useEffect, useRef, useState } from 'react';
import InfinitePaperLayout from '../layout/infinitePaperLayout';
import SearchField from '../components/other/SearchField';
import axios from 'axios';
import {useAuthUser, useAuthHeader, withAuthUser} from 'react-auth-kit';
import { Item, ItemFeed, Shop } from '../components/util/Types';
import ItemCard from '../components/Item/Item';
import { API_TOKEN } from '../components/util/Constants';
import { useCurrentUser } from '../components/store/authStore';
import ItemCreator from '../components/Item/ItemCreator';
import { useDisclosure } from '@mantine/hooks';
import ItemCreatorModal from '../components/Item/ItemCreatorModal';


const ShopPage=()=> {

  const  token = useAuthHeader()(); 
  const [items, setItems] = useState<Array<Item>>([])
  const me = useCurrentUser();
  const [shop, setShop] = useState<string|null>("");
  const [itemCreatorOpened, setItemCreatorOpened] = useState(false);
  //const [itemCreatorOpened, { open:setItemCreatorOpened, close:setItemCreatorClosed }] = useDisclosure(false);
  
  useEffect(() => {
      if(me){
      axios({
        method: 'GET',
        url:`http://localhost:8080/market/shop/${me?.id}/items`,
        headers:{
        Authorization:`${token}`,
        'Access-Control-Allow-Headers': 'Content-Type,Authorization',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
        'Access-Control-Allow-Credentials': true,
        'origin': 'https://localhost:3000'
          },
        params:{

        },
        
      //responseType:"json",
      
      }).then(({data}:{data:Shop} ) =>{
        console.log(data);
        if(data){   

          if(data.items)
          setItems(data.items);
        }
        setShop(data.name);
        
      }).catch((error) =>{console.log(error.message);});
    
      }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [me]);



  return (
    <InfinitePaperLayout>
      <>
        <Group
          noWrap
          position="apart"
          px="lg"
          py="md"
          spacing="md"
          sx={{
            backgroundColor: '#868e96',
            borderTopLeftRadius: '20px',
            borderTopRightRadius: '20px',
          }}
          w="100%"
        >
          <Text>{`Shop shop shop- $`}</Text>
          <Text>{`Shop : ${shop}`}</Text>
        </Group>

        <ScrollArea
          sx={{ height: '75vh', width: '96%' }}
          >
          <SimpleGrid cols={3} spacing="sm" verticalSpacing="lg">
          {items.map((item) => (
              <ItemCard item={item} isMain={true}/>
            ))}
            {/* <ItemCreator shop={""} token={''} opened={itemCreatorOpened} setOpened={setItemCreatorOpened} /> */}
            
            <Card shadow="sm" padding="lg" radius="md" withBorder sx={{
                backgroundColor:"#FFE0FE",
                cursor: 'pointer'}}
                onClick={() =>setItemCreatorOpened(true)}
             >
              <Box sx={{cursor:'pointer'}} >
                    Add new Item
              </Box> 
 
            </Card>
          </SimpleGrid>
          <Space h="md"/>
         
        </ScrollArea>

         <ItemCreatorModal
          shop={shop? shop:""}
          opened={itemCreatorOpened}
          setOpened={setItemCreatorOpened}
          //setClosed={setItemCreatorClosed}
          token={token}
          items={items}
          setItems={setItems}
        />
      </>
    </InfinitePaperLayout>
  );
}

export default ShopPage;
