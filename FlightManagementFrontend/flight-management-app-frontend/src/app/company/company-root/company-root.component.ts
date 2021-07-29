import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-company-root',
  templateUrl: './company-root.component.html',
  styleUrls: ['./company-root.component.css']
})
export class CompanyRootComponent implements OnInit {

  constructor(private route: ActivatedRoute, private router: Router) { }



  ngOnInit(): void {
  }


  goToCreateCompanyPage(): void{

    this.router.navigate(["#"], { relativeTo: this.route });

  }

  createCompanyButtonIsVisible(): boolean {
    let currentUrl= window.location.href;
    let lastUrlSegment=currentUrl.split('/').pop();
    return lastUrlSegment==='companies';
  }

  
}
