import { TestBed } from '@angular/core/testing';

import { PasswordDataService } from './password-data.service';

describe('PasswordDataService', () => {
  let service: PasswordDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PasswordDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
