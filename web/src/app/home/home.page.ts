import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonicModule, LoadingController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CandidateService } from '../services/candidate.service';
import { Candidate } from '../models/candidate.model';
import { ApiResponse } from '../models/api-response.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: true,
  imports: [IonicModule, FormsModule, CommonModule, HttpClientModule],
  providers: [CandidateService],
})
export class HomePage {

  statusMsg: string = '';
  isError: boolean = false;
  isLoading: boolean = false;
  resumeError: string = '';
  showAddCandidateForm = false;
  candidates: Candidate[] = [];
  errors: { [key: string]: string } = {};

  candidate: Candidate = {
    id: 0,
    firstName: '',
    lastName: '',
    positionType: 'BACKEND_DEVELOPER',
    militaryStatus: 'DONE',
    noticePeriod: 'ONE_MONTH',
    phone: '',
    email: '',
    cv: null,
  };

  currentPage: number = 1;
  totalPages: number = 1;
  pageSize: number = 5;
  totalCandidates: number = 0;

  positionTypes = [
    { label: 'Backend Developer', value: 'BACKEND_DEVELOPER' },
    { label: 'Frontend Developer', value: 'FRONTEND_DEVELOPER' },
    { label: 'Full Stack Developer', value: 'FULL_STACK_DEVELOPER' },
  ];

  militaryStatuses = [
    { label: 'Done', value: 'DONE' },
    { label: 'Not Done', value: 'NOT_DONE' },
  ];

  noticePeriods = [
    { label: '5 Days', value: 'FIVE_DAYS' },
    { label: '10 Days', value: 'TEN_DAYS' },
    { label: '1 Week', value: 'ONE_WEEK' },
    { label: '2 Weeks', value: 'TWO_WEEKS' },
    { label: '1 Month', value: 'ONE_MONTH' },
  ];

  constructor(
    private candidateService: CandidateService,
    private loadingController: LoadingController,
    private router: Router
  ) {}


  navigateToHomeFilter() {
    this.router.navigate(['/home-filter']);
  }

  async presentLoading(message: string) {
    const loading = await this.loadingController.create({
      message: message,
    });
    await loading.present();
    return loading;
  }

  ngOnInit() {
    this.loadCandidates();
  }

  async loadCandidates() {
    const loading = await this.presentLoading('Loading candidates...');
    this.candidateService.getCandidates(this.currentPage - 1, this.pageSize).subscribe(
      (data) => {
        if (data && data.content) {
          this.candidates = data.content;
          this.totalPages = data.totalPages;
          this.totalCandidates = data.totalElements;
          console.log(`Loaded page ${this.currentPage} of ${this.totalPages}`);
        } else {
          this.candidates = [];
          this.totalPages = 1;
          this.totalCandidates = 0;
          this.isError = true;
          this.statusMsg = 'No candidates found.';
        }
        loading.dismiss();
      },
      (error) => {
        console.error('Error loading candidates:', error);
        this.candidates = [];
        this.totalPages = 1;
        this.totalCandidates = 0;
        this.isError = true;
        this.statusMsg = error.error?.errorMessage || 'An error occurred while loading candidates!';
        loading.dismiss();
      }
    );
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadCandidates();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadCandidates();
    }
  }

  toggleAddCandidateForm() {
    this.showAddCandidateForm = !this.showAddCandidateForm;
    if (this.showAddCandidateForm) {
      this.resetForm();
      this.errors = {};
    }
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      const isPdf = file.type === 'application/pdf';
      const maxSizeInBytes = 2 * 1024 * 1024;

      if (!isPdf) {
        this.errors['cv'] = 'Only PDF files are allowed.';
        this.isError = true;
        this.candidate.cv = null;
        return;
      }

      if (file.size > maxSizeInBytes) {
        this.errors['cv'] = 'File size must not exceed 2 MB.';
        this.isError = true;
        this.candidate.cv = null;
        return;
      }

      this.candidate.cv = file;
      delete this.errors['cv'];
      this.isError = false;
    } else {
      this.errors['cv'] = 'Please upload a PDF resume.';
      this.isError = true;
      this.candidate.cv = null;
    }
  }

  async submitCandidate() {
    if (!this.candidate.cv) {
      this.errors['cv'] = 'Please upload a PDF resume.';
      this.isError = true;
      return;
    }

    const formData = new FormData();
    Object.entries(this.candidate).forEach(([key, value]) => {
      if (value !== null) {
        formData.append(key, value as string | Blob);
      }
    });

    const loading = await this.presentLoading('Submitting candidate...');
    this.candidateService.addCandidate(formData).subscribe(
      (response: ApiResponse) => {
        console.log('Candidate added successfully:', response.statusMsg);
        this.errors = {};
        this.isError = false;
        this.statusMsg = response.statusMsg;
        this.loadCandidates();
        this.resetForm();
        this.showAddCandidateForm = false;
        loading.dismiss();
      },
      (error) => {
        console.error('Error adding candidate:', error);
        this.errors = error.error || {};
        this.isError = true;
        this.statusMsg = 'Please fix the errors and try again.';
        loading.dismiss();
      }
    );
  }

  async updateCandidate() {
    console.log("Updating candidate with FormData:", this.candidate);

    const formData = new FormData();
    Object.entries(this.candidate).forEach(([key, value]) => {
        if (key === 'cv' && value instanceof File) {
            formData.append(key, value);
        } else if (value !== null) {
            formData.append(key, value as string | Blob);
        }
    });

    const loading = await this.presentLoading('Updating candidate...');
    this.candidateService.updateCandidate(this.candidate.id, formData).subscribe(
        (response: ApiResponse) => {
            console.log('Candidate Updated:', response.statusMsg);
            this.isError = false;
            this.statusMsg = response.statusMsg;
            this.errors = {};
            this.loadCandidates();
            this.resetForm();
            this.showAddCandidateForm = false;
            loading.dismiss();
        },
        (error) => {
            console.error('Error updating candidate:', error);
            this.isError = true;
            this.statusMsg = 'Please fix the errors and try again.';
            this.errors = error.error || {};
            loading.dismiss();
        }
    );
}

  async deleteCandidate(candidateId: number) {
    const loading = await this.presentLoading('Deleting candidate...');
    this.candidateService.deleteCandidate(candidateId).subscribe(
      () => {
        console.log(`Candidate with ID ${candidateId} deleted`);
        this.isError = false;
        this.statusMsg = 'Candidate deleted successfully.';
        this.loadCandidates();
        loading.dismiss();
      },
      (error) => {
        console.error('Error deleting candidate:', error);
        this.isError = true;
        this.statusMsg = error.error?.errorMessage || 'An error occurred while deleting the candidate!';
        loading.dismiss();
      }
    );
  }

  editCandidate(candidate: Candidate) {
    
    this.candidate = { ...candidate };
    console.log("Edit Candidate id : ", candidate.id)
    this.showAddCandidateForm = true;
    this.errors = {};
  }

  resetForm() {
    this.candidate = {
      id: 0,
      firstName: '',
      lastName: '',
      positionType: 'BACKEND_DEVELOPER',
      militaryStatus: 'DONE',
      noticePeriod: 'ONE_MONTH',
      phone: '',
      email: '',
      cv: null,
    };
    this.errors = {};
  }
}
