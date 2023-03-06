import { OrderProduct } from "./order-product";

export class OrderCreate {
    constructor (
        public userId: string,
        public items: OrderProduct[],
        public paymentMethod: string,
        public shippingMethod: string
    ) {}
}