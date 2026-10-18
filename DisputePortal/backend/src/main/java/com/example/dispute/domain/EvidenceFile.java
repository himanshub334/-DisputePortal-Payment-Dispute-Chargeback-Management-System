package com.example.dispute.domain;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
@Entity @Table(name="evidence_files")
public class EvidenceFile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="dispute_id") private Dispute dispute;
 @Column(name="original_name",nullable=false) private String originalName;
 @Column(name="stored_name",nullable=false) private String storedName;
 @Column(name="detected_mime_type",nullable=false) private String detectedMimeType;
 @Column(name="size_bytes",nullable=false) private long sizeBytes;
 @Column(name="uploaded_at",nullable=false) private OffsetDateTime uploadedAt=OffsetDateTime.now();
 protected EvidenceFile(){}
 public EvidenceFile(Dispute dispute,String originalName,String storedName,String mime,long size){this.dispute=dispute;this.originalName=originalName;this.storedName=storedName;this.detectedMimeType=mime;this.sizeBytes=size;}
 public Long getId(){return id;} public Long getDisputeId(){return dispute.getId();} public String getOriginalName(){return originalName;} public String getStoredName(){return storedName;}
 public String getDetectedMimeType(){return detectedMimeType;} public long getSizeBytes(){return sizeBytes;} public OffsetDateTime getUploadedAt(){return uploadedAt;}
}
