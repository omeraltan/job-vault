export interface Candidate {
    id: number;
    firstName: string;
    lastName: string;
    positionType: string;
    militaryStatus: string;
    noticePeriod: string;
    phone: string;
    email: string;
    cvPath?: string;
    cv: File | null;
  }
  