# Test plan template

| ID | Area | Scenario | Expected result | Status |
|---|---|---|---|---|
| DP-001 | Lifecycle | FILED → EVIDENCE_REQUESTED | Accepted | Not run |
| DP-002 | Lifecycle | UNDER_REVIEW → RESOLVED_FAVOUR_CARDHOLDER | Accepted | Not run |
| DP-003 | Lifecycle | RESOLVED_FAVOUR_MERCHANT → UNDER_REVIEW | Rejected | Not run |
| DP-004 | Evidence | Unsupported extension | HTTP 400 | Not run |
| DP-005 | Evidence | File over 10 MB | HTTP 400 | Not run |
| DP-006 | Evidence | MIME does not match extension | HTTP 400 | Not run |
| DP-007 | API | Missing transaction reference | Validation error | Not run |
| DP-008 | API | Unknown dispute ID | HTTP 404 | Not run |

Expand this plan as implementation and testing proceed. Do not mark tests as passed until they have actually been run.
