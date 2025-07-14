import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, Upload, FileSpreadsheet, CheckCircle, AlertTriangle, X, Download, Eye } from 'lucide-angular';

interface ImportResult {
  total: number;
  success: number;
  errors: number;
  warnings: number;
}

interface ImportError {
  row: number;
  column: string;
  message: string;
  type: 'error' | 'warning';
}

@Component({
  selector: 'app-import-excel',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <div class="max-w-4xl mx-auto space-y-6 animate-fade-in">
      <!-- Header -->
      <div>
        <h1 class="text-2xl font-bold text-gray-900">Import de données Excel</h1>
        <p class="mt-1 text-sm text-gray-500">Importation des données historiques de transactions</p>
      </div>

      <!-- Required Columns Info -->
      <div class="card">
        <div class="flex items-center mb-4">
          <lucide-angular [img]="FileSpreadsheet" class="w-5 h-5 text-afriland-600 mr-2"></lucide-angular>
          <h2 class="text-lg font-semibold text-gray-900">Structure requise du fichier Excel</h2>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <div *ngFor="let column of requiredColumns" class="flex items-center space-x-2">
            <div class="w-2 h-2 bg-afriland-500 rounded-full"></div>
            <span class="text-sm font-medium text-gray-700">{{ column }}</span>
          </div>
        </div>
        
        <div class="mt-4 p-4 bg-blue-50 border border-blue-200 rounded-lg">
          <div class="flex">
            <lucide-angular [img]="Download" class="w-5 h-5 text-blue-400 mr-2 mt-0.5"></lucide-angular>
            <div>
              <h4 class="text-sm font-medium text-blue-800">Modèle Excel</h4>
              <p class="text-sm text-blue-700 mt-1">
                Téléchargez le modèle Excel avec la structure correcte pour éviter les erreurs d'import.
              </p>
              <button class="mt-2 text-sm text-blue-600 hover:text-blue-800 font-medium">
                Télécharger le modèle
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Upload Zone -->
      <div class="card">
        <div class="flex items-center mb-6">
          <lucide-angular [img]="Upload" class="w-5 h-5 text-afriland-600 mr-2"></lucide-angular>
          <h2 class="text-lg font-semibold text-gray-900">Upload du fichier</h2>
        </div>
        
        <div *ngIf="!uploadedFile" 
             class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-afriland-400 transition-colors duration-200"
             (dragover)="onDragOver($event)"
             (dragleave)="onDragLeave($event)"
             (drop)="onDrop($event)">
          <lucide-angular [img]="FileSpreadsheet" class="w-16 h-16 text-gray-400 mx-auto mb-4"></lucide-angular>
          <h3 class="text-lg font-medium text-gray-900 mb-2">Glissez-déposez votre fichier Excel ici</h3>
          <p class="text-sm text-gray-500 mb-4">ou</p>
          <label class="btn-primary cursor-pointer">
            Parcourir les fichiers
            <input type="file" 
                   accept=".xlsx,.xls"
                   (change)="onFileSelect($event)"
                   class="hidden">
          </label>
          <p class="text-xs text-gray-500 mt-4">Formats acceptés: .xlsx, .xls (max 50MB)</p>
        </div>

        <!-- File Preview -->
        <div *ngIf="uploadedFile && !isProcessing" class="space-y-4">
          <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
            <div class="flex items-center space-x-3">
              <lucide-angular [img]="FileSpreadsheet" class="w-8 h-8 text-green-500"></lucide-angular>
              <div>
                <p class="text-sm font-medium text-gray-900">{{ uploadedFile.name }}</p>
                <p class="text-xs text-gray-500">{{ formatFileSize(uploadedFile.size) }}</p>
              </div>
            </div>
            <button (click)="removeFile()" class="text-red-500 hover:text-red-700">
              <lucide-angular [img]="X" class="w-5 h-5"></lucide-angular>
            </button>
          </div>

          <!-- Preview Table -->
          <div *ngIf="previewData.length > 0" class="space-y-4">
            <div class="flex items-center justify-between">
              <h3 class="text-sm font-medium text-gray-900">Aperçu des données ({{ previewData.length }} lignes)</h3>
              <button (click)="togglePreview()" class="text-sm text-afriland-600 hover:text-afriland-700">
                {{ showPreview ? 'Masquer' : 'Afficher' }} l'aperçu
              </button>
            </div>
            
            <div *ngIf="showPreview" class="overflow-x-auto border border-gray-200 rounded-lg">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th *ngFor="let header of previewHeaders" 
                        class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      {{ header }}
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr *ngFor="let row of previewData.slice(0, 5)" class="hover:bg-gray-50">
                    <td *ngFor="let cell of row" class="px-4 py-2 text-sm text-gray-900 whitespace-nowrap">
                      {{ cell }}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Import Button -->
          <div class="flex justify-end space-x-4">
            <button (click)="validateFile()" class="btn-secondary">
              <lucide-angular [img]="Eye" class="w-4 h-4 mr-2"></lucide-angular>
              Valider le fichier
            </button>
            <button (click)="startImport()" 
                    [disabled]="!isFileValid"
                    class="btn-primary disabled:opacity-50 disabled:cursor-not-allowed">
              Démarrer l'import
            </button>
          </div>
        </div>

        <!-- Processing State -->
        <div *ngIf="isProcessing" class="text-center py-8">
          <div class="inline-flex items-center space-x-3">
            <svg class="animate-spin h-8 w-8 text-afriland-600" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            <div>
              <p class="text-lg font-medium text-gray-900">Import en cours...</p>
              <p class="text-sm text-gray-500">{{ processedRows }}/{{ totalRows }} lignes traitées</p>
            </div>
          </div>
          
          <!-- Progress Bar -->
          <div class="mt-4 w-full bg-gray-200 rounded-full h-2">
            <div class="bg-afriland-600 h-2 rounded-full transition-all duration-300" 
                 [style.width.%]="progressPercentage"></div>
          </div>
        </div>
      </div>

      <!-- Import Results -->
      <div *ngIf="importResult" class="card">
        <div class="flex items-center mb-6">
          <lucide-angular [img]="CheckCircle" class="w-5 h-5 text-green-600 mr-2"></lucide-angular>
          <h2 class="text-lg font-semibold text-gray-900">Résultats de l'import</h2>
        </div>
        
        <!-- Summary Cards -->
        <div class="grid grid-cols-1 sm:grid-cols-4 gap-4 mb-6">
          <div class="bg-blue-50 border border-blue-200 rounded-lg p-4">
            <div class="text-2xl font-bold text-blue-600">{{ importResult.total }}</div>
            <div class="text-sm text-blue-700">Total lignes</div>
          </div>
          <div class="bg-green-50 border border-green-200 rounded-lg p-4">
            <div class="text-2xl font-bold text-green-600">{{ importResult.success }}</div>
            <div class="text-sm text-green-700">Succès</div>
          </div>
          <div class="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
            <div class="text-2xl font-bold text-yellow-600">{{ importResult.warnings }}</div>
            <div class="text-sm text-yellow-700">Avertissements</div>
          </div>
          <div class="bg-red-50 border border-red-200 rounded-lg p-4">
            <div class="text-2xl font-bold text-red-600">{{ importResult.errors }}</div>
            <div class="text-sm text-red-700">Erreurs</div>
          </div>
        </div>

        <!-- Errors and Warnings -->
        <div *ngIf="importErrors.length > 0" class="space-y-4">
          <h3 class="text-sm font-medium text-gray-900">Détails des erreurs et avertissements</h3>
          <div class="max-h-64 overflow-y-auto custom-scrollbar">
            <div *ngFor="let error of importErrors" 
                 class="flex items-start space-x-3 p-3 rounded-lg"
                 [class.bg-red-50]="error.type === 'error'"
                 [class.bg-yellow-50]="error.type === 'warning'">
              <lucide-angular [img]="error.type === 'error' ? AlertTriangle : AlertTriangle" 
                              [class.text-red-500]="error.type === 'error'"
                              [class.text-yellow-500]="error.type === 'warning'"
                              class="w-5 h-5 mt-0.5"></lucide-angular>
              <div class="flex-1">
                <p class="text-sm font-medium"
                   [class.text-red-800]="error.type === 'error'"
                   [class.text-yellow-800]="error.type === 'warning'">
                  Ligne {{ error.row }}, Colonne {{ error.column }}
                </p>
                <p class="text-sm"
                   [class.text-red-700]="error.type === 'error'"
                   [class.text-yellow-700]="error.type === 'warning'">
                  {{ error.message }}
                </p>
              </div>
            </div>
          </div>
        </div>

        <!-- Actions -->
        <div class="flex justify-end space-x-4 mt-6 pt-6 border-t border-gray-200">
          <button (click)="downloadReport()" class="btn-secondary">
            <lucide-angular [img]="Download" class="w-4 h-4 mr-2"></lucide-angular>
            Télécharger le rapport
          </button>
          <button (click)="resetImport()" class="btn-primary">
            Nouvel import
          </button>
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
export class ImportExcelComponent implements OnInit {
  // Icons
  Upload = Upload;
  FileSpreadsheet = FileSpreadsheet;
  CheckCircle = CheckCircle;
  AlertTriangle = AlertTriangle;
  X = X;
  Download = Download;
  Eye = Eye;

  // File handling
  uploadedFile: File | null = null;
  isFileValid = false;
  
  // Preview
  previewData: any[][] = [];
  previewHeaders: string[] = [];
  showPreview = false;
  
  // Processing
  isProcessing = false;
  processedRows = 0;
  totalRows = 0;
  progressPercentage = 0;
  
  // Results
  importResult: ImportResult | null = null;
  importErrors: ImportError[] = [];

  requiredColumns = [
    'RESEAU',
    'AGENCE',
    'Matricule CLIENT',
    'Numéro de carte',
    'NOM PORTEUR',
    'NOM CLIENT',
    'PRENOM CLIENT',
    'TELEPHONE',
    'EMAIL',
    'Cumul Montant transaction',
    'Mois Voyage',
    'ANNEE Voyage'
  ];

  ngOnInit() {}

  onDragOver(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    
    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.handleFile(files[0]);
    }
  }

  onFileSelect(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.handleFile(input.files[0]);
    }
  }

  private handleFile(file: File) {
    if (this.validateFileType(file)) {
      this.uploadedFile = file;
      this.loadPreview(file);
    }
  }

  private validateFileType(file: File): boolean {
    const allowedTypes = [
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      'application/vnd.ms-excel'
    ];
    
    if (!allowedTypes.includes(file.type)) {
      alert('Type de fichier non supporté. Veuillez utiliser un fichier Excel (.xlsx ou .xls)');
      return false;
    }
    
    const maxSize = 50 * 1024 * 1024; // 50MB
    if (file.size > maxSize) {
      alert('Le fichier est trop volumineux (max 50MB)');
      return false;
    }
    
    return true;
  }

  private loadPreview(file: File) {
    // Simulate file reading and preview generation
    setTimeout(() => {
      this.previewHeaders = this.requiredColumns.slice(0, 6); // Show first 6 columns
      this.previewData = [
        ['RES001', 'Douala Centre', 'EMP001', '1234****5678', 'DUPONT Jean', 'DUPONT'],
        ['RES002', 'Yaoundé Nlongkak', 'EMP002', '5678****1234', 'MARTIN Marie', 'MARTIN'],
        ['RES001', 'Bafoussam', 'EMP003', '9012****3456', 'BERNARD Paul', 'BERNARD'],
        ['RES003', 'Garoua', 'EMP004', '3456****7890', 'DURAND Sophie', 'DURAND']
      ];
      this.totalRows = 1250; // Simulate total rows
    }, 1000);
  }

  togglePreview() {
    this.showPreview = !this.showPreview;
  }

  validateFile() {
    if (!this.uploadedFile) return;
    
    // Simulate validation
    setTimeout(() => {
      this.isFileValid = true;
      this.importErrors = [
        {
          row: 15,
          column: 'EMAIL',
          message: 'Format d\'email invalide',
          type: 'warning'
        },
        {
          row: 23,
          column: 'Cumul Montant transaction',
          message: 'Valeur manquante',
          type: 'error'
        }
      ];
      
      alert('Validation terminée. Le fichier contient quelques avertissements mais peut être importé.');
    }, 2000);
  }

  startImport() {
    if (!this.uploadedFile || !this.isFileValid) return;
    
    this.isProcessing = true;
    this.processedRows = 0;
    this.progressPercentage = 0;
    
    // Simulate import process
    const interval = setInterval(() => {
      this.processedRows += Math.floor(Math.random() * 50) + 10;
      this.progressPercentage = Math.min((this.processedRows / this.totalRows) * 100, 100);
      
      if (this.processedRows >= this.totalRows) {
        clearInterval(interval);
        this.completeImport();
      }
    }, 200);
  }

  private completeImport() {
    this.isProcessing = false;
    this.importResult = {
      total: this.totalRows,
      success: this.totalRows - 15,
      errors: 5,
      warnings: 10
    };
    
    // Add more detailed errors
    this.importErrors = [
      ...this.importErrors,
      {
        row: 45,
        column: 'TELEPHONE',
        message: 'Numéro de téléphone invalide',
        type: 'warning'
      },
      {
        row: 67,
        column: 'Matricule CLIENT',
        message: 'Matricule déjà existant',
        type: 'error'
      }
    ];
  }

  removeFile() {
    this.uploadedFile = null;
    this.previewData = [];
    this.previewHeaders = [];
    this.isFileValid = false;
    this.showPreview = false;
  }

  downloadReport() {
    // Simulate report download
    const blob = new Blob(['Rapport d\'import...'], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'rapport-import.txt';
    a.click();
    window.URL.revokeObjectURL(url);
  }

  resetImport() {
    this.uploadedFile = null;
    this.previewData = [];
    this.previewHeaders = [];
    this.isFileValid = false;
    this.showPreview = false;
    this.isProcessing = false;
    this.importResult = null;
    this.importErrors = [];
    this.processedRows = 0;
    this.totalRows = 0;
    this.progressPercentage = 0;
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }
}