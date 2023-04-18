import './styles/background.css';
import './styles/card.css';
import './styles/confirmButton.css';
import './styles/formLogin.css';
import './styles/general.css';
import './styles/main.css';
// import '@styles/mainWindow.css';
import './styles/navbar.css';
import './styles/switchFormButtons.css';

import { BrowserRouter, } from 'react-router-dom';
import { AuthProvider } from 'react-auth-kit';
import App from './App';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { API_TOKEN } from './components/util/Constants';
import UserProvider from './layout/UserProvider';

//import ReactDOM from 'react-dom/client'

const element  = document.getElementById('root');
if(!!element){
  const root = ReactDOM.createRoot(element);
  root.render(
   
      <BrowserRouter>
    
    <AuthProvider 
      authType={"localstorage"}
      authName={API_TOKEN}
    >
      <UserProvider>
        <App/>
        </UserProvider>
        </AuthProvider>
    </BrowserRouter>
     
   
  );
}
 // <React.StrictMode>
 {/* </React.StrictMode> */}
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals

