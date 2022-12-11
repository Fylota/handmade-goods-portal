export class User {
    constructor(
        public id: string,
        public firstName: string,
        public lastName: string,
        public password: string,
        public matchingPassword: string,
        public email: string,
        public phoneNumber: string,
        public address: string,
    ) { }
}