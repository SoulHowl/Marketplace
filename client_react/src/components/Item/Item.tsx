import { Card, Group, ScrollArea, Stack, Text } from "@mantine/core";
import React from "react";
import { Item } from "../util/Types";
import { useNavigate } from "react-router-dom";




const ItemCard = ({
  item, isMain,
}: {
  item: Item;
  isMain: boolean;
}) => {

  const navigate = useNavigate();
  return(
    <Card shadow="sm" padding="lg" radius="md" withBorder sx={{
      backgroundColor:"#FFE0FE",
      cursor: `${isMain ? 'pointer' : null}`,}}
      onClick={() => navigate(`/main/${item.id}`)}>
    
      <Stack>
      <Text>{item.title}</Text>
      <Group>
      <Text>{item.category}</Text>
        <Text>{item.platform}</Text>
        </Group>
        <Group>
        <Text>{`Price: ${item.price}`}</Text>
        <Text>{`Quantity: ${item.quantity}`}</Text>
        </Group>
      <ScrollArea>
          {item.description}
      </ScrollArea>
      </Stack>
      {/*there will be modal for item editing*/ }
    </Card>
  );
}


export default ItemCard;