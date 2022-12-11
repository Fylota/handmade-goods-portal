export class CartProduct {
    constructor(
        public id: string,
        public userId: string,
        public productId: string,
        public quantity: number
    ) { }
}