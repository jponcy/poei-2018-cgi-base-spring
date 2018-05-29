import { UserApiService } from './../user-api.service';
import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  public users: any[];

  constructor(private api: UserApiService) {
  }

  ngOnInit() {
    this.api
      .getAll()
      .subscribe(
        (data: Response) => this.users = data.json(),
        (error: string) => console.error('TOO BAD', error)
      );
  }
}
