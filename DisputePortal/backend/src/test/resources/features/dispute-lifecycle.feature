Feature: Dispute lifecycle
  Scenario: A filed dispute can request evidence
    Given a dispute is in FILED state
    When the dispute transitions to EVIDENCE_REQUESTED
    Then the transition should be accepted

  Scenario: A resolved dispute cannot be reopened directly
    Given a dispute is in RESOLVED_FAVOUR_MERCHANT state
    When the dispute transitions to UNDER_REVIEW
    Then the transition should be rejected
