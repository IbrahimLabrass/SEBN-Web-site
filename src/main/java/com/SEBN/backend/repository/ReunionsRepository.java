package com.SEBN.backend.repository;

import com.SEBN.backend.models.Reunions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionsRepository extends JpaRepository<Reunions, Long> {
}