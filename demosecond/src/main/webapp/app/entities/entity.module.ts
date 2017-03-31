import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { UnivportalEntryModule } from './entry/entry.module';
import { UnivportalTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        UnivportalEntryModule,
        UnivportalTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UnivportalEntityModule {}
