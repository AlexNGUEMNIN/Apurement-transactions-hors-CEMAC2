import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, TrendingUp, TrendingDown, Clock, CheckCircle, AlertTriangle, FileText, Users, DollarSign } from 'lucide-angular';

interface KPI {
  label: string;
  value: string;
  change: string;
  trend: 'up' | 'down' | 'stable';
  icon: any;
  color: string;
}

interface RecentActivity {
  id: string;
  type: string;
  description: string;
  timestamp: Date;
  status: 'success' | 'warning' | 'error' | 'info';
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  template: `
    <div class="space-y-6 animate-fade-in">
      <!-- Header -->
      <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Tableau de bord</h1>
          <p class="mt-1 text-sm text-gray-500">Vue d'ensemble de la plateforme d'apurement</p>
        </div>
        <div class="mt-4 sm:mt-0">
          <span class="text-sm text-gray-500">Dernière mise à jour: {{ lastUpdate | date:'short' }}</span>
        </div>
      </div>

      <!-- KPI Cards -->
      <div class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
        <div *ngFor="let kpi of kpis; trackBy: trackByKpi" 
             class="card hover:shadow-md transition-shadow duration-200">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div [class]="'w-8 h-8 rounded-md flex items-center justify-center ' + kpi.color">
                <lucide-angular [img]="kpi.icon" class="w-5 h-5 text-white"></lucide-angular>
              </div>
            </div>
            <div class="ml-5 w-0 flex-1">
              <dl>
                <dt class="text-sm font-medium text-gray-500 truncate">{{ kpi.label }}</dt>
                <dd class="flex items-baseline">
                  <div class="text-2xl font-semibold text-gray-900">{{ kpi.value }}</div>
                  <div class="ml-2 flex items-baseline text-sm font-semibold"
                       [class.text-green-600]="kpi.trend === 'up'"
                       [class.text-red-600]="kpi.trend === 'down'"
                       [class.text-gray-500]="kpi.trend === 'stable'">
                    <lucide-angular *ngIf="kpi.trend === 'up'" [img]="TrendingUp" class="w-4 h-4 mr-1"></lucide-angular>
                    <lucide-angular *ngIf="kpi.trend === 'down'" [img]="TrendingDown" class="w-4 h-4 mr-1"></lucide-angular>
                    {{ kpi.change }}
                  </div>
                </dd>
              </dl>
            </div>
          </div>
        </div>
      </div>

      <!-- Charts and Analytics -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <!-- Évolution des apurements -->
        <div class="card">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-medium text-gray-900">Évolution des apurements</h3>
            <select class="text-sm border-gray-300 rounded-md focus:ring-afriland-500 focus:border-afriland-500">
              <option>7 derniers jours</option>
              <option>30 derniers jours</option>
              <option>3 derniers mois</option>
            </select>
          </div>
          <div class="h-64 flex items-center justify-center bg-gray-50 rounded-lg">
            <div class="text-center">
              <div class="skeleton w-full h-48 mb-4"></div>
              <p class="text-sm text-gray-500">Graphique en cours de chargement...</p>
            </div>
          </div>
        </div>

        <!-- Répartition par statut -->
        <div class="card">
          <h3 class="text-lg font-medium text-gray-900 mb-4">Répartition par statut</h3>
          <div class="space-y-4">
            <div *ngFor="let status of statusDistribution" class="flex items-center justify-between">
              <div class="flex items-center">
                <div [class]="'w-3 h-3 rounded-full mr-3 ' + status.color"></div>
                <span class="text-sm font-medium text-gray-700">{{ status.label }}</span>
              </div>
              <div class="flex items-center space-x-2">
                <span class="text-sm text-gray-900">{{ status.count }}</span>
                <div class="w-20 bg-gray-200 rounded-full h-2">
                  <div [class]="'h-2 rounded-full ' + status.color" 
                       [style.width.%]="status.percentage"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Recent Activity and Quick Actions -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Recent Activity -->
        <div class="lg:col-span-2 card">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-medium text-gray-900">Activité récente</h3>
            <a routerLink="/processus" class="text-sm text-afriland-600 hover:text-afriland-700 font-medium">
              Voir tout
            </a>
          </div>
          <div class="space-y-4">
            <div *ngFor="let activity of recentActivities; trackBy: trackByActivity" 
                 class="flex items-start space-x-3 p-3 rounded-lg hover:bg-gray-50 transition-colors duration-150">
              <div class="flex-shrink-0 mt-1">
                <div [ngSwitch]="activity.status" class="w-6 h-6">
                  <lucide-angular *ngSwitchCase="'success'" [img]="CheckCircle" class="text-green-500"></lucide-angular>
                  <lucide-angular *ngSwitchCase="'warning'" [img]="AlertTriangle" class="text-yellow-500"></lucide-angular>
                  <lucide-angular *ngSwitchCase="'error'" [img]="AlertTriangle" class="text-red-500"></lucide-angular>
                  <lucide-angular *ngSwitchDefault [img]="Clock" class="text-blue-500"></lucide-angular>
                </div>
              </div>
              <div class="flex-1 min-w-0">
                <p class="text-sm font-medium text-gray-900">{{ activity.type }}</p>
                <p class="text-sm text-gray-600">{{ activity.description }}</p>
                <p class="text-xs text-gray-400 mt-1">{{ formatTimestamp(activity.timestamp) }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="card">
          <h3 class="text-lg font-medium text-gray-900 mb-4">Actions rapides</h3>
          <div class="space-y-3">
            <a routerLink="/processus/nouveau" 
               class="block w-full btn-primary text-center">
              Nouveau processus
            </a>
            <a routerLink="/import" 
               class="block w-full btn-secondary text-center">
              Importer des données
            </a>
            <a routerLink="/rapports" 
               class="block w-full btn-secondary text-center">
              Générer un rapport
            </a>
            <a routerLink="/consultation" 
               class="block w-full btn-secondary text-center">
              Rechercher
            </a>
          </div>
          
          <!-- Alerts -->
          <div class="mt-6 p-4 bg-yellow-50 border border-yellow-200 rounded-lg">
            <div class="flex">
              <lucide-angular [img]="AlertTriangle" class="w-5 h-5 text-yellow-400"></lucide-angular>
              <div class="ml-3">
                <h4 class="text-sm font-medium text-yellow-800">Attention</h4>
                <p class="text-sm text-yellow-700 mt-1">
                  {{ pendingProcessCount }} processus en attente de traitement
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    :host {
      display: block;
    }
  `]
})
export class DashboardComponent implements OnInit {
  lastUpdate = new Date();
  pendingProcessCount = 12;
  
