package com.example.dispute.service;
import com.example.dispute.domain.*;
import com.example.dispute.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
@Service
public class DisputeService {
 private final DisputeRepository disputes; private final TransitionRepository transitions; private final AuditRepository audit; private final DisputeStateMachine machine;
 public DisputeService(DisputeRepository d,TransitionRepository t,AuditRepository a,DisputeStateMachine m){this.disputes=d;this.transitions=t;this.audit=a;this.machine=m;}
 @Transactional public Dispute create(String transactionReference,ClaimantType claimantType,BigDecimal amount,String currency,String reason,String actor){
  if(amount==null||amount.signum()<=0)throw new IllegalArgumentException("Amount must be positive");
  String code="DSP-"+UUID.randomUUID().toString().substring(0,8).toUpperCase(Locale.ROOT);
  Dispute d=disputes.save(new Dispute(code,transactionReference,claimantType,amount,currency.toUpperCase(Locale.ROOT),reason));
  transitions.save(new StateTransition(d,null,DisputeStatus.FILED.name(),"Dispute filed",actor));
  audit.save(new AuditLog(d.getId(),"DISPUTE_CREATED","Created "+code,actor));return d;
 }
 public List<Dispute> list(DisputeStatus status){return status==null?disputes.findAllByOrderByCreatedAtDesc():disputes.findByStatusOrderByCreatedAtDesc(status);}
 public Dispute get(Long id){return disputes.findById(id).orElseThrow(()->new NoSuchElementException("Dispute not found"));}
 @Transactional public Dispute transition(Long id,DisputeStatus target,String note,String actor){
  Dispute d=get(id);DisputeStatus from=d.getStatus();
  if(!machine.canTransition(from,target))throw new IllegalArgumentException("Invalid transition: "+from+" -> "+target);
  d.setStatus(target);transitions.save(new StateTransition(d,from.name(),target.name(),note,actor));
  audit.save(new AuditLog(d.getId(),"STATE_CHANGED",from+" -> "+target+"; "+Objects.requireNonNullElse(note,""),actor));return d;
 }
 public List<StateTransition> history(Long id){get(id);return transitions.findByDisputeIdOrderByChangedAtAsc(id);}
}
