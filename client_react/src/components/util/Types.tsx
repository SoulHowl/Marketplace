export type User = {
  id: number;
  nickname: string | null,
  email: string | null,
  balance: number | null,
  roles: Array<string>
}

export type Item = {
  id : number;
  shop: string;
   category : string;
  platform : string;
   title: string;
  description: string;
  quantity: number;
  price: number;
}

export type ItemFeed = {
  items:Array<Item>;
  pageNo: number;
 pageSize: number;
  totalElements: number;
  totalPages: number;
 last:Boolean;
}


export type Shop={
    id: number;
    name: string;
    score: string;
    myScore?: string;
    sellerId: number;
    items?: Array<Item>;
}

export type ItemView = {
   id: number;
  shop: Shop;
  category: string;
  platform: string;
   title: string;
   description: string;
   quantity: number;
   price: number;
   inCart: boolean;
}

export type CartItem = {
   id: number;
   shop: string;
    title: string;
    description: string;
   quantity: number;
    price: number;
     status: string;
    value: string;
}

export type CartView = {
  id: number;
  status: string;
  items: Array<CartItem> ;
   sum: number;
  datePurchase: string;
}

export type Ratings = {
  shopRating: string;
  userRating: string;
}