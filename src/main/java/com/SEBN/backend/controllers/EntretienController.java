package com.SEBN.backend.controllers;


import com.SEBN.backend.models.Entretien;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.EntretienRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "Entretien Management API")

public class EntretienController {
    @Autowired
    private EntretienRepository EntretienRepository;
    private Optional<User> user;

    @GetMapping("/entretiens")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Get all entretiens", notes = "Retrieve a list of all entretiens")
    @ApiResponse(code = 200, message = "List of entretiens retrieved successfully")
    public List<Entretien> getAllEntretiens() {


        return  (List<Entretien>) EntretienRepository.findAll();
    }


    @GetMapping("/condidature/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Get entretien by ID", notes = "Retrieve a single entretien by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entretien retrieved successfully"),
            @ApiResponse(code = 404, message = "Entretien not found")
    })
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
    @ApiOperation(value = "Create a new entretien", notes = "Create a new entretien entry")
    @ApiResponse(code = 200, message = "Entretien created successfully")
    public Entretien createEntretien(@Valid @RequestBody Entretien entretiens) {
        return EntretienRepository.save(entretiens);
    }



    @DeleteMapping("/entretiens/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Delete entretien", notes = "Delete an entretien by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entretien deleted successfully"),
            @ApiResponse(code = 404, message = "Entretien not found")
    })
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
