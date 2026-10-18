package com.example.dispute.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Entity @Table(name="disputes")
public class Dispute {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="reference_code",nullable=false,unique=true) private String referenceCode;
 @Column(name="transaction_reference",nullable=false) private String transactionReference;
 @Enumerated(EnumType.STRING) @Column(name="claimant_type",nullable=false) private ClaimantType claimantType;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal amount;
 @Column(nullable=false,length=3) private String currency;
 @Column(nullable=false,length=300) private String reason;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private DisputeStatus status;
 @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
 @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
 protected Dispute(){}
 public Dispute(String referenceCode,String transactionReference,ClaimantType claimantType,BigDecimal amount,String currency,String reason){
  this.referenceCode=referenceCode;this.transactionReference=transactionReference;this.claimantType=claimantType;this.amount=amount;this.currency=currency;this.reason=reason;this.status=DisputeStatus.FILED;this.createdAt=OffsetDateTime.now();this.updatedAt=this.createdAt;
 }
 @PreUpdate void touch(){updatedAt=OffsetDateTime.now();}
 public Long getId(){return id;} public String getReferenceCode(){return referenceCode;} public String getTransactionReference(){return transactionReference;}
 public ClaimantType getClaimantType(){return claimantType;} public BigDecimal getAmount(){return amount;} public String getCurrency(){return currency;}
 public String getReason(){return reason;} public DisputeStatus getStatus(){return status;} public OffsetDateTime getCreatedAt(){return createdAt;} public OffsetDateTime getUpdatedAt(){return updatedAt;}
 public void setStatus(DisputeStatus status){this.status=status;}
}
