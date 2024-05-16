package com.SEBN.backend.controllers;


import com.SEBN.backend.models.Entretien;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.EntretienRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")

public class EntretienController {
    @Autowired
    private EntretienRepository EntretienRepository;
    private Optional<User> user;

    @GetMapping("/entretiens")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")

    public List<Entretien> getAllEntretiens() {


        return  (List<Entretien>) EntretienRepository.findAll();
    }


    @GetMapping("/condidature/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")

    public ResponseEntity<Entretien> getEntretienById(@PathVariable(value = "id") Long id) {
        Optional<Entretien> optionalEntretien = EntretienRepository.findById(id);
        if (!optionalEntretien.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Entretien entretien = optionalEntretien.get();
        return ResponseEntity.ok().body(entretien);
    }


    @PostMapping("/entretiens")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")

    public Entretien createEntretien(@Valid @RequestBody Entretien entretiens) {
        return EntretienRepository.save(entretiens);
    }



    @DeleteMapping("/entretiens/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")

    public ResponseEntity<Map<String, Boolean>> deleteEntretien(@PathVariable(value = "id") Long id) {
        Optional<Entretien> optionalEntretien = EntretienRepository.findById(id);
        if (!optionalEntretien.isPresent()) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Entretien entretien = optionalEntretien.get();
        EntretienRepository.delete(entretien);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

}
