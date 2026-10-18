package com.example.dispute.service;
import com.example.dispute.domain.DisputeStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DisputeStateMachineTest {
 private final DisputeStateMachine machine=new DisputeStateMachine();
 @Test void allowsFiledToEvidenceRequested(){assertTrue(machine.canTransition(DisputeStatus.FILED,DisputeStatus.EVIDENCE_REQUESTED));}
 @Test void rejectsResolvedDisputeTransition(){assertFalse(machine.canTransition(DisputeStatus.RESOLVED_FAVOUR_MERCHANT,DisputeStatus.UNDER_REVIEW));}
 @Test void allowsUnderReviewToResolution(){assertTrue(machine.canTransition(DisputeStatus.UNDER_REVIEW,DisputeStatus.RESOLVED_FAVOUR_CARDHOLDER));}
}
