import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BahStaffingPortalSharedModule } from '../../shared';

import {
    EntryService,
    EntryPopupService,
    EntryComponent,
    EntryDetailComponent,
    EntryDialogComponent,
    EntryPopupComponent,
    EntryDeletePopupComponent,
    EntryDeleteDialogComponent,
    entryRoute,
    entryPopupRoute,
} from './';

let ENTITY_STATES = [
    ...entryRoute,
    ...entryPopupRoute,
];

@NgModule({
    imports: [
        BahStaffingPortalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EntryComponent,
        EntryDetailComponent,
        EntryDialogComponent,
        EntryDeleteDialogComponent,
        EntryPopupComponent,
        EntryDeletePopupComponent,
    ],
    entryComponents: [
        EntryComponent,
        EntryDialogComponent,
        EntryPopupComponent,
        EntryDeleteDialogComponent,
        EntryDeletePopupComponent,
    ],
    providers: [
        EntryService,
        EntryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BahStaffingPortalEntryModule {}
