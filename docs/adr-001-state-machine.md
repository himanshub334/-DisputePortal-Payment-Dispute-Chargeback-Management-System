# ADR-001: Dispute lifecycle state machine

## Context
Dispute lifecycle changes must be explicit, validated and testable. The application needs a small state graph with terminal resolution states and an escalation path.

## Alternatives considered
1. `switch/case`: easy to start with, but transition rules become scattered as the graph grows.
2. Spring State Machine: supports guards, actions and persistence integrations, but adds framework configuration for this project's modest graph.
3. Transition map: a map from current state to allowed next states, centralized and straightforward to unit test.

## Decision
Use a transition map in `DisputeStateMachine`. `DisputeService` validates every transition and records accepted transitions. Invalid transitions return HTTP 400.

## Consequences
The transition map is compact and testable. More complex workflows may eventually justify a dedicated state-machine library.
