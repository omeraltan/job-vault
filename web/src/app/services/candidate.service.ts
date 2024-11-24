import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Candidate } from '../models/candidate.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root',
})
export class CandidateService {
  private apiUrl = `${environment.apiBaseUrl}/candidates`;

  constructor(private http: HttpClient) {}

  /**
   * Get paged candidates from the server
   * @param page Current page number (0-based index)
   * @param size Number of items per page
   */
  getCandidates(page: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<any>(this.apiUrl, { params });
  }

  /**
   * Add a new candidate
   * @param candidate Candidate data as FormData
   */
  addCandidate(candidate: FormData): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.apiUrl, candidate);
  }

  /**
   * Update an existing candidate
   * @param id Candidate ID
   * @param candidate Candidate data
   */
  updateCandidate(id: number, candidate: FormData): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.apiUrl}/${id}`, candidate);
  }
  

  /**
   * Delete a candidate
   * @param id Candidate ID
   */
  deleteCandidate(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/${id}`);
  }

  filterCandidates(
    positionType: string | null,
    militaryStatus: string | null,
    noticePeriod: string | null
  ): Observable<Candidate[]> {
    let params: any = {};
    if (positionType) params.positionType = positionType;
    if (militaryStatus) params.militaryStatus = militaryStatus;
    if (noticePeriod) params.noticePeriod = noticePeriod;
  
    return this.http.get<Candidate[]>(`${this.apiUrl}/filter`, { params });
  }
  
}
