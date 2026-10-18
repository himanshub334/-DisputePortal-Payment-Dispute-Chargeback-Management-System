import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
export interface Dispute { id:number; referenceCode:string; transactionReference:string; claimantType:string; amount:number; currency:string; reason:string; status:string; createdAt:string; }
@Injectable({providedIn:'root'})
export class DisputeService {
 constructor(private http:HttpClient){}
 list():Observable<Dispute[]>{return this.http.get<Dispute[]>('/api/disputes');}
 create(payload:unknown):Observable<Dispute>{return this.http.post<Dispute>('/api/disputes',payload);}
 transition(id:number,payload:unknown):Observable<Dispute>{return this.http.post<Dispute>(`/api/disputes/${id}/transitions`,payload);}
 upload(id:number,file:File):Observable<unknown>{const form=new FormData();form.append('file',file);return this.http.post(`/api/disputes/${id}/evidence`,form);}
 history(id:number):Observable<unknown[]>{return this.http.get<unknown[]>(`/api/disputes/${id}/history`);}
}
