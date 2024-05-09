package com.SEBN.backend.repository;

import com.SEBN.backend.models.OffreEmpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OffreEmplRepository extends JpaRepository<OffreEmpl,Long> {
    Optional<OffreEmpl> findByTitre(String titre);
    List<OffreEmpl> findByUser(String user);

}
