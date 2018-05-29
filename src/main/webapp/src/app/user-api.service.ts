import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class UserApiService {

  constructor(private http: Http) { }

  public getAll(): any {
    return this.http.get('http://localhost:8080/user/');
  }
}
