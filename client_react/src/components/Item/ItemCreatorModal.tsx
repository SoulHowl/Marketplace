import {
  Button,
  Group,
  Modal,
  Select,
  Space,
  Stack,
  Text,
  Textarea,
  TextInput,
} from '@mantine/core';
import { useForm } from '@mantine/form';
import React, { useEffect, useState } from 'react';
import { Item } from '../util/Types';
import axios from 'axios';
import { IconCurrencyDollar } from '@tabler/icons-react';


type Props = {
  shop: string;
  setOpened: (value: boolean) => void;
  //setClosed: (value: boolean) => void;
  items:Array<Item>;
  setItems: (value:Array<Item>) => void;
  opened: boolean;
  token: string;
};

type FormProps = {
  title:string;
  description:string;
  platform:string;
  category:string;
  shop:string;
  quantity: number;
  price:number
}

const ItemCreatorModal: React.FC<Props> = ({
  shop,
  setOpened,
  //setClosed,
  items,
  setItems,
  opened,
  token
  
}) => {
  
  const [category, setCategory] = useState<string | null>('');
  const [shopName, setShopName] = useState<string>('');
  
  const [error, setError] = useState<string | null>(null);
  const { getInputProps, onSubmit, setFieldValue } = useForm<FormProps>({
    initialValues: {
      title: "",
      description:"",
      platform:"",
      category:"",
      shop:shop,
      quantity: 0,
      price: 0
    },
    validate: {
      quantity: (value) =>
      value > 0
          ? null
          : `cant add item`,
          price: (value) =>
      value > 0
          ? null
          : `cant add item`,
      title: (value) =>
      value.length !== 0
          ? null
          : `cant add item`,
      category: (value) =>
        value !== ""
            ? null
            : `cant add item`,
      platform: (value) =>
      value !== ""
          ? null
          : `cant add item`,
    },
  });

  const handleError = (errorMessage: string) => {
    if (errorMessage === 'email has already been taken') {
      setError('Email is already in use.');
    } else {
      setError(errorMessage);
    }
  };

  useEffect(() => {setShopName(shop)},[shop])

  const handleSubmit = async ({  
      title,
      description,
      platform,
      category,
      shop,
      quantity,
      price}: FormProps) => {
        console.log(shop)
        if(shop != null && shop.length > 0) {
    await axios({
      method: 'POST',

      url:`http://localhost:8080/market/shop/create`,
      headers:{
      Authorization:`${token}`,
      'Access-Control-Allow-Headers': 'Content-Type,Authorization',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
      'Access-Control-Allow-Credentials': true,
      
        },
      data:{
        title:title,
        description:description,
        platform:platform,
        category:category,
        shop:shop,
        quantity:quantity,
        price:price,
      },
    }).then(({data}:{data:Item} ) =>{
      console.log(data);
      if(data){   
          // add to array
          setOpened(false);
          let newItems = items;
          newItems.push(data);
          setItems(newItems);
      }
      
    }).catch((error) =>{console.log(error.message);
        handleError(error.message);});
          
    }
  };
    
  return (
    <Modal
      onClose={()=>setOpened(false)}
      opened={opened}
      size="lg"
      styles={{
        content: { backgroundColor: '#CED4DA' },
      }}
      title="Add item to cart"
      xOffset={0}
    >
      <form onSubmit={onSubmit(handleSubmit)}>
        <Stack justify="flex-start" spacing="sm">
        <TextInput
            label="Title"
            name="ttl"
            withAsterisk
            {...getInputProps('title')}
            radius="md"
            
          />
          <Group>
        <Select  label="Platfrom" data={[
             { value: '', label: 'Any' },
             { value: 'Steam', label: 'Steam' },
             { value: 'Origin', label: 'Origin' },
             { value: 'Uplay', label: 'Uplay' },
             { value: 'GoG', label: 'GoG' },
          ]} 
          {...getInputProps('platform')}/>
          <Select  label="Platfrom"  data={[ 
            { value: '', label: 'Any' },
            { value: 'Gift card', label: 'Gift' },
            { value: 'Game', label: 'Game' },
            { value: 'In-ngame money', label: 'Ingame money' },
          ]} {...getInputProps('category')}/>
          </Group>
           <Textarea
            autosize
            label="Description"
            maxRows={10}
            minRows={2}
            name="description"
            {...getInputProps('description')}
            radius="md"
          />
          <TextInput
            label="Set quantity"
            name="ttl"
            withAsterisk
            type="number"
            {...getInputProps('quantity')}
            onChange={(event) => {
             
              setFieldValue('quantity', event.currentTarget.value as unknown as number);
            }}
            radius="md"
            
          />
        <TextInput
            label="Set price"
            name="ttl"
            withAsterisk
            type="number"
            rightSection={<IconCurrencyDollar />}
            {...getInputProps('price')}
            onChange={(event) => {
             
              setFieldValue('price', event.currentTarget.value as unknown as number);
            }}
            radius="md"
            
          />
          {error ? (
          <Text align="center" color="red">
            {error}
          </Text>
        ) : (
          <Space h="sm" />
        )}

          <Button
            radius="md"
            size="lg"
            sx={{
              ':hover': { backgroundColor: '#212529' },
              backgroundColor: '#495057',
            }}
            type="submit"
          >
            Create
          </Button>
        </Stack>
      </form>
    </Modal>
  );
};

export default ItemCreatorModal;
