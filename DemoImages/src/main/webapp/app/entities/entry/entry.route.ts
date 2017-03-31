import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EntryComponent } from './entry.component';
import { EntryDetailComponent } from './entry-detail.component';
import { EntryPopupComponent } from './entry-dialog.component';
import { EntryDeletePopupComponent } from './entry-delete-dialog.component';

import { Principal } from '../../shared';


export const entryRoute: Routes = [
  {
    path: 'entry',
    component: EntryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Entries'
    }
  }, {
    path: 'entry/:id',
    component: EntryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Entries'
    }
  }
];

export const entryPopupRoute: Routes = [
  {
    path: 'entry-new',
    component: EntryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Entries'
    },
    outlet: 'popup'
  },
  {
    path: 'entry/:id/edit',
    component: EntryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Entries'
    },
    outlet: 'popup'
  },
  {
    path: 'entry/:id/delete',
    component: EntryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Entries'
    },
    outlet: 'popup'
  }
];
