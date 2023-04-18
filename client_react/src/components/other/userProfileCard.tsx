import { Avatar, Group, Menu, Stack, Text, Title } from '@mantine/core';
import { IconCake, IconHistory, IconLogout, IconMoneybag, IconShoppingCart, IconUser } from '@tabler/icons-react';

import { useState } from 'react';

import React from 'react';

import { limitText } from '../util/formatters';
import { User } from '../util/Types';
import { useNavigate } from 'react-router-dom';

function UserProfileCard({
  logOut,
  me,
}: {
  logOut: () => void;
  me: User;
}) {
  //const navigate = useNavigate();
  const [opened, setOpened] = useState(false);
  const navigate = useNavigate();
  return (
    <Menu
      onChange={setOpened}
      opened={opened}
      shadow="md"
      styles={{
        dropdown: { backgroundColor: '#DEE2E6' },
        item: { fontSize: 16 },
      }}
      width={300}
    >
      <Menu.Target>
        <Group
          noWrap
          p="xs"
          sx={{
            backgroundColor: '#DEE2E6',
            borderRadius: '5px',
            width: '300px',
          }}
        >
          <Avatar radius="xl" size="lg" />
          <Stack spacing={0}>
            <Title order={3}>{limitText(me?.nickname || '', 16)}</Title>
            <Text>{limitText(me?.email || '', 25)}</Text>
          </Stack>
        </Group>
      </Menu.Target>

      <Menu.Dropdown>
        <Menu.Item
          // component="a"
          // href={`/menu/cart`}
          icon={<IconShoppingCart  />}
          onClick={() => navigate("/menu/cart")}
        >
          Cart
        </Menu.Item>
    
        <Menu.Item
          
          icon={<IconHistory  />}
          onClick={() => navigate("/menu/history")}
        >
          My history
        </Menu.Item>
        <Menu.Item

          icon={<IconMoneybag  />}
          onClick={() => navigate("/menu/balance")}
        >
          {`Balance - ${me.balance} $`}
        </Menu.Item>
        <Menu.Item icon={<IconLogout  />}  onClick={ logOut}>
          Log out
        </Menu.Item>
      </Menu.Dropdown>
    </Menu>
  );
}

export default UserProfileCard;
