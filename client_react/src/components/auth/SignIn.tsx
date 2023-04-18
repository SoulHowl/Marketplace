
import { Button, Flex, Space, Stack, Text, TextInput } from '@mantine/core';
import { useForm } from '@mantine/form';
import { IconAt, IconPassword } from '@tabler/icons-react';

import { useNavigate } from "react-router-dom";
import React, { useState } from 'react';
import { useSignIn, useAuthUser } from 'react-auth-kit';
import axios, { AxiosError } from "axios";
import useAuthStore from '../store/authStore';


const SignInForm=() => {
 
  const [error, setError] = useState<string | null>(null);
  const signIn = useSignIn();
  const navigate = useNavigate();
  const setUser = useAuthStore((state) => state.setMe);

  const { getInputProps, onSubmit } = useForm({
    initialValues: {
      nickanme: '',
      password: '',
    },
  });

  const handleError = (errorMessage: string) => {
    if (errorMessage === 'email has already been taken') {
      setError('Email is already in use.');
    } else {
      setError(errorMessage);
    }
  };

  const handleSubmit = async ({
    nickanme,
    password,
  }: {
    nickanme: string;
    password: string;
  }) => {
    try{
    const res = await axios.post(
      "http://localhost:8080/auth/sign-in",
      {
        username:nickanme,
        password:password
      }, );
        
          console.log(res)
          if(res.data.user){
          setUser(res.data.user);
        signIn(
          {
            token:res.data.accessToken, 
            expiresIn:3600*24,
            tokenType:"Bearer",
            authState:{user:res.data.user},
          }
        );
          
        navigate("/main");
          }
  }
  catch(err){
    if (err && err instanceof AxiosError)
        setError(err.response?.data.message);
      else if (err && err instanceof Error) setError(err.message);
  }
  };

  return (
    <form className="form-auth" onSubmit={onSubmit(handleSubmit)}>
      <Stack
        spacing="sm"
        sx={{
          backgroundColor: 'rgba(245, 245, 245, 0.8)',
          borderRadius: '20px',
          width: '500px',
          zIndex: 5,
        }}
      >
        <TextInput
          autoFocus
          icon={<IconAt />}
          label="Nickname"
          labelProps={{ style: { paddingLeft: '4px' } }}
          placeholder="Enter your nickanme"
          {...getInputProps('nickanme')}
          name="nickanme"
        />
        <TextInput
          label="Password"
          labelProps={{ style: { paddingLeft: '4px' } }}
          placeholder="Enter password"
          type="password"
          {...getInputProps('password')}
          icon={<IconPassword />}
          name="password"
        />
        {error ? (
          <Text align="center" color="red">
            {error}
          </Text>
        ) : (
          <Space h="sm" />
        )}
        <Flex
          align="center"
          direction="column"
          justify="center"
          pb="20px"
          wrap="wrap"
        >
          <Button
            className="confirm-button"
            color={undefined}
            radius="lg"
            type="submit"
            variant="filled"
          >
            Submit
          </Button>
        </Flex>
      </Stack>
    </form>
  );
}

export default SignInForm;
