package com.example.dispute.service;
import com.example.dispute.domain.*;
import com.example.dispute.repository.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
class DisputeServiceTest {
 @Test void invalidTransitionIsRejected(){
  DisputeRepository d=mock(DisputeRepository.class);TransitionRepository t=mock(TransitionRepository.class);AuditRepository a=mock(AuditRepository.class);
  DisputeStateMachine m=new DisputeStateMachine();Dispute dispute=new Dispute("DSP-12345678","TX-1",ClaimantType.MERCHANT,new java.math.BigDecimal("20.00"),"USD","Not received");
  when(d.findById(1L)).thenReturn(Optional.of(dispute));
  DisputeService service=new DisputeService(d,t,a,m);
  assertThrows(IllegalArgumentException.class,()->service.transition(1L,DisputeStatus.RESOLVED_FAVOUR_MERCHANT,"done","tester"));
 }
}
