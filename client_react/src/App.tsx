import './App.css';
import {Route, Routes } from 'react-router-dom';

import AuthPage from './pages/Auth';
import ItemFeed from './pages/ItemFeed';
import React from 'react';
import { RequireAuth } from 'react-auth-kit';
import ItemViewPage from './pages/ItemView';
import Cart from './pages/Cart';
import Balance from './pages/Balance';
import HistoryPage from './pages/History';
import ShopPage from './pages/Shop';


function App() {
  return (
    
    <Routes>
       <Route path="/" element={<RequireAuth loginPath='/auth'>< ItemFeed /></RequireAuth>}>  </Route>
       <Route path="/shop" element={<RequireAuth loginPath='/auth'>< ShopPage /></RequireAuth>}>  </Route>
      <Route path="/main" element={<RequireAuth loginPath='/auth'>< ItemFeed /></RequireAuth>}>  </Route>
      <Route path="/menu/balance" element={<RequireAuth loginPath='/auth'>< Balance /></RequireAuth>}>  </Route>
      <Route path="/main/:itemId" element={<RequireAuth loginPath='/auth'>< ItemViewPage /></RequireAuth>}>  </Route>
      <Route path="/menu/cart" element={<RequireAuth loginPath='/auth'>< Cart /></RequireAuth>}>  </Route>
      <Route path="/menu/history" element={<RequireAuth loginPath='/auth'>< HistoryPage /></RequireAuth>}>  </Route>
      <Route path="/auth" element={< AuthPage />}></Route>
    </Routes>
  );
}

export default App;
