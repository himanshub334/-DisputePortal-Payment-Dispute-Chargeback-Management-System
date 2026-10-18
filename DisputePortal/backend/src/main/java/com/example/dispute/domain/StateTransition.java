package com.example.dispute.domain;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
@Entity @Table(name="state_transitions")
public class StateTransition {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="dispute_id") private Dispute dispute;
 @Column(name="from_state") private String fromState;
 @Column(name="to_state",nullable=false) private String toState;
 @Column(length=1000) private String note;
 @Column(nullable=false) private String actor;
 @Column(name="changed_at",nullable=false) private OffsetDateTime changedAt=OffsetDateTime.now();
 protected StateTransition(){}
 public StateTransition(Dispute dispute,String from,String to,String note,String actor){this.dispute=dispute;this.fromState=from;this.toState=to;this.note=note;this.actor=actor;}
 public Long getId(){return id;} public Long getDisputeId(){return dispute.getId();} public String getFromState(){return fromState;} public String getToState(){return toState;} public String getNote(){return note;} public String getActor(){return actor;} public OffsetDateTime getChangedAt(){return changedAt;}
}
