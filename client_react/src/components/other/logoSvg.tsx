import { Box } from '@mantine/core';
import React from 'react';
import { SVGProps } from 'react';

export default function LogoSvg({
  size,
  ...props
}: SVGProps<never> & { size: string }): JSX.Element {
  return (
    <Box sx={{ height: size, minHeight: size, minWidth: size, width: size }}>
      <svg
        fill="none"
        height="100%"
        viewBox="10 0 130 130"
        width="100%"
        xmlns="http://www.w3.org/2000/svg"
        {...props}
      >
        <path
          d="M41.0294 11.5272C41.9226 9.98021 43.5732 9.02722 45.3595 9.02722L103.64 9.02722C105.427 9.02722 107.077 9.98021 107.971 11.5272L137.111 62C138.004 63.547 138.004 65.453 137.111 67L107.971 117.473C107.077 119.02 105.427 119.973 103.64 119.973L45.3595 119.973C43.5732 119.973 41.9226 119.02 41.0294 117.473L11.8889 67C10.9958 65.453 10.9958 63.547 11.8889 62L41.0294 11.5272Z"
          fill="white"
        />
        <path
          d="M46.7842 16.0427L75.313 65.4562H18.2553L46.7842 16.0427Z"
          fill="#BF2025"
        />
        <path
          d="M102.908 16.0427L131.437 65.4562H74.3792L102.908 16.0427Z"
          fill="#BF2025"
        />
        <path
          d="M46.7842 114.87L18.2553 65.4562L75.313 65.4562L46.7842 114.87Z"
          fill="#D4D3D4"
        />
        <path
          d="M102.908 114.87L74.3792 65.4562L131.437 65.4562L102.908 114.87Z"
          fill="#D4D3D4"
        />
        <path
          d="M74.8461 64.8461L103.375 114.259H46.3172L74.8461 64.8461Z"
          fill="#BF2025"
        />
        <path
          d="M74.8461 64.8461L46.3172 16.3477L103.375 16.3477L74.8461 64.8461Z"
          fill="#D9D9D9"
        />
      </svg>
    </Box>
  );
}
