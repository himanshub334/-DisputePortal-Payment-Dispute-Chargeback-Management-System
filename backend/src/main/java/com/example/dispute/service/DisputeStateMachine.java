package com.example.dispute.service;
import com.example.dispute.domain.DisputeStatus;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class DisputeStateMachine {
 private final Map<DisputeStatus,Set<DisputeStatus>> transitions=Map.of(
  DisputeStatus.FILED,Set.of(DisputeStatus.EVIDENCE_REQUESTED,DisputeStatus.UNDER_REVIEW,DisputeStatus.ESCALATED),
  DisputeStatus.EVIDENCE_REQUESTED,Set.of(DisputeStatus.UNDER_REVIEW,DisputeStatus.ESCALATED),
  DisputeStatus.UNDER_REVIEW,Set.of(DisputeStatus.RESOLVED_FAVOUR_CARDHOLDER,DisputeStatus.RESOLVED_FAVOUR_MERCHANT,DisputeStatus.ESCALATED),
  DisputeStatus.ESCALATED,Set.of(DisputeStatus.UNDER_REVIEW,DisputeStatus.RESOLVED_FAVOUR_CARDHOLDER,DisputeStatus.RESOLVED_FAVOUR_MERCHANT),
  DisputeStatus.RESOLVED_FAVOUR_CARDHOLDER,Set.of(),DisputeStatus.RESOLVED_FAVOUR_MERCHANT,Set.of()
 );
 public boolean canTransition(DisputeStatus from,DisputeStatus to){return transitions.getOrDefault(from,Set.of()).contains(to);}
 public Set<DisputeStatus> allowedFrom(DisputeStatus from){return transitions.getOrDefault(from,Set.of());}
}
