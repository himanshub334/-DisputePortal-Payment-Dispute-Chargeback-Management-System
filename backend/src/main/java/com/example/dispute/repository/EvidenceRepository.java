package com.example.dispute.repository;
import com.example.dispute.domain.EvidenceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EvidenceRepository extends JpaRepository<EvidenceFile,Long> { List<EvidenceFile> findByDisputeIdOrderByUploadedAtDesc(Long disputeId); }