  // Icons
  TrendingUp = TrendingUp;
  TrendingDown = TrendingDown;
  Clock = Clock;
  CheckCircle = CheckCircle;
  AlertTriangle = AlertTriangle;
  FileText = FileText;
  Users = Users;
  DollarSign = DollarSign;

  kpis: KPI[] = [
    {
      label: 'Exposition totale',
      value: '2.4B XAF',
      change: '+12.5%',
      trend: 'up',
      icon: this.DollarSign,
      color: 'bg-green-500'
    },
    {
      label: 'Taux de conformité',
      value: '87.3%',
      change: '+2.1%',
      trend: 'up',
      icon: this.CheckCircle,
      color: 'bg-blue-500'
    },
    {
      label: 'Processus en cours',
      value: '156',
      change: '-5.2%',
      trend: 'down',
      icon: this.FileText,
      color: 'bg-yellow-500'
    },
    {
      label: 'Délai moyen',
      value: '4.2j',
      change: '-0.8j',
      trend: 'up',
      icon: this.Clock,
      color: 'bg-purple-500'
    }
  ];

  statusDistribution = [
    { label: 'Validés', count: 245, percentage: 45, color: 'bg-green-500' },
    { label: 'En attente', count: 156, percentage: 28, color: 'bg-yellow-500' },
    { label: 'À modifier', count: 89, percentage: 16, color: 'bg-orange-500' },
    { label: 'Rejetés', count: 34, percentage: 6, color: 'bg-red-500' },
    { label: 'Clôturés', count: 28, percentage: 5, color: 'bg-gray-500' }
  ];

  recentActivities: RecentActivity[] = [
    {
      id: '1',
      type: 'Processus validé',
      description: 'Le processus #12345 a été validé par l\'agent central',
      timestamp: new Date(Date.now() - 5 * 60 * 1000),
      status: 'success'
    },
    {
      id: '2',
      type: 'Nouveau processus',
      description: 'Processus #12346 soumis par l\'agence Douala',
      timestamp: new Date(Date.now() - 15 * 60 * 1000),
      status: 'info'
    },
    {
      id: '3',
      type: 'Délai dépassé',
      description: 'Le processus #12340 a dépassé le délai de traitement',
      timestamp: new Date(Date.now() - 30 * 60 * 1000),
      status: 'warning'
    },
    {
      id: '4',
      type: 'Import terminé',
      description: 'Import de 1,250 transactions depuis Excel',
      timestamp: new Date(Date.now() - 45 * 60 * 1000),
      status: 'success'
    }
  ];

  ngOnInit() {
    this.loadDashboardData();
  }

  trackByKpi(index: number, kpi: KPI): string {
    return kpi.label;
  }

  trackByActivity(index: number, activity: RecentActivity): string {
    return activity.id;
  }

  formatTimestamp(timestamp: Date): string {
    const now = new Date();
    const diff = now.getTime() - timestamp.getTime();
    const minutes = Math.floor(diff / (1000 * 60));
    const hours = Math.floor(diff / (1000 * 60 * 60));

    if (minutes < 1) return 'À l\'instant';
    if (minutes < 60) return `Il y a ${minutes} min`;
    return `Il y a ${hours}h`;
  }

  private loadDashboardData() {
    // Simulate data loading
    setTimeout(() => {
      this.lastUpdate = new Date();
    }, 1000);
  }
}