import {
  ActionIcon,
  Button,
  Modal,
  ScrollArea,
  Space,
  Stack,
  Text,
  Textarea,
  TextInput,
} from '@mantine/core';
import { useForm } from '@mantine/form';
import { showNotification } from '@mantine/notifications';
import { IconPlus } from '@tabler/icons';
import isNil from 'lodash/isNil';
import React, { useEffect, useState } from 'react';
import { Item, ItemView } from '../util/Types';
import axios from 'axios';
import { useAuthHeader, useAuthUser } from 'react-auth-kit';
import { useCurrentUser } from '../store/authStore';


type Props = {
  item: ItemView | null;
  opened: boolean;
  setOpened: (value: boolean) => void;
  setCart: (value: boolean) => void;
};

type FormProps = {
 
  quantity: number;

}

const RedactCampaignModal: React.FC<Props> = ({
  item,
  opened,
  setOpened,
  setCart
}) => {
  const me = useCurrentUser();
  const token = useAuthHeader();

  const [itemInfo, setItemInfo] = useState<ItemView | null>(null);
 
  const { getInputProps, onSubmit, setFieldValue } = useForm<FormProps>({
    initialValues: {
      quantity: 1,
    },
    validate: {
      quantity: (value) =>
      value < (itemInfo?.quantity ? itemInfo.quantity : 0) && value > 0
          ? null
          : `cant add item`,

    },
  });

 

  const handleSubmit = async ({ quantity }: FormProps) => {
    if(me){
    await axios({
      method: 'POST',
      
      url:`http://localhost:8080/market/main/${itemInfo?.id}/add`,
      headers:{
      Authorization:`${token()}`,
      'Access-Control-Allow-Headers': 'Content-Type,Authorization',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
      'Access-Control-Allow-Credentials': true,
      'origin': 'https://localhost:3000'
        },
      params:{
        userid:me.id,
        quantity:quantity
      },
    }).then(({data}:{data:ItemView} ) =>{
      console.log(data);
      if(data){   
        setCart(true);
        setOpened(false);
      }
      
    }).catch((error) =>{console.log(error.message);});
          
  }
  };
    

    
  useEffect(() => {
   setItemInfo(item);
    
  }, [item]);



  return (
    <Modal
      onClose={() => setOpened(false)}
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
        <Text>{itemInfo?.title}</Text>
        <Text>{`Left: ${itemInfo?.quantity}`}</Text>
           <Text>{`Price: ${itemInfo?.price} $`}</Text>

          <TextInput
            label="Pick number of items"
            name="ttl"
            withAsterisk
            type="number"
            {...getInputProps('quantity')}
            onChange={(event) => {
             
              setFieldValue('quantity', event.currentTarget.value as unknown as number);
            }}
            radius="md"
            
          />
        
          

          <Button
            radius="md"
            size="lg"
            sx={{
              ':hover': { backgroundColor: '#212529' },
              backgroundColor: '#495057',
            }}
            type="submit"
          >
            Add
          </Button>
        </Stack>
      </form>
    </Modal>
  );
};

export default RedactCampaignModal;
