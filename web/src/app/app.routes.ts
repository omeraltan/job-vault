import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./home/home.page').then((m) => m.HomePage),
  },
  {
    path: 'home-filter',
    loadComponent: () => import('./home-filter/home-filter.page').then((m) => m.HomeFilterPage),
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
];

