package com.example.dispute.repository;
import com.example.dispute.domain.StateTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TransitionRepository extends JpaRepository<StateTransition,Long> { List<StateTransition> findByDisputeIdOrderByChangedAtAsc(Long disputeId); }
