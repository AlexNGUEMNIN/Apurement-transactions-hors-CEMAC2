import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { LucideAngularModule, Menu, X, Home, Bell, FileText, Upload, Search, BarChart3, Users, Settings } from 'lucide-angular';

interface MenuItem {
  label: string;
  route: string;
  icon: any;
  badge?: number;
  children?: MenuItem[];
}

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  template: `
    <div class="fixed inset-y-0 left-0 z-50 w-64 bg-white shadow-lg transform transition-transform duration-300 ease-in-out lg:translate-x-0"
         [class.translate-x-0]="isOpen"
         [class.-translate-x-full]="!isOpen">
      
      <!-- Header -->
      <div class="flex items-center justify-between h-16 px-6 border-b border-gray-200">
        <div class="flex items-center space-x-3">
          <div class="w-8 h-8 bg-afriland-600 rounded-lg flex items-center justify-center">
            <span class="text-white font-bold text-sm">AF</span>
          </div>
          <span class="font-semibold text-gray-900">Apurement CEMAC</span>
        </div>
        <button (click)="toggleSidebar()" class="lg:hidden p-1 rounded-md hover:bg-gray-100">
          <lucide-angular [img]="X" class="w-5 h-5"></lucide-angular>
        </button>
      </div>

      <!-- Navigation Menu -->
      <nav class="mt-6 px-3">
        <div class="space-y-1">
          <div *ngFor="let item of menuItems" class="relative">
            <a [routerLink]="item.route"
               routerLinkActive="bg-afriland-50 text-afriland-700 border-r-2 border-afriland-600"
               class="group flex items-center px-3 py-2 text-sm font-medium rounded-md text-gray-700 hover:bg-gray-50 hover:text-gray-900 transition-colors duration-200">
              <lucide-angular [img]="item.icon" class="mr-3 h-5 w-5 flex-shrink-0"></lucide-angular>
              {{ item.label }}
              <span *ngIf="item.badge" 
                    class="ml-auto inline-block py-0.5 px-2 text-xs font-medium rounded-full bg-red-100 text-red-600">
                {{ item.badge }}
              </span>
            </a>
          </div>
        </div>
      </nav>

      <!-- User Profile -->
      <div class="absolute bottom-0 left-0 right-0 p-4 border-t border-gray-200">
        <div class="flex items-center space-x-3">
          <div class="w-8 h-8 bg-gray-300 rounded-full flex items-center justify-center">
            <span class="text-gray-600 font-medium text-sm">AD</span>
          </div>
          <div class="flex-1 min-w-0">
            <p class="text-sm font-medium text-gray-900 truncate">Administrateur</p>
            <p class="text-xs text-gray-500 truncate">admin@afriland.cm</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile overlay -->
    <div *ngIf="isOpen" 
         (click)="closeSidebar()"
         class="fixed inset-0 z-40 bg-gray-600 bg-opacity-75 lg:hidden"></div>

    <!-- Mobile menu button -->
    <div class="lg:hidden fixed top-4 left-4 z-50">
      <button (click)="toggleSidebar()" 
              class="p-2 rounded-md bg-white shadow-md hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-afriland-500">
        <lucide-angular [img]="Menu" class="w-6 h-6 text-gray-600"></lucide-angular>
      </button>
    </div>
  `,
  styles: [`
    :host {
      display: block;
    }
  `]
})
export class NavigationComponent implements OnInit {
  isOpen = false;
  
  // Icons
  Menu = Menu;
  X = X;
  Home = Home;
  Bell = Bell;
  FileText = FileText;
  Upload = Upload;
  Search = Search;
  BarChart3 = BarChart3;
  Users = Users;
  Settings = Settings;

  menuItems: MenuItem[] = [
    {
      label: 'Tableau de bord',
      route: '/dashboard',
      icon: this.Home
    },
    {
      label: 'Notifications',
      route: '/notifications',
      icon: this.Bell,
      badge: 3
    },
    {
      label: 'Processus d\'apurement',
      route: '/processus',
      icon: this.FileText,
      badge: 12
    },
    {
      label: 'Import de données',
      route: '/import',
      icon: this.Upload
    },
    {
      label: 'Consultation',
      route: '/consultation',
      icon: this.Search
    },
    {
      label: 'Rapports',
      route: '/rapports',
      icon: this.BarChart3
    },
    {
      label: 'Utilisateurs',
      route: '/utilisateurs',
      icon: this.Users
    }
  ];

  constructor(private router: Router) {}

  ngOnInit() {
    // Auto-close sidebar on route change for mobile
    this.router.events.subscribe(() => {
      if (window.innerWidth < 1024) {
        this.isOpen = false;
      }
    });
  }

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

  closeSidebar() {
    this.isOpen = false;
  }
}