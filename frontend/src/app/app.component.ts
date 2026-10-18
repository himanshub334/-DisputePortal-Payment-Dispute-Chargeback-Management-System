import { Component, OnInit } from '@angular/core';
import { Dispute, DisputeService } from './dispute.service';
@Component({selector:'app-root',templateUrl:'./app.component.html'})
export class AppComponent implements OnInit {
 disputes:Dispute[]=[]; loading=false; message=''; selectedFile:File|null=null; selectedId:number|null=null;
 form={transactionReference:'TX-10042',claimantType:'CARDHOLDER',amount:125.50,currency:'USD',reason:'Goods or services not received'};
 target='EVIDENCE_REQUESTED'; note='Evidence requested from claimant'; actor='portal-operator';
 constructor(private service:DisputeService){}
 ngOnInit(){this.load();}
 load(){this.loading=true;this.service.list().subscribe({next:d=>{this.disputes=d;this.loading=false;},error:e=>{this.message='API unavailable. Start the backend and PostgreSQL.';this.loading=false;}});}
 create(){this.message='';this.service.create(this.form).subscribe({next:d=>{this.message=`Created ${d.referenceCode}`;this.load();},error:e=>this.message=e.error?.message||'Could not create dispute. Check the form and API.'});}
 chooseFile(event:Event){const input=event.target as HTMLInputElement;this.selectedFile=input.files?.[0]||null;}
 upload(){if(!this.selectedId||!this.selectedFile){this.message='Select a dispute and file first.';return;}const f=this.selectedFile;const allowed=['pdf','png','jpg','jpeg','txt','csv'];const ext=f.name.split('.').pop()?.toLowerCase()||'';if(!allowed.includes(ext)){this.message='Allowed files: PDF, PNG, JPG, TXT, CSV.';return;}if(f.size>10*1024*1024){this.message='Maximum file size is 10 MB.';return;}this.service.upload(this.selectedId,f).subscribe({next:()=>this.message='Evidence uploaded and validated.',error:e=>this.message=e.error?.message||'Evidence upload failed.'});}
 transition(d:Dispute){this.service.transition(d.id,{target:this.target,note:this.note,actor:this.actor}).subscribe({next:()=>{this.message=`Updated ${d.referenceCode}`;this.load();},error:e=>this.message=e.error?.message||'Invalid transition.'});}
}
