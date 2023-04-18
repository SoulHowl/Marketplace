import {
  Accordion,
  Button,
  Card,
  Group,
  Loader,
  NumberInput,
  ScrollArea,
  Space,
  Stack,
  Text,
  Title,
} from '@mantine/core';
import { useForm } from '@mantine/form';

import { IconCurrencyDollar } from '@tabler/icons-react';
import axios from 'axios';

import React, { useEffect, useState } from 'react';
import { useAuthHeader,  } from 'react-auth-kit';
import InfinitePaperLayout from '../layout/infinitePaperLayout';
import useAuthStore, { useCurrentUser } from '../components/store/authStore';
import { User } from '../components/util/Types';
import isNil from 'lodash/isNil';

type FormProps = {
  usdAmount: number;
};


const BalancePage=()=> {
  const token = useAuthHeader()();
  const me = useCurrentUser();
  const setMe = useAuthStore((state) => state.setMe);
  console.log("balance", me)
  const { getInputProps, onSubmit } = useForm<FormProps>({
    initialValues: {
      usdAmount: 0,
    },
    validate: {
      usdAmount: (value) =>
        value <= 1 ? 'Donation must be at least 1 USD.' : null,
    },
  });
  const [user, setUser] = useState<User | null>(me);

  useEffect(() => {setUser(me);},[me])
  //const [currentBalance, setCurrentBalance] = useState<number>(user?.balance ? user.balance : 0);

  const handleSubmit = async ({ usdAmount }: FormProps) => {
    const res = await axios.put(
      "http://localhost:8080/market/menu/balance",
      null,
      {
        headers:{
          Authorization:`${token}`,
          'Access-Control-Allow-Headers': 'Content-Type,Authorization',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
          'Access-Control-Allow-Credentials': true,
          
      },
    params:{
      balance:usdAmount,
      userid: me?.id,
    }} );
    if (
     res.data && me?.balance
    ) {
     
     if(user && user.balance){
     setMe({id: user.id, email: user.email, nickname: user.nickname, balance: user.balance + usdAmount, roles: user.roles})
     }
      //me.balance = usdAmount + me.balance;
    }
  };
  
  return (
    <InfinitePaperLayout>
      { !isNil(user)?
      <>
      
        <Group position="center" pt={20} w="94%">
          <Title order={1}>{`Balance Page`}</Title>
        </Group> 
          <Space h="xl" />
          <Group position="left" pt={20} w="94%">
          <Text>{`Your balance : ${user?.balance} $`}</Text>
          </Group>
          <Group position="center" w="100%"> 
            <Stack justify="center" spacing="xl">
              <form
                onSubmit={onSubmit(handleSubmit)}
                style={{ width: '300px' }}
              >
               <Stack
                  align="center"
                  justify="center"
                  spacing="lg"
                  sx={{
                    backgroundColor: `#DEE2E6`,
                    borderRadius: 8,
                    width: '100%',
                  }}
                >
                  <Space h="sm" />
                  <Group position="center">
                    <NumberInput
                      label="Input donation ammount"
                      labelProps={{ style: { paddingLeft: '36px' } }}
                      name="donateGoalUsd"
                      rightSection={<IconCurrencyDollar />}
                      type="number"
                      withAsterisk
                      {...getInputProps('usdAmount')}
                      precision={2}
                      radius="md"
                      removeTrailingZeros
                    />
                  </Group>

                  <Button
                    
                    radius="xl"
                    sx={{
                      ':hover': { backgroundColor: '#343A40' },
                      backgroundColor: '#6C757D',
                    }}
                    type="submit"
                    w="50%"
                  >
                    Update balance
                  </Button>
                  <Space h="lg" />
                </Stack> 
              </form>
              <Space h="xl" />
            </Stack>
          </Group>

          <Space h="lg" />
        
      </>
      :<Loader color="dark" size="xl" variant="dots" />}
    </InfinitePaperLayout>
  );
}

export default BalancePage;
