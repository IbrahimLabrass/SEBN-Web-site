package com.SEBN.backend.repository;

import com.SEBN.backend.models.FeedbackEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  FeedbackRepository extends JpaRepository<FeedbackEvent, Long> {
}
