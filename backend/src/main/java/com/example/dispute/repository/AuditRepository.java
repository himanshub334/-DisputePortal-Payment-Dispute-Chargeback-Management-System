package com.example.dispute.repository;
import com.example.dispute.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditRepository extends JpaRepository<AuditLog,Long> {}
