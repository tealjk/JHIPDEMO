import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BoozAllenStaffingPortalEntryModule } from './entry/entry.module';
import { BoozAllenStaffingPortalTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BoozAllenStaffingPortalEntryModule,
        BoozAllenStaffingPortalTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BoozAllenStaffingPortalEntityModule {}
