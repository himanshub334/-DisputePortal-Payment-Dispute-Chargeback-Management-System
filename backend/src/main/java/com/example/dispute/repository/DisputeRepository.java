package com.example.dispute.repository;
import com.example.dispute.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DisputeRepository extends JpaRepository<Dispute,Long> {
 List<Dispute> findAllByOrderByCreatedAtDesc();
 List<Dispute> findByStatusOrderByCreatedAtDesc(DisputeStatus status);
}
