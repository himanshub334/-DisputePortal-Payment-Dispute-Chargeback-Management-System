package com.example.dispute.domain;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
@Entity @Table(name="audit_log")
public class AuditLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="dispute_id",nullable=false) private Long disputeId;
 @Column(name="event_type",nullable=false) private String eventType;
 @Column(nullable=false,length=1000) private String detail;
 @Column(nullable=false) private String actor;
 @Column(name="created_at",nullable=false) private OffsetDateTime createdAt=OffsetDateTime.now();
 protected AuditLog(){}
 public AuditLog(Long disputeId,String eventType,String detail,String actor){this.disputeId=disputeId;this.eventType=eventType;this.detail=detail;this.actor=actor;}
}
