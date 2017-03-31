import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BaHportalEntryModule } from './entry/entry.module';
import { BaHportalTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BaHportalEntryModule,
        BaHportalTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BaHportalEntityModule {}
