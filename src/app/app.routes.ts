import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'notifications',
    loadComponent: () => import('./features/notifications/notification-config.component').then(m => m.NotificationConfigComponent)
  },
  {
    path: 'processus',
    children: [
      {
        path: '',
        loadComponent: () => import('./features/processus/processus-list.component').then(m => m.ProcessusListComponent)
      },
      {
        path: 'nouveau',
        loadComponent: () => import('./features/processus/processus-form.component').then(m => m.ProcessusFormComponent)
      },
      {
        path: 'traitement',
        loadComponent: () => import('./features/processus/processus-traitement.component').then(m => m.ProcessusTraitementComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./features/processus/processus-detail.component').then(m => m.ProcessusDetailComponent)
      }
    ]
  },
  {
    path: 'import',
    loadComponent: () => import('./features/import/import-excel.component').then(m => m.ImportExcelComponent)
  },
  {
    path: 'consultation',
    loadComponent: () => import('./features/consultation/consultation.component').then(m => m.ConsultationComponent)
  },
  {
    path: 'rapports',
    loadComponent: () => import('./features/rapports/rapports.component').then(m => m.RapportsComponent)
  },
  {
    path: 'utilisateurs',
    loadComponent: () => import('./features/utilisateurs/utilisateurs.component').then(m => m.UtilisateursComponent)
  },
  {
    path: '**',
    redirectTo: '/dashboard'
  }
];