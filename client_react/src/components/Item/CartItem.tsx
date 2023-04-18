import {
  ActionIcon,
  Badge,
  Button,
  Card,
  Group,
  Image,
  Loader,
  Modal,
  NumberInput,
  NumberInputHandlers,
  rem,
  Select,
  Stack,
  Text,
  Textarea,
  TextInput,
} from '@mantine/core';
import { useForm } from '@mantine/form';
import { useDebouncedState, useDisclosure } from '@mantine/hooks';

import React, { useEffect, useRef } from 'react';
import { useState } from 'react';
import { CartItem, User } from '../util/Types';
import axios from 'axios';
import isNil from 'lodash/isNil';
import { isNumber } from 'lodash';


type Props = {
  cartItem: CartItem
  token: string
  updateCart?:(value: number) => void;
  index: number
  isHistory: boolean;
};

const CartItemForm: React.FC<Props> = ({
  cartItem,
  token,
  index,
  updateCart,
  isHistory
}) => {

  const [itemQuantity, setItemQuantity] = useDebouncedState<number | ''>(0, 2000);
  const [item, setItem] = useState<CartItem | null>(null);
  const handlers = useRef<NumberInputHandlers>();
  
  useEffect(() => {
    console.log("sended item:", cartItem)
    if(!isNil(cartItem)){
    setItem(cartItem);
    setItemQuantity(cartItem.quantity);
    }
  },[]);

  
  useEffect(() => {
      console.log("updating + quantity=" + itemQuantity )
    if(itemQuantity !== 0 && itemQuantity !== item?.quantity && !isNil(item?.id) && isNumber(itemQuantity)){
      console.log(itemQuantity, token, item?.id);
      axios({
        method: 'PATCH',
        url:`http://localhost:8080/market/menu/cart/content/update`,
        
         data: {
          id:item?.id,
          quantity:itemQuantity
        },
        headers:{
          Authorization:`${token}`,
          'Access-Control-Allow-Headers': 'Content-Type,Authorization',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
          'Access-Control-Allow-Credentials': true,
          'origin': 'https://localhost:3000'
            },
      }).then(({data} ) =>{
        console.log(data);
        
      }).catch((error) =>{console.log(error.message);});
   }
  },[itemQuantity])


  const deleteItemFromCart = (itemId:number, arrayId:number) =>{
    axios({
      method: 'DELETE',
      url:`http://localhost:8080/market/menu/cart/content/${itemId}/delete`,
      headers:{
      Authorization:`${token}`,
      'Access-Control-Allow-Headers': 'Content-Type,Authorization',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
      'Access-Control-Allow-Credentials': true,
      'origin': 'https://localhost:3000'
        },
    }).then(({data}) =>{
      console.log(data);
      if(!data.success){   
        console.log("updating from child")
        if(updateCart){
        updateCart(arrayId);
        }
      }
      
    }).catch((error) =>{console.log(error.message);});
  }

  return (
    
       <Card shadow="sm" padding="lg" radius="md" withBorder>
              <Group position="apart" w="100%" sx={{}} styles={{
                
                Box:{borderWidth:rem(10), borderColor:"black"}
                }}
                >
                  <Stack>
                  <Text>{`Item: ${item?.title}`}</Text>
            
                  <Text>{`Shop: ${item?.shop}`}</Text>
                  {!isHistory && <Button onClick={()=> deleteItemFromCart(item?.id ? item.id : -1, index)}>delete</Button>}
                  </Stack>
                  <Stack>
                  {!isHistory? 
                    (itemQuantity !== 0 ?
                      <Group spacing={5}>
                        <ActionIcon size={42} variant="default" onClick={() => handlers.current?.decrement()}>
                          –
                        </ActionIcon>
                        
                        <NumberInput
                          hideControls
                          value={itemQuantity}
                          onChange={(val) => setItemQuantity(val)}
                          handlersRef={handlers}
                          max={10}
                          min={1}
                          step={1}
                          styles={{ input: { width: rem(54), textAlign: 'center' } }}
                        />

                        <ActionIcon size={42} variant="default" onClick={() => handlers.current?.increment()}>
                          +
                        </ActionIcon>
                      </Group>
                      : <Loader color="dark" size="xl" variant="dots" />) :<Text>{`Quantity:${itemQuantity}`}</Text> }
                  <Text>{`Price: ${item?.price} $`}</Text>
                  <Text>{`Total: ${item? item?.quantity * item?.price: 0} $`}</Text>
                  {item?.value ? <Text>{`Total: ${item.value } $`}</Text>:<></>}
                  </Stack>
              </Group>
              </Card>
  );
};

export default CartItemForm;
