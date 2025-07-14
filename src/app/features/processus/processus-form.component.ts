import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LucideAngularModule, Upload, X, Eye, FileText, Calendar, User, CreditCard } from 'lucide-angular';

interface Document {
  id: string;
  name: string;
  size: number;
  type: string;
  file: File;
  preview?: string;
}

@Component({
  selector: 'app-processus-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, LucideAngularModule],
  template: `
    <div class="max-w-4xl mx-auto space-y-6 animate-fade-in">
      <!-- Header -->
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Nouveau processus d'apurement</h1>
          <p class="mt-1 text-sm text-gray-500">Soumission des justificatifs de voyage hors CEMAC</p>
        </div>
        <button (click)="goBack()" class="btn-secondary">
          Retour
        </button>
      </div>

      <form [formGroup]="processusForm" (ngSubmit)="onSubmit()" class="space-y-8">
        <!-- Informations générales -->
        <div class="card">
          <div class="flex items-center mb-6">
            <lucide-angular [img]="User" class="w-5 h-5 text-afriland-600 mr-2"></lucide-angular>
            <h2 class="text-lg font-semibold text-gray-900">Informations</h2>
          </div>
          
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="form-label">Matricule *</label>
              <input type="text" 
                     formControlName="matricule"
                     class="form-input"
                     placeholder="Entrez votre matricule">
              <div *ngIf="processusForm.get('matricule')?.invalid && processusForm.get('matricule')?.touched" 
                   class="mt-1 text-sm text-red-600">
                Le matricule est obligatoire
              </div>
            </div>
            
            <div>
              <label class="form-label">Nom complet</label>
              <input type="text" 
                     formControlName="nomComplet"
                     class="form-input"
                     placeholder="Nom et prénom">
            </div>
          </div>
        </div>

        <!-- Période de voyage -->
        <div class="card">
          <div class="flex items-center mb-6">
            <lucide-angular [img]="Calendar" class="w-5 h-5 text-afriland-600 mr-2"></lucide-angular>
            <h2 class="text-lg font-semibold text-gray-900">Période de voyage</h2>
          </div>
          
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="form-label">Date de départ *</label>
              <input type="date" 
                     formControlName="dateDepart"
                     class="form-input">
              <div *ngIf="processusForm.get('dateDepart')?.invalid && processusForm.get('dateDepart')?.touched" 
                   class="mt-1 text-sm text-red-600">
                La date de départ est obligatoire
              </div>
            </div>
            
            <div>
              <label class="form-label">Date de retour *</label>
              <input type="date" 
                     formControlName="dateRetour"
                     class="form-input">
              <div *ngIf="processusForm.get('dateRetour')?.invalid && processusForm.get('dateRetour')?.touched" 
                   class="mt-1 text-sm text-red-600">
                La date de retour est obligatoire
              </div>
            </div>
          </div>
        </div>

        <!-- Éléments déclarés -->
        <div class="card">
          <div class="flex items-center mb-6">
            <lucide-angular [img]="FileText" class="w-5 h-5 text-afriland-600 mr-2"></lucide-angular>
            <h2 class="text-lg font-semibold text-gray-900">Éléments déclarés</h2>
          </div>
          
          <div class="space-y-4">
            <div class="flex items-center">
              <input type="checkbox" 
                     formControlName="billetAvion"
                     class="h-4 w-4 text-afriland-600 focus:ring-afriland-500 border-gray-300 rounded">
              <label class="ml-3 text-sm font-medium text-gray-700">
                Billet d'avion / Titre de voyage
              </label>
            </div>
            
            <div class="flex items-center">
              <input type="checkbox" 
                     formControlName="visa"
                     class="h-4 w-4 text-afriland-600 focus:ring-afriland-500 border-gray-300 rounded">
              <label class="ml-3 text-sm font-medium text-gray-700">
                Visa
              </label>
            </div>
            
            <div class="flex items-center">
              <input type="checkbox" 
                     formControlName="tamponImmigration"
                     class="h-4 w-4 text-afriland-600 focus:ring-afriland-500 border-gray-300 rounded">
              <label class="ml-3 text-sm font-medium text-gray-700">
                Tampon d'immigration (entrée/sortie)
              </label>
            </div>
            
            <div class="flex items-center">
              <input type="checkbox" 
                     formControlName="factures"
                     class="h-4 w-4 text-afriland-600 focus:ring-afriland-500 border-gray-300 rounded">
              <label class="ml-3 text-sm font-medium text-gray-700">
                Factures (> 5M FCFA)
              </label>
            </div>
            
            <div class="space-y-2">
              <div class="flex items-center">
                <input type="checkbox" 
                       formControlName="autre"
                       class="h-4 w-4 text-afriland-600 focus:ring-afriland-500 border-gray-300 rounded">
                <label class="ml-3 text-sm font-medium text-gray-700">
                  Autre
                </label>
              </div>
              <div *ngIf="processusForm.get('autre')?.value" class="ml-7">
                <input type="text" 
                       formControlName="autrePrecision"
                       class="form-input"
                       placeholder="Précisez...">
              </div>
            </div>
          </div>
        </div>

        <!-- Carte bancaire utilisée -->
        <div class="card">
          <div class="flex items-center mb-6">
            <lucide-angular [img]="CreditCard" class="w-5 h-5 text-afriland-600 mr-2"></lucide-angular>
            <h2 class="text-lg font-semibold text-gray-900">Carte bancaire utilisée</h2>
          </div>
          
          <div>
            <label class="form-label">Numéro de carte *</label>
            <input type="text" 
                   formControlName="numeroCarte"
                   class="form-input"
                   placeholder="**** **** **** 1234"
                   maxlength="19">
            <div *ngIf="processusForm.get('numeroCarte')?.invalid && processusForm.get('numeroCarte')?.touched" 
                 class="mt-1 text-sm text-red-600">
              Le numéro de carte est obligatoire
            </div>
          </div>
        </div>

        <!-- Upload de documents -->
        <div class="card">
          <div class="flex items-center mb-6">
            <lucide-angular [img]="Upload" class="w-5 h-5 text-afriland-600 mr-2"></lucide-angular>
            <h2 class="text-lg font-semibold text-gray-900">Documents justificatifs</h2>
          </div>
          
          <!-- Drop Zone -->
          <div class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-afriland-400 transition-colors duration-200"
               (dragover)="onDragOver($event)"
               (dragleave)="onDragLeave($event)"
               (drop)="onDrop($event)">
            <lucide-angular [img]="Upload" class="w-12 h-12 text-gray-400 mx-auto mb-4"></lucide-angular>
            <p class="text-lg font-medium text-gray-900 mb-2">Glissez-déposez vos fichiers ici</p>
            <p class="text-sm text-gray-500 mb-4">ou</p>
            <label class="btn-primary cursor-pointer">
              Parcourir les fichiers
              <input type="file" 
                     multiple 
                     accept=".pdf,.jpg,.jpeg,.png,.doc,.docx"
                     (change)="onFileSelect($event)"
                     class="hidden">
            </label>
            <p class="text-xs text-gray-500 mt-2">PDF, JPG, PNG, DOC, DOCX (max 10MB par fichier)</p>
          </div>

          <!-- Documents List -->
          <div *ngIf="documents.length > 0" class="mt-6 space-y-3">
            <h3 class="text-sm font-medium text-gray-900">Documents ajoutés ({{ documents.length }})</h3>
            <div *ngFor="let doc of documents; trackBy: trackByDocument" 
                 class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
              <div class="flex items-center space-x-3">
                <div class="flex-shrink-0">
                  <lucide-angular [img]="FileText" class="w-5 h-5 text-gray-500"></lucide-angular>
                </div>
                <div class="flex-1 min-w-0">
                  <p class="text-sm font-medium text-gray-900 truncate">{{ doc.name }}</p>
                  <p class="text-xs text-gray-500">{{ formatFileSize(doc.size) }}</p>
                </div>
              </div>
              <div class="flex items-center space-x-2">
                <button type="button" 
                        (click)="previewDocument(doc)"
                        class="p-1 text-gray-400 hover:text-gray-600">
                  <lucide-angular [img]="Eye" class="w-4 h-4"></lucide-angular>
                </button>
                <button type="button" 
                        (click)="removeDocument(doc.id)"
                        class="p-1 text-red-400 hover:text-red-600">
                  <lucide-angular [img]="X" class="w-4 h-4"></lucide-angular>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Actions -->
        <div class="flex justify-end space-x-4 pt-6 border-t border-gray-200">
          <button type="button" 
                  (click)="saveDraft()"
                  class="btn-secondary">
            Sauvegarder le brouillon
          </button>
          <button type="submit" 
                  [disabled]="processusForm.invalid || isSubmitting"
                  class="btn-primary disabled:opacity-50 disabled:cursor-not-allowed">
            <span *ngIf="isSubmitting" class="inline-flex items-center">
              <svg class="animate-spin -ml-1 mr-3 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Soumission...
            </span>
            <span *ngIf="!isSubmitting">Soumettre le processus</span>
          </button>
        </div>
      </form>
    </div>
  `,
  styles: [`
    :host {
      display: block;
    }
  `]
})
export class ProcessusFormComponent implements OnInit {
  processusForm!: FormGroup;
  documents: Document[] = [];
  isSubmitting = false;
  
