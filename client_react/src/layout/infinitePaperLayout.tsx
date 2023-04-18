import {
  ActionIcon,
  Box,
  Button,
  Center,
  Group,
  Stack,
  Tooltip,
} from '@mantine/core';

import { IconBusinessplan, IconSearch } from '@tabler/icons-react';
import React, { useMemo } from 'react';
import Background from '../components/bachround/Background';
import UserProfileCard from '../components/other/userProfileCard';
import { useSignOut } from 'react-auth-kit';
import LogoSvg from '../components/other/logoSvg';
import { useNavigate } from 'react-router-dom';
import useAuthStore, { useCurrentUser } from '../components/store/authStore';


const InfinitePaperLayout = ({ children }: { children: JSX.Element }) => {
  //const [me, setMe] = useState<User | null>();
  const me = useCurrentUser();
  const zLogout = useAuthStore((state) => state.logOut);
  
  const user = useCurrentUser();
  //console.log("zustand",user)
  const navigate = useNavigate();
  const logOut = useSignOut();
  const signOut =() =>{
    zLogout();
    logOut();
    navigate("/auth");
}

const selllerAccess = useMemo(
  () => user && user.roles.includes("seller") ,
  [user]
);
  return (
    <Stack
      sx={{
        margin: 0,
        maxHeight: '100vh',
        maxWidth: '100vw',
        overflowX: 'hidden',
        overflowY: 'hidden',
        padding: 0,
      }}
    >
      <Background />
      <Group
        pl="sm"
        position="apart"
        pr="xl"
        sx={{ backgroundColor: '#adb5bd', width: '100%', zIndex: 10 }}
      >
        <Box sx={{cursor:'pointer'}} onClick={()=> navigate("/main")}>
          <LogoSvg size="100px" />
        </Box>

        <Group>
        {selllerAccess && <Tooltip label="my shop">
            <ActionIcon
              // component="a"
              // href="/main"
              size={60}
              variant="filled"
              onClick={() => navigate("/shop")}
            >
              <IconBusinessplan />
            </ActionIcon>
          </Tooltip>}
          <Tooltip label="Search items">
            <ActionIcon
              // component="a"
              // href="/main"
              size={60}
              variant="filled"
              onClick={() => navigate("/main")}
            >
              <IconSearch />
            </ActionIcon>
          </Tooltip>

          {me ? (
            <UserProfileCard logOut={signOut} me={me} />
          ) : (
            <Button color="gray" component="a" href="/auth" size="xl">
              Log in
            </Button>
          )}
        </Group>
      </Group>

      <Center>
        <Stack
          align="center"
          justify="flex-start"
          sx={{
            backgroundColor: '#F8F9FA',
            borderTopLeftRadius: '20px',
            borderTopRightRadius: '20px',
            maxHeight: '100vh',
            width: '60%',
          }}
        >
          {children}
        </Stack>
      </Center>
    </Stack>
  );
};

export default InfinitePaperLayout;
