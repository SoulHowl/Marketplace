import { Box } from '@mantine/core';
import React from 'react';


const Background = () =>{
  return (
    <Box
      sx={{
        backgroundColor: '#e9ecef',
        height: '100%',
        position: 'absolute',
        width: '100%',
        zIndex: -1,
      }}
    />
  );
}

export default Background;
