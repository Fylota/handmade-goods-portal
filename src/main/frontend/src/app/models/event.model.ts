export class Event {
    constructor(
        public id: string,
        public title: string,
        public description: string,
        public creationDate: Date,
        public startDateTime: Date,
        public endDateTime: Date
    ) { }
}