  // Icons
  Upload = Upload;
  X = X;
  Eye = Eye;
  FileText = FileText;
  Calendar = Calendar;
  User = User;
  CreditCard = CreditCard;

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    this.processusForm = this.fb.group({
      matricule: ['', [Validators.required]],
      nomComplet: [''],
      dateDepart: ['', [Validators.required]],
      dateRetour: ['', [Validators.required]],
      billetAvion: [false],
      visa: [false],
      tamponImmigration: [false],
      factures: [false],
      autre: [false],
      autrePrecision: [''],
      numeroCarte: ['', [Validators.required]]
    });
  }

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
    if (files) {
      this.handleFiles(Array.from(files));
    }
  }

  onFileSelect(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.handleFiles(Array.from(input.files));
    }
  }

  private handleFiles(files: File[]) {
    files.forEach(file => {
      if (this.validateFile(file)) {
        const document: Document = {
          id: Date.now().toString() + Math.random().toString(36).substr(2, 9),
          name: file.name,
          size: file.size,
          type: file.type,
          file: file
        };
        
        // Generate preview for images
        if (file.type.startsWith('image/')) {
          const reader = new FileReader();
          reader.onload = (e) => {
            document.preview = e.target?.result as string;
          };
          reader.readAsDataURL(file);
        }
        
        this.documents.push(document);
      }
    });
  }

  private validateFile(file: File): boolean {
    const maxSize = 10 * 1024 * 1024; // 10MB
    const allowedTypes = [
      'application/pdf',
      'image/jpeg',
      'image/jpg',
      'image/png',
      'application/msword',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    ];

    if (file.size > maxSize) {
      alert(`Le fichier ${file.name} est trop volumineux (max 10MB)`);
      return false;
    }

    if (!allowedTypes.includes(file.type)) {
      alert(`Le type de fichier ${file.name} n'est pas autorisé`);
      return false;
    }

    return true;
  }

  removeDocument(documentId: string) {
    this.documents = this.documents.filter(doc => doc.id !== documentId);
  }

  previewDocument(document: Document) {
    if (document.preview) {
      // Open image preview in modal
      window.open(document.preview, '_blank');
    } else {
      // For other file types, you might want to implement a different preview mechanism
      alert('Aperçu non disponible pour ce type de fichier');
    }
  }

  trackByDocument(index: number, document: Document): string {
    return document.id;
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }

  saveDraft() {
    // Save form data as draft
    const formData = this.processusForm.value;
    localStorage.setItem('processus_draft', JSON.stringify(formData));
    alert('Brouillon sauvegardé avec succès');
  }

  onSubmit() {
    if (this.processusForm.valid) {
      this.isSubmitting = true;
      
      // Simulate API call
      setTimeout(() => {
        this.isSubmitting = false;
        alert('Processus soumis avec succès!');
        this.router.navigate(['/processus']);
      }, 2000);
    } else {
      // Mark all fields as touched to show validation errors
      Object.keys(this.processusForm.controls).forEach(key => {
        this.processusForm.get(key)?.markAsTouched();
      });
    }
  }

  goBack() {
    this.router.navigate(['/processus']);
  }
}