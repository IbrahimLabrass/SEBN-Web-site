package com.SEBN.backend.controllers;


import com.SEBN.backend.models.Condidature;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.CandidatureRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Condidature> getAllCondidatures() {
        return  (List<Condidature>) CandidatureRepository.findAll();
        //return (List<Condidature>) CondidatureRepository.findAll();
    }


    @GetMapping("/condidature/{id}")
    public ResponseEntity<Condidature> getCondidatureById(@PathVariable(value = "id") Long id) {
        Optional<Condidature> optionalCondidature = CandidatureRepository.findById(id);
        if (!optionalCondidature.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Condidature condidature = optionalCondidature.get();
        return ResponseEntity.ok().body(condidature);
    }


    @PostMapping("/condidature")
    public Condidature createCondidature(@Valid @RequestBody Condidature condidatures) {
        return CandidatureRepository.save(condidatures);
    }



    @DeleteMapping("/condidatures/{id}")
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

}