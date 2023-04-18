import { Card, Group, ScrollArea, Stack, Text, Image, Box } from "@mantine/core";
import React, { useState } from "react";
import { Item } from "../util/Types";
import { useNavigate } from "react-router-dom";
import PlusSvg from "../other/plusSvg";
import ItemCreatorModal from "./ItemCreatorModal";

type Props = {
  shop: string;
  setOpened: (value: boolean) => void;
  opened: boolean;
  token: string;
};

const ItemCreator: React.FC<Props> = ({
  shop,
  opened,
  setOpened,
  token
}) => {
 
  console.log("creator open",opened)
  const navigate = useNavigate();
  return(
    <Card shadow="sm" padding="lg" radius="md" withBorder sx={{
      backgroundColor:"#FFE0FE",
      cursor: 'pointer'}}
      onClick={() => setOpened}>
           <Box sx={{cursor:'pointer'}} onClick={()=> navigate("/main")}>
          {/* <PlusSvg size="100px" /> */}
          Add new Item
        </Box> 
 
    </Card>

  );
}


export default ItemCreator;