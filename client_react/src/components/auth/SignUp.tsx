import { Button, Flex, Space, Stack, Text, TextInput } from '@mantine/core';
import { useForm } from '@mantine/form';
import { IconAt, IconClipboardText, IconPassword } from '@tabler/icons-react';
import React, { useState } from 'react';
import { useSignIn } from 'react-auth-kit';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../store/authStore';
import axios, { AxiosError } from 'axios';

const SignUp=()=> {

  const signIn = useSignIn();
  const navigate = useNavigate();
  const setUser = useAuthStore((state) => state.setMe);
  
  const [error, setError] = useState<string | null>(null);
  const { getInputProps, onSubmit } = useForm({
    initialValues: {
      email: '',
      nickname: '',
      password: '',
      passwordConfirmation: '',
    },
    validate: {
      email: (value) =>
        /^\S+@\S+$/.test(value) ? null : 'Must be a valid email address',
      password: (value) =>
        value.length >= 4
          ? null
          : 'Your password must be at least 8 characters.',
      passwordConfirmation: (value, formValues) =>
        value !== formValues.password
          ? 'Your password must match in both fields'
          : null,
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
    email,
    nickname,
    password,
  }: {
    email: string;
    nickname: string;
    password: string;
  }) => {
    try{
     const res = await axios.post(
      "http://localhost:8080/auth/sign-up",
      {
        email:email,
        nickname:nickname,
        password:password,

      }, );
      if(res.data.user){
          console.log(res.data)
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
  };
  }
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
          label="Email"
          labelProps={{ style: { paddingLeft: '4px' } }}
          name="email"
          {...getInputProps('email')}
          icon={<IconAt />}
          placeholder="Email"
          radius="md"
        />
        <TextInput
          autoFocus
          label="Nickname"
          labelProps={{ style: { paddingLeft: '4px' } }}
          name="nickname"
          placeholder="Nickname"
          {...getInputProps('nickname')}
          icon={<IconClipboardText />}
          radius="md"
        />
        <TextInput
          label="Password"
          labelProps={{ style: { paddingLeft: '4px' } }}
          name="password"
          placeholder="Password"
          type="password"
          {...getInputProps('password')}
          icon={<IconPassword />}
          radius="md"
        />
        <TextInput
          label="Password Confirmation"
          labelProps={{ style: { paddingLeft: '4px' } }}
          name="passwordConfirmation"
          placeholder="Password confirmation"
          type="password"
          {...getInputProps('passwordConfirmation')}
          icon={<IconPassword />}
          radius="md"
        />

        {error ? <Text color="red">{error}</Text> : <Space h="sm" />}

        <Flex
          align="center"
          direction="column"
          justify="center"
          pb="20px"
          wrap="wrap"
        >
          {/* <p className="form-line-signin"> This is sign-up form</p> */}
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

export default SignUp;
