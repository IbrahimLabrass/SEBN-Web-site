package com.SEBN.backend.controllers;


import com.SEBN.backend.models.Condidature;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.CandidatureRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")

public class CandidatureController {

    @Autowired
    private CandidatureRepository CandidatureRepository;
    private Optional<User> user;


    @GetMapping("/condidatures")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")

    public List<Condidature> getAllCondidatures() {
        return (List<Condidature>) CandidatureRepository.findAll();
    }


    @GetMapping("/condidature/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")

    public ResponseEntity<Condidature> getCondidatureById(@PathVariable(value = "id") Long id) {
        Optional<Condidature> optionalCondidature = CandidatureRepository.findById(id);
        if (!optionalCondidature.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Condidature condidature = optionalCondidature.get();
        return ResponseEntity.ok().body(condidature);
    }


    @PostMapping("/condidature")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")

    public Condidature createCondidature(@Valid @RequestBody Condidature condidatures) {
        return CandidatureRepository.save(condidatures);
    }

    @PutMapping("/condidatures/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")

    public ResponseEntity<Condidature> updateCondidature(@PathVariable(value = "id") Long id,
                                                         @Valid @RequestBody Condidature condidaturesDetails) {
        Optional<Condidature> optionalCondidature = CandidatureRepository.findById(id);
        if (!optionalCondidature.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Condidature existingCondidature = optionalCondidature.get();
        existingCondidature.setNom(condidaturesDetails.getNom());
        existingCondidature.setEmail(condidaturesDetails.getEmail());
        existingCondidature.setCv(condidaturesDetails.getCv());
        existingCondidature.setLettremo(condidaturesDetails.getLettremo());
        existingCondidature.setOffreEmpl(condidaturesDetails.getOffreEmpl());

        final Condidature updatedCondidature = CandidatureRepository.save(existingCondidature);
        return ResponseEntity.ok(updatedCondidature);
    }


    @DeleteMapping("/condidatures/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")

    public ResponseEntity<Map<String, Boolean>> deleteCondidature(@PathVariable(value = "id") Long id) {
        Optional<Condidature> optionalCondidature = CandidatureRepository.findById(id);
        if (!optionalCondidature.isPresent()) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Condidature condidature = optionalCondidature.get();
        CandidatureRepository.delete(condidature);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

    // Endpoint for preselecting candidates, accessible only to ROLE_RESP_RECRUTEMENT
    @PostMapping("/candidatures/preselection")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")

    public ResponseEntity<?> preselectCandidates(@RequestBody List<Long> candidatureIds) {
        try {
            // Logic to perform preselection of candidates
            for (Long candidatureId : candidatureIds) {
                Optional<Condidature> optionalCondidature = CandidatureRepository.findById(candidatureId);
                if (optionalCondidature.isPresent()) {
                    Condidature candidature = optionalCondidature.get();
                    // Perform preselection action on candidature
                    candidature.setPreselected(true); // Set a boolean flag indicating preselection
                    CandidatureRepository.save(candidature); // Save the updated candidature
                }
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while preselecting candidates.");
        }
    }
}


