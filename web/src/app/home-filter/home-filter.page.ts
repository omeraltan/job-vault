import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonicModule, LoadingController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CandidateService } from '../services/candidate.service';
import { Candidate } from '../models/candidate.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-filter',
  templateUrl: './home-filter.page.html',
  styleUrls: ['./home-filter.page.scss'],
  standalone: true,
  imports: [IonicModule, FormsModule, CommonModule, HttpClientModule],
  providers: [CandidateService],
})
export class HomeFilterPage {
  candidates: Candidate[] = [];
  positionType: string | null = null;
  militaryStatus: string | null = null;
  noticePeriod: string | null = null;
  error: string | null = null;

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

  navigateToHome() {
    this.router.navigate(['/home']);
  }

  async filterCandidates() {
    const loading = await this.loadingController.create({
      message: 'Filtering candidates...',
    });
    await loading.present();

    this.candidateService
      .filterCandidates(this.positionType, this.militaryStatus, this.noticePeriod)
      .subscribe(
        (data) => {
          this.candidates = data;
          this.error = null;
          loading.dismiss();
        },
        (error) => {
          console.error('Error filtering candidates:', error);
          this.error = 'An error occurred while filtering candidates.';
          this.candidates = [];
          loading.dismiss();
        }
      );
  }

  downloadResume(cvPath: string) {
    const fileName = cvPath.split('/').pop();
    console.log("Dosyanın İsmi nedir: ",fileName)
    const downloadUrl = `http://localhost:8080/api/candidates/download-cv/${fileName}`;
    const link = document.createElement('a');
    link.href = downloadUrl;
    link.download = fileName || 'resume.pdf';
    link.click();
  }
    
  
}
