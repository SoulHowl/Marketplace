import {
  Box,
  Button,
  Group,
  Popover,
  Rating as MantineRating,
  Stack,
  Text,
} from '@mantine/core';

import isNumber from 'lodash/isNumber';
import React, { useEffect, useState } from 'react';
import { useAuthHeader, useAuthUser } from 'react-auth-kit';
import OverallScoreIndicator from './ScoreIndicator';
import isNil from 'lodash/isNil';
import axios from 'axios';
import { useCurrentUser } from '../store/authStore';
import { Ratings } from '../util/Types';


type Props = {
  shopId: number;
  shopScore?: string;
  userScore?: string;
};

const Rating: React.FC<Props> = ({ shopScore, userScore, shopId }) => {
  const user = useCurrentUser();
  const token = useAuthHeader()();
  const [myRating, setMyRating] = useState<
    number | null | undefined
  >(userScore !== "N/A" ? Number(userScore) : undefined);


  const [shopRating, setShopRating] = useState<
    number | null | undefined
  >(shopScore !== "N/A" ? Number(shopScore) : undefined);


  const handleUserRatingChange = async () => {
    if (!isNil(myRating) && isNumber(myRating) && user)  {
      await axios({
        method: 'POST',
        url:`http://localhost:8080/market/main/shop_rating/set`,
        headers:{
        Authorization:`${token}`,
        'Access-Control-Allow-Headers': 'Content-Type,Authorization',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST, PUT, PATCH, GET, DELETE, OPTIONS',
        'Access-Control-Allow-Credentials': true,
          },
        params:{
          userid:user.id,
          myrating: myRating,
          shopid: shopId,
        },
      }).then(({data}:{data:Ratings} ) =>{
        console.log(data);
        if(data){   
          setMyRating(data.userRating !== "N/A" ? Number(data.userRating) : 0);
          setShopRating(data.shopRating !== "N/A" ? Number(data.shopRating) : 0);
        }
        
      }).catch((error) =>{console.log(error.message);});
    }
  };

  useEffect(() => {
    setShopRating(Number(shopScore));
    setMyRating(Number(userScore))
  }, [shopScore, userScore, shopId]);

  
  return (
    <Group align="center">
      <OverallScoreIndicator
        score={shopRating ?  shopRating : 0}
        size="168px"
      />
      <Stack justify="center" spacing="xl">
        <Box
          sx={{
            height: '30px',
            width: '100%',
          }}
        >
          <Text>Shop Rating</Text>
          <MantineRating
            count={10}
            fractions={10}
            readOnly
            size="lg"
            value={shopRating ? shopRating: 0}
          />
        </Box>
        <Box
          sx={{
            height: '30px',
            width: '100%',
          }}
        >
       
        </Box>

        <Popover position="left-end" shadow="md" width={300} withArrow>
          <Popover.Target>
            {user ? (
              <Button
                compact
                mt="sm"
                radius="lg"
                size="md"
                sx={{
                  ':hover': { backgroundColor: '#212529' },
                  backgroundColor: '#495057',
                  width: '100%',
                }}
              >
                Set my rating
              </Button>
            ) : (
              <Text align="center" mt="xs">
                Log in to score campaign!
              </Text>
            )}
          </Popover.Target>
          <Popover.Dropdown>
            <Text size="sm">Set your rating and save it </Text>
            <Stack justify="flex-start" spacing="xl">
              <Box
                sx={{
                  height: '30px',
                  width: '100%',
                }}
              >
                <Text>My Rating</Text>
                <MantineRating
                  count={10}
                  fractions={1}
                  onChange={setMyRating}
                  size="lg"
                  value={myRating as number | undefined}
                />
              </Box>
              <Button onClick={handleUserRatingChange}>Save</Button>
            </Stack>
          </Popover.Dropdown>
        </Popover>
      </Stack>
    </Group>
  );
};

export default Rating;
