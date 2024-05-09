package com.SEBN.backend.repository;

import com.SEBN.backend.models.Condidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureRepository extends JpaRepository<Condidature,Long> {

}