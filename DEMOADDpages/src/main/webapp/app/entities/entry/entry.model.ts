import { Tag } from '../tag';
export class Entry {
    constructor(
        public id?: number,
        public entrydate?: any,
        public candidatename?: string,
        public major?: string,
        public pool?: string,
        public school?: string,
        public graduation?: any,
        public availability?: string,
        public recruiter?: any,
        public information?: any,
        public tag?: Tag,
    ) {
    }
}
