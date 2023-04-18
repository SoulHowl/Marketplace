import { Button } from '@mantine/core';
import  { useState } from 'react';
import SignIn from '../components/auth/SignIn';
import SignUp from '../components/auth/SignUp';

import SignInForm from '../components/auth/SignIn';
import Background from '../components/bachround/Background';
import React from 'react';

function AuthPage() {
  const [formType, setForm] = useState(<SignInForm />);
  const [signinStyle, setSignInStyle] = useState(true);
  const [signupStyle, setSignUpStyle] = useState(false);
  return (
    <section className="auth-container">
      <Background />

      <section className="form-section">
        <Button.Group className="switch-buts">
          <Button
            className="sign-button"
            onClick={() => {
              setForm(<SignUp />);
              setSignUpStyle(true);
              setSignInStyle(false);
            }}
            sx={
              signupStyle
                ? {
                    backgroundColor: `#730000`,
                    borderColor: `#730000`,
                  }
                : {
                    backgroundColor: `#9d0000`,
                    borderColor: `#9d0000`,
                  }
            }
            variant="filled"
          >
            Sign Up
          </Button>
          <Button
            className="register-button"
            onClick={() => {
              setForm(<SignIn />);
              setSignInStyle(true);
              setSignUpStyle(false);
            }}
            sx={
              signinStyle
                ? {
                    backgroundColor: `#730000`,
                    borderColor: `#730000`,
                  }
                : {
                    backgroundColor: `#9d0000`,
                    borderColor: `#9d0000`,
                  }
            }
            variant="filled"
          >
            Sign In
          </Button>
        </Button.Group>
        {formType}
      </section>
    </section>
  );
}

export default AuthPage;
