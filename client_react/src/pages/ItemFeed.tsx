import {
  ActionIcon,
  Checkbox,
  Group,
  Loader,
  ScrollArea,
  Select,
  SimpleGrid,
  Space,
  Stack,
} from '@mantine/core';

import { IconPlus } from '@tabler/icons';

import React, { useEffect, useRef, useState } from 'react';
import InfinitePaperLayout from '../layout/infinitePaperLayout';
import SearchField from '../components/other/SearchField';
import axios from 'axios';
import {useAuthUser, useAuthHeader, withAuthUser} from 'react-auth-kit';
import { Item, ItemFeed } from '../components/util/Types';
import ItemCard from '../components/Item/Item';
import { API_TOKEN } from '../components/util/Constants';


const ItemsFeed=()=> {

  const  token = useAuthHeader()(); 
  const [items, setItems] = useState<Array<Item>>([])
  const [search, setSearch] = useState('');

  const [page, setPage] = useState(0);
  const [per, setPer] = useState(3);
  
  const [category, setCategory] = useState<string | null>('');
  const platfromOnChange=(value: string)=>{
      setPlatform(value);
      setFetching(true);
  }
  const [platform, setPlatform] = useState<string | null>('');
  const categoryOnChange=(value: string)=>{
    setCategory(value);
    setFetching(true);
}

  const [fetching, setFetching] = useState(true);
 
  const [scrollPosition, onScrollPositionChange] = useState({ x: 0, y: 0 });
  const viewport = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (
      viewport.current &&
      scrollPosition.y > viewport.current.scrollHeight - 1000 &&
      !fetching 
    ) {
      setPer(per + 3);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [scrollPosition, viewport.current]);
  
  useEffect(() => {
    console.log("fetching ", fetching);
    console.log("searching for: ",search)
    
    if (fetching) {
      axios({
        method: 'GET',
        url:`http://localhost:8080/market/main`,
        headers:{
        Authorization:`${token}`,
        'Access-Control-Allow-Headers': 'Content-Type,Authorization',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
        'Access-Control-Allow-Credentials': true,
        'origin': 'https://localhost:3000'
          },
        params:{
          title:search,
          platform: platform,
          category: category,
          pageNo: page,
          pageSize: per
        },
        
      //responseType:"json",
      
      }).then(({data}:{data:ItemFeed} ) =>{
        console.log(data);
        if(data.last){   

        setFetching(false);
        }
        setItems(data.items);
      }).catch((error) =>{console.log(error.message);});
    
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [search, platform, category, per, page]);



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
      
          <SearchField setValue={setSearch} refreshFetching={setFetching}/>
          
          <Select  label="Platfrom" value={platform} onChange={platfromOnChange} data={[
             { value: '', label: 'Any' },
             { value: 'Steam', label: 'Steam' },
             { value: 'Origin', label: 'Origin' },
             { value: 'Uplay', label: 'Uplay' },
             { value: 'GoG', label: 'GoG' },
          ]} />
          <Select  label="Platfrom" value={category} onChange={categoryOnChange} data={[ 
            { value: '', label: 'Any' },
            { value: 'Gift card', label: 'Gift' },
            { value: 'Game', label: 'Game' },
            { value: 'In-ngame money', label: 'Ingame money' },
          ]} />
        
        </Group>

        <ScrollArea
          onScrollPositionChange={onScrollPositionChange}
          sx={{ height: '75vh', width: '96%' }}
          viewportRef={viewport}
        >
          <SimpleGrid cols={3} spacing="sm" verticalSpacing="lg">
          {items.map((item) => (
              <ItemCard item={item} isMain={true}/>
            ))}
            
          </SimpleGrid>
          <Space h="md"/>
          {fetching && <Group position="center" w="100%"><Loader color="dark" size="xl" variant="dots" /></Group>}
        </ScrollArea>

        {/* <CreateShopModal
          opened={createCampaignOpened}
          setOpened={setCreateCampaignOpened}
        /> */}
      </>
    </InfinitePaperLayout>
  );
}

export default ItemsFeed;